<%@ page import="softexcel.fedmedco.Query" %>


<div class="form-group  ">
    <label class="col-sm-4" for="search"  class="control-label">
        <g:message code="category.label" default="Select a Category"></g:message>
    </label>
    <g:select id="categorySelect" name="category" from="${softexcel.fedmedco.OpenFDACategory.list()}" optionKey="category" optionValue="category"/>
</div>

<div class="form-group  ">
    <label class="col-sm-4" for="search"  class="control-label">
        <g:message code="subcategory.label" default="Select a Sub Category"></g:message>
    </label>
    <g:select id="subCategorySelect" name="subcategory" from="${softexcel.fedmedco.EndPoint.list()}" optionKey="endPoint" optionValue="endPoint"/>

</div>

<div class="form-group  ">
	<label class="col-sm-4" for="queryField">
		<g:message code="user.bpaContractNumber.label" default="openFDA Field" />
	</label>
	<input type="text" id="queryField" name="queryField" required="" class="col-sm-4" value="" placeholder="Enter the field to search" />
</div>

<div class="form-group has-feedback">
    <div class="form-inline">
        <label class="col-sm-4" for="criteria"><g:message code="user.bpaContractNumber.label" default="Search Criteria" /></label>
	    <input type="text" id="criteria" class="col-sm-4" required="" name="criteria" value="" placeholder="Enter search criteria"/>
        <label class="col-sm-offset-1" for="exactCriteria">
            <input type="checkbox" id="exactCriteria" name="exactCriteria"/> Exact </label>
    </div>
</div>

<div class="form-group has-feedback">
    <div class="form-inline">
            <label class="col-sm-4" for="count">
                <g:message code="user.bpaContractNumber.label" default="Count" />
            </label>
            <input type="text"  id="count" class="col-sm-4" name="count" value="" placeholder="Count the number of unique values of a certain field"/>
            <label class="col-sm-offset-1" for="exactCount">
            <input type="checkbox" id="exactCount" name="exactCount"/> Exact </label>
    </div>
</div>

<div class="form-group has-feedback">
    <label class="col-sm-4" for="limit">
        <g:message code="user.bpaContractNumber.label" default="Limit" />
    </label>
    <input type="text"  id="limit" class="col-sm-4" name="limit" value="" placeholder="Return up to this number of records"/>
</div>


<div class="form-group has-feedback">
    <label class="col-sm-4" for="skip">
        <g:message code="user.bpaContractNumber.label" default="Skip" />
    </label>
    <input type="text"  id="skip" class="col-sm-4" name="skip" value="" placeholder="Skip this number of records"/>
</div>