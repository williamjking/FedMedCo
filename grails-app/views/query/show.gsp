
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

		<g:if test="${queryResults != null}">
			<hr>
			<div id="searchResults" class="panel panel-default">
				<div class="panel-heading">
					<h3 class="panel-title">Results from OpenFDA</h3>
				</div>
				<div class="panel-body">
					<small>${queryResults}</small>
				</div>
			</div>
		</g:if>
	</body>
</html>
