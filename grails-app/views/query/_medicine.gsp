<%@ page import="softexcel.fedmedco.Query" %>

<div class="form-group has-feedback">
    <div class="form-inline">
            <label class="col-xs-4 col-sm-4" for="medicine">
                <g:message code="user.bpaContractNumber.label" default="Please enter medicine name" />
            </label>
            <input type="text" required="" id="medicine" class="col-xs-6 col-sm-6" name="medicine" value="" placeholder="Generic/Brand names e.g. advil or tylenol"/>
            <label class="col-xs-offset-0 col-sm-offset-1" for="exactCount">
            <input type="checkbox" id="exactCount" name="exactCount"/> Exact </label>
    </div>
</div>