
<%@ page import="softexcel.fedmedco.Query" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'query.label', default: 'Query')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-query" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>


		<nav id="menu" class="nav" role="navigation">
			<ul class="nav-pills">
				<li><a class="btn home" role="button" href="${createLink(uri: '/')}"><span class="glyphicon glyphicon-home"></span>  <g:message class="text-justify" code="default.home.label"/></a></li>
			</ul>
		</nav>

		<g:if test="${flash.message}">
			<div class="message" role="status">${flash.message}</div>
		</g:if>
		<g:hasErrors bean="${queryInstance}">
			<ul class="errors" role="alert">
				<g:eachError bean="${queryInstance}" var="error">
					<li <g:if test="${error in org.springframework.validation.FieldError}">data-field-id="${error.field}"</g:if>><g:message error="${error}"/></li>
				</g:eachError>
			</ul>
		</g:hasErrors>

		<div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="headingMinusOne">
					<h4 class="panel-title">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseMinusOne" aria-expanded="false" aria-controls="collapseMinusOne">
							Adverse Events: Number of drug related patient casualties between two dates
						</a>
					</h4>
				</div>
				<div id="collapseMinusOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingMinusOne">
					<div class="panel-body">
						<g:form class="form-horizontal" url="[resource:contractorInstance, action:'patientDeaths']" >
							<fieldset class="form">
								<g:render template="dateSelector"/>
							</fieldset>
							<fieldset class="buttons">
								<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Submit</button>
							</fieldset>
						</g:form>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="headingZero">
					<h4 class="panel-title">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseZero" aria-expanded="false" aria-controls="collapseZero">
							Find out all the names for your medication
						</a>
					</h4>
				</div>
				<div id="collapseZero" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingZero">
					<div class="panel-body">
						<g:form class="form-horizontal" url="[resource:contractorInstance, action:'drugNames']" >
							<fieldset class="form">
								<g:render template="medicine"/>
							</fieldset>
							<fieldset class="buttons">
								<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Submit</button>
								<button type="button" class="btn btn-default reset"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
							</fieldset>
						</g:form>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="headingOne">
					<h4 class="panel-title">
						<a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
							Find out reported reactions to a drug
						</a>
					</h4>
				</div>
				<div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
					<div class="panel-body">
						<g:form class="form-horizontal" url="[resource:contractorInstance, action:'medicineReactions']" >
							<fieldset class="form">
								<g:render template="medicine"/>
							</fieldset>
							<fieldset class="buttons">
								<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Submit</button>
								<button type="button" class="btn btn-default reset"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
							</fieldset>
						</g:form>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="headingTwo">
					<h4 class="panel-title">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
							Find out interesting facts about your medication
						</a>
					</h4>
				</div>
				<div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
					<div class="panel-body">
                        <g:form class="form-horizontal" url="[resource:contractorInstance, action:'interestingFacts']" >
                            <fieldset class="form">
                                <g:render template="medicine"/>
                            </fieldset>
                            <fieldset class="buttons">
                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Submit</button>
                                <button type="button" class="btn btn-default reset"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
                            </fieldset>
                        </g:form>
					</div>
				</div>
			</div>
			<div class="panel panel-default">
				<div class="panel-heading" role="tab" id="headingThree">
					<h4 class="panel-title">
						<a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
							Find out what food or drugs to be cautious about while taking your medication
						</a>
					</h4>
				</div>
				<div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
					<div class="panel-body">
						<g:form class="form-horizontal" url="[resource:contractorInstance, action:'drugFoodInteractions']" >
							<fieldset class="form">
								<g:render template="foodInteractions"/>
							</fieldset>
							<fieldset class="buttons">
								<button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Submit</button>
								<button type="button" class="btn btn-default reset"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
							</fieldset>
						</g:form>
					</div>
				</div>
			</div>
            <div class="panel panel-default">
                <div class="panel-heading" role="tab" id="headingFour">
                    <h4 class="panel-title">
                        <a class="collapsed" role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="false" aria-controls="collapseFour">
                            Perform Generic Search on openFDA
                        </a>
                    </h4>
                </div>
                <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                    <div class="panel-body">
                        <g:form class="form-horizontal" url="[resource:contractorInstance, action:'search']" >
                            <fieldset class="form">
                                <g:render template="form"/>
                            </fieldset>
                            <fieldset class="buttons">
                                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Submit</button>
                                <button type="button" class="btn btn-default reset"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
                            </fieldset>
                        </g:form>
                    </div>
                </div>
            </div>
		</div>


	</body>
</html>
