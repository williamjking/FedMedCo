<!DOCTYPE html>
<!--[if lt IE 7 ]> <html lang="en" class="no-js ie6"> <![endif]-->
<!--[if IE 7 ]>    <html lang="en" class="no-js ie7"> <![endif]-->
<!--[if IE 8 ]>    <html lang="en" class="no-js ie8"> <![endif]-->
<!--[if IE 9 ]>    <html lang="en" class="no-js ie9"> <![endif]-->
<!--[if (gt IE 9)|!(IE)]><!--> <html lang="en" class="no-js"><!--<![endif]-->
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
		<link rel="shortcut icon" href="${createLinkTo(dir:'images',file:'favicon.png')}" type="image/x-icon" />
		<title><g:layoutTitle default=" Welcome to ${meta(name: 'app.name')}"/></title>
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1" />
  		<asset:stylesheet src="application.css"/>
		<asset:stylesheet src="jumbotron-narrow.css"/>
		<asset:javascript src="application.js"/>
		<asset:javascript src="bootstrap.js"/>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/d3/3.5.3/d3.min.js"></script>
	<script src="http://cdnjs.cloudflare.com/ajax/libs/topojson/1.6.9/topojson.min.js"></script>
	<script src="http://datamaps.github.io/scripts/datamaps.world.min.js"></script>
		<g:layoutHead/>
	</head>
	<body>
	<div class="container">
		<div class="header clearfix">
			<nav class="nav">
				<ul class="nav-pills pull-right">
					<li role="presentation"><a title="Home" class="glyphicon glyphicon-home home" href="${createLink(uri: '/')}"></a></li>

					<sec:ifNotLoggedIn>
						<li role="presentation"><g:link title="Login" class="glyphicon glyphicon-log-in login" controller="login" action="auth"></g:link></li>
					</sec:ifNotLoggedIn>
					<sec:ifLoggedIn>
						<li role="presentation"><a title="Logout" class="glyphicon glyphicon-log-out logout" href="${createLink(uri: '/j_spring_security_logout')}"></a></li>
					</sec:ifLoggedIn>
					<li role="presentation"><g:link title="Contact" class="glyphicon glyphicon-earphone contact" controller="contact"></g:link></li>
					<li role="presentation"><g:link title="Help" class="glyphicon glyphicon-question-sign help" controller="help"></g:link></li>
				</ul>
			</nav>
			<a class="logo" href="${createLink(uri: '/')}"><asset:image src="FedMedCo.png" alt="FedMedCo"/></a>
		</div>
		<g:layoutBody/>
		<footer class="footer">
			<a href="#">This website is developed in response to GSA RFQ 4QTFHS150004</a>
		</footer>

	</div> <!-- /container -->



	</body>
</html>
