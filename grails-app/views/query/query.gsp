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

    <div id="searchDiv" class="row">
        <div class="col-sm-2 col-md-3 col-lg-3"></div>
        <div class="col-sm-8 col-md-6 col-lg-6">
            <div class="form-horizontal">
                <div id="category" class="form-group-sm has-feedback">
                    <label for="search"  class="control-label">
                        <g:message code="category.label" default="Select a Category"></g:message>
                    </label>
                    <g:select name="category" from="${softexcel.fedmedco.OpenFDACategory.list()}" optionKey="category" optionValue="category"/>
                </div>
                <div id="subcategory" class="form-group-sm has-feedback">
                    <label for="search"  class="control-label">
                        <g:message code="subcategory.label" default="Select a Sub Category"></g:message>
                    </label>
                    <g:select name="subcategory" from="${softexcel.fedmedco.EndPoint.list()}" optionKey="endPoint" optionValue="endPoint"/>
                </div>
                <div id="fieldText" class="form-group-sm has-feedback">
                    <label for="search"  class="control-label">
                        <g:message code="search.label" default="Search OpenFDA"></g:message>
                    </label>
                    <g:textField class="form-control" name="queryField" id="queryField" placeholder="Enter the field to search">
                    </g:textField>
                </div>
                <div id="searchText" class="form-group-sm has-feedback">
                    <g:textField class="form-control" name="search" id="search" placeholder="Enter search criteria">
                    </g:textField>
                    <span class="glyphicon glyphicon-search form-control-feedback"></span>
                </div>


                <select id="queryResults" class="form-control" style="display:none;"></select>
            </div>
        </div>
        <div class="col-sm-2 col-md-3 col-lg-3"></div>
    </div>
</body>
</html>