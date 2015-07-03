
<%@ page import="softexcel.fedmedco.Query" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'query.label', default: 'Query')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
		<asset:stylesheet src="jquery.json-view.css"/>
		<asset:javascript src="jquery.json-view.js"/>
	</head>
	<body>
		<a href="#show-query" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

		<nav id="menu" class="nav" role="navigation">
			<ul class="nav-pills">
				<li><a class="btn home" role="button" href="${createLink(uri: '/')}"><span class="glyphicon glyphicon-home"></span>  <g:message class="text-justify" code="default.home.label"/></a></li>
				<li><g:link class="btn list" role="button"><span class="glyphicon glyphicon-search"></span>  <g:message class="text-justify" code="default.search.label" args="[entityName]" /></g:link></li>
			</ul>
		</nav>

		<ul class="errors" role="alert" style="display:none;">
			<li>Error in getting data from the server. Please try again later.</li>
		</ul>

		<h3 id="mapTitle" class="text-center">Drug related deaths reported throughout the world between ${beginDate} and ${endDate}</h3>
	    <div class="col-md-8, col-md-offset-1">
			<fieldset class="form">
				<g:render template="dateSelector"/>
			</fieldset>

		</div>
<div class="col-md-12">	   <div id="mapcontainer" style="position: relative; width:100%; height: 60%;"></div>
</div>		
		<g:javascript>
			var map = new Datamap({
				element: document.getElementById('mapcontainer'),
				fills: ${raw(fillKeys)},
				responsive: true,
				data: ${raw(queryResults)},
				geographyConfig: {
           			 popupTemplate: function(geo, data) {
           			    var numberOfDeaths = 'unknown';
           			    if (data != null && data.numberOfThings != null)
           			    	numberOfDeaths = data.numberOfThings;
                		return ['<div class="hoverinfo"><strong>',
								'Number of deaths in ' + geo.properties.name,
								': ' + numberOfDeaths,
								'</strong></div>'].join('');
            			}
        		}
			});

			$(window).on('resize', function() {
				hideErrorMessage();
       			map.resize();
    		});

			$('#beginDate').change(function(){
				getDeaths();
    		});
			$('#endDate').change(function(){
				getDeaths()
    		});

			function getDeaths() {
				hideErrorMessage();
				$.ajax({
					type: 'POST',
					dataType:'JSON',
					data: 'beginDate=' + escape($('#beginDate').val()) + '&endDate=' + escape($('#endDate').val()),
					url: '/FedMedCo/query/refreshPatientDeaths',
					success: function(data,textStatus){
					    map.updateChoropleth(data.queryResults);
                        $("#mapTitle").text("Drug related deaths reported throughout the world between "+ data.beginDate +" and " + data.endDate);
					},
					error: function(XMLHttpRequest,textStatus,errorThrown){
						showErrorMessage();
					}}
				);
			}

			function hideErrorMessage(){
        		$(".errors").hide();
		    }
			function showErrorMessage(){
        		$(".errors").show();
		    }

		</g:javascript>
	</body>
</html>
