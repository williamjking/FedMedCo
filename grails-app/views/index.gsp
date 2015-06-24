<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main"/>
	</head>
	<body>
		<a href="#page-body" class="skip"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>

	<div class="jumbotron hero-spacer">
		<h1>Welcome to FedMedCo!</h1>
		<p>A site to use and visualize data sets from OpenFDA.gov. The goal of OpenFDA is to provide easy access to public data. This website was created in response to GSA RFQ.

        <div class="text-center">
		<sec:ifNotLoggedIn>
			<g:link title="Enter" class="btn btn-primary btn-large" controller="query"><span class="glyphicon glyphicon-log-in login"/>  Enter!</g:link>
		</sec:ifNotLoggedIn>
        <sec:ifLoggedIn>
			<g:link title="Enter" class="btn btn-primary btn-large" controller="query"><span class="glyphicon glyphicon-search"/>  <g:message class="text-justify" code="default.search.label" /></g:link>
			<a title="Logout" class="btn btn-primary btn-large" href="${createLink(uri: '/j_spring_security_logout')}"><span class="glyphicon glyphicon-log-out logout"/>  Logout!</a>
        </sec:ifLoggedIn>
        </div>

		</p>
	</div>

	</body>
</html>
