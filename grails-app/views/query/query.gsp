<%--
  Created by IntelliJ IDEA.
  User: anupam
  Date: 6/20/15
  Time: 5:50 AM
--%>

<%@ page import="softexcel.fedmedco.OpenFDACategory" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'query.label', default: 'Query')}" />
    <title><g:message code="default.create.label" args="[entityName]" /></title>
    <script>

    </script>
</head>


<body>
    <a href="#query-query" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
    <div id="create_contractor" class="content scaffold-create" role="main">
        <h1><g:message code="default.search.label" default="Search OpenFDA" args="[entityName]" /></h1>
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
        <g:form class="form-horizontal" url="[resource:contractorInstance, action:'search']" >
            <fieldset class="form">
                <g:render template="form"/>
            </fieldset>
            <fieldset class="buttons">
                <button type="submit" class="btn btn-default"><span class="glyphicon glyphicon-search"></span> Submit</button>
                <button type="button" class="btn btn-default reset"><span class="glyphicon glyphicon-refresh"></span> Reset</button>
            </fieldset>
        </g:form>


        <g:if test="${queryResults != null}">
            <hr>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">Results from OpenFDA</h3>
                </div>
                <div class="panel-body">
                    <small>${queryResults}</small>
                </div>
            </div>
        </g:if>
    </div>

</body>
</html>