<%@ page import="softexcel.fedmedco.Query" %>


<div class="form-group  ">
    <label class="col-sm-2" for="search"  class="control-label">
        <g:message code="category.label" default="Select a Category"></g:message>
    </label>
    <g:select id="categorySelect"
    	name="category"
    	from="${softexcel.fedmedco.OpenFDACategory.list()}"
    	optionKey="category"
    	optionValue="category" />
</div>

<div class="form-group  ">
    <label class="col-sm-2" for="search"  class="control-label">
        <g:message code="subcategory.label" default="Select a Sub Category"></g:message>
    </label>
    <g:select id="subCategorySelect"
    	name="subcategory"
    	from="${softexcel.fedmedco.EndPoint.list()}"
    	optionKey="endPoint"
    	optionValue="endPoint" />

</div>


<div class="form-group">
    <div class="col-sm-2"></div>
    <div id="fields" class="col-sm-10 panel panel-default">
        <div class="panel-heading">
            <div class="row">
                <div class="col-lg-4">Search Fields</div>
                <div class="col-lg-8">
                    <p style="text-align:right;">
                        <a href="#" id="add_another_field">
                            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
                            Add Another Field</a>
                    </p>
                </div>
            </div>
        </div>
        <div class="panel-body">
            <div class="form-inline" id="default_field">
                <div>
                    <div class="input-group">
                        <span class="input-group-addon" id="basic-addon1" style="color:rgba(0,0,0,0)">AND</span>
                        <select id="fieldsSelect_0" name="fields_0"></select>
                    </div>

                    <label for="criteria">
                        <input type="text" id="criteria" size="20" name="criteria_0" value="" required="" placeholder="Search Criteria" />
                    </label>
                    <label for="criteria_exact">
                        <input type="checkbox" id="criteria_exact" name="criteria_exact_0"/> Exact
                    </label>
                </div>
            </div>
            <div class="form-inline" id="additional_fields">
            </div>
        </div>
    </div>
</div>


<div class="form-group has-feedback">
    <div class="form-inline">
            <label class="col-sm-2" for="count">
                <g:message code="user.bpaContractNumber.label" default="Count" />
            </label>
            <input type="text"  id="count" class="col-sm-4" name="count" value="" placeholder="Count the number of unique values of a certain field"/>
            <label class="col-sm-offset-1" for="exactCount">
            <input type="checkbox" id="exactCount" name="exactCount"/> Exact </label>
    </div>
</div>

<div class="form-group has-feedback">
    <label class="col-sm-2" for="limit">
        <g:message code="user.bpaContractNumber.label" default="Limit" />
    </label>
    <input type="text"  id="limit" class="col-sm-4" name="limit" value="" placeholder="Return up to this number of records"/>
</div>


<div class="form-group has-feedback">
    <label class="col-sm-2" for="skip">
        <g:message code="user.bpaContractNumber.label" default="Skip" />
    </label>
    <input type="text"  id="skip" class="col-sm-4" name="skip" value="" placeholder="Skip this number of records"/>
</div>

<g:javascript>
	getFields();
</g:javascript>