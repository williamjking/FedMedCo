
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
				<h4 class="panel-title">Drug interactions for ${drugInteractions?.medicine} and ${drugInteractions?.otherDrugOrFood}</h4>
			</div>
			<div class="panel-body">
				<g:if test="${drugInteractions?.interactions == null && drugInteractions?.adverseReactions == null && drugInteractions?.boxedWarnings == null && drugInteractions?.warningAndPrecautions == null}">
                    There does not seem to be any interaction between your medicine, ${drugInteractions?.medicine}, and the food item or drug, ${drugInteractions?.otherDrugOrFood}.
                </g:if>
                <g:else>
                    Your medicine, ${drugInteractions?.medicine}, seems to interact with the food or drug item, ${drugInteractions?.otherDrugOrFood}. Please click on the panel headers below to get more details.
                     <hr>

                    <g:if test="${drugInteractions?.interactions != null}">
                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                           <div class="panel panel-info">
                               <div class="panel-heading" role="tab" id="headingOne">
                                   <h4 class="panel-title">
                                       <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                           Detailed drug interaction report for ${drugInteractions?.medicine} possibly caused by ${drugInteractions?.otherDrugOrFood}
                                        </a>
                                   </h4>
                               </div>
                            </div>
                            <div id="collapseOne" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingOne">
                                <div class="panel-body">
                                    ${raw(drugInteractions.interactions)}
                                </div>
                            </div>
                        </div>
                    </g:if>

                    <g:if test="${drugInteractions?.adverseReactions != null}">
                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                           <div class="panel panel-info">
                               <div class="panel-heading" role="tab" id="headingTwo">
                                   <h4 class="panel-title">
                                       <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="true" aria-controls="collapseTwo">
                                           Detailed adverse reactions report for ${drugInteractions?.medicine} possibly caused by ${drugInteractions?.otherDrugOrFood}
                                        </a>
                                   </h4>
                                </div>
                            </div>
                            <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                                <div class="panel-body">
                                    ${raw(drugInteractions.adverseReactions)}
                                </div>
                            </div>
                        </div>
                    </g:if>

                    <g:if test="${drugInteractions?.boxedWarnings != null}">
                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                           <div class="panel panel-info">
                               <div class="panel-heading" role="tab" id="headingThree">
                                   <h4 class="panel-title">
                                       <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="true" aria-controls="collapseThree">
                                           Critical warning for ${drugInteractions?.medicine} possibly due to ${drugInteractions?.otherDrugOrFood}
                                        </a>
                                    </h4>
                                </div>
                            </div>
                            <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                                <div class="panel-body">
                                        ${raw(drugInteractions.boxedWarnings)}
                                </div>
                            </div>
                        </div>
                    </g:if>

                    <g:if test="${drugInteractions?.warningAndPrecautions != null}">
                        <div class="panel-group" id="accordion" role="tablist" aria-multiselectable="true">
                            <div class="panel panel-info">
                                <div class="panel-heading" role="tab" id="headingFour">
                                    <h4 class="panel-title">
                                        <a role="button" data-toggle="collapse" data-parent="#accordion" href="#collapseFour" aria-expanded="true" aria-controls="collapseFour">
                                            General Warnings and Precautions for ${drugInteractions?.medicine} possibly due to ${drugInteractions?.otherDrugOrFood}
                                        </a>
                                    </h4>
                                </div>
                            </div>
                            <div id="collapseFour" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingFour">
                                <div class="panel-body">
                                    ${raw(drugInteractions.warningAndPrecautions)}
                                </div>
                            </div>
                        </div>
                    </g:if>

                </g:else>

			</div>
		</div>
	</body>
</html>
