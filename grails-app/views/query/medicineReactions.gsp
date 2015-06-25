
<%@ page import="softexcel.fedmedco.Query" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'query.label', default: 'Query')}" />
		<title><g:message code="default.show.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#show-query" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

		<nav id="menu" class="nav" role="navigation">
			<ul class="nav-pills">
				<li><a class="btn home" role="button" href="${createLink(uri: '/')}"><span class="glyphicon glyphicon-home"></span>  <g:message class="text-justify" code="default.home.label"/></a></li>
				<li><g:link class="btn list" role="button"><span class="glyphicon glyphicon-search"></span>  <g:message class="text-justify" code="default.search.label" args="[entityName]" /></g:link></li>
			</ul>
		</nav>

		<div id="searchResults" class="panel panel-default">
			<div class="panel-heading">
				<h4 class="panel-title">Reactions reported for drug - ${drugReactions?.medicine}</h4>
			</div>
			<div class="panel-body">
				There are ${drugReactions?.totalReportedReactions}  total reported reactions for the drug, ${drugReactions?.medicine}. Of these ${drugReactions?.severeReactions?.size()} are severe reactions, ${drugReactions?.moderateReactions?.size()}
				are moderate reactions and ${drugReactions?.mildReactions?.size()} are mild reactions. The top three reactions to these drugs are:

				<ul class="list-group">
					<g:each in="${drugReactions?.topThreeReactions}" status="i" var="reaction">
						<li class="list-group-item list-group-item-info">${reaction}</li>
					</g:each>
				</ul>
				<hr>
				For details on these reactions, please click the tabs below.
				<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
					<div class="panel panel-danger">
						<div class="panel-heading" role="tab" id="headingOne">
							<h4 class="panel-title">
								<a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
									<span class="badge">${drugReactions?.severeReactions?.size()}</span>Severe reactions reported for ${drugReactions?.medicine}
								</a>
							</h4>
						</div>
						<div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
							<div class="panel-body">
								<ul class="list-group">
									<g:each in="${drugReactions?.severeReactions}" status="i" var="reaction">
										<li class="list-group-item">
											<span class="badge">${reaction?.key}</span>
											${reaction?.value}
										</li>
									</g:each>
								</ul>
							</div>
						</div>
					</div>
					<div class="panel panel-warning">
						<div class="panel-heading" role="tab" id="headingTwo">
							<h4 class="panel-title">
								<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
									<span class="badge">${drugReactions?.moderateReactions?.size()}</span>Moderate reactions reported for ${drugReactions?.medicine}
								</a>
							</h4>
						</div>
						<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
							<div class="panel-body">
								<ul class="list-group">
									<g:each in="${drugReactions?.moderateReactions}" status="i" var="reaction">
										<li class="list-group-item">
											<span class="badge">${reaction?.key}</span>
											${reaction?.value}
										</li>
									</g:each>
								</ul>
							</div>
						</div>
					</div>
					<div class="panel panel-success">
						<div class="panel-heading" role="tab" id="headingThree">
							<h4 class="panel-title">
								<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
									<span class="badge">${drugReactions?.mildReactions?.size()}</span>Mild reactions reported for ${drugReactions?.medicine}
								</a>
							</h4>
						</div>
						<div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
							<div class="panel-body">
								<ul class="list-group">
									<g:each in="${drugReactions?.mildReactions}" status="i" var="reaction">
										<li class="list-group-item">
											<span class="badge">${reaction?.key}</span>
											${reaction?.value}
										</li>
									</g:each>
								</ul>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>
	</body>
</html>
