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
	<input type="text" name="queryField" required="" class="col-sm-4" value="" placeholder="Enter the field to search" />
</div>

<div class="form-group has-feedback">
    <label class="col-sm-4" for="queryField">
        <g:message code="user.bpaContractNumber.label" default="Search Criteria" />
    </label>
	<input type="text"  class="col-sm-4" required="" name="criteria" value="" placeholder="Enter search criteria"/>
</div>



