
<%@ page import="softexcel.fedmedco.Contact" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'contact.label', default: 'Contact')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
	    <p></p>
		<div class="col-md-7">
			<h3>Thank You for your interest in FedMedCo!</h3>
			<p class="text-left">Please help us improve the website. We would love your comments!</p>
		</div>
		<div id="contact" class="col-md-3 navbar navbar-default navbar-right">
			<h4>Contact Information:</h4>
			<address>
				<strong>Lockheed martin</strong><br>
				Information Systems & Global Solutions<br>
				2345 Crystal Drive, Suite 300<br>
				Arlington, VA 22202<br>
				<abbr title="Phone">P:</abbr> (571) 357-7500
			</address>

			<address>
				<strong>Email:</strong><br>
				<a href="mailto:#">Phil.magrogan@lmco.com</a>
			</address>
		</div>
	</body>
</html>
