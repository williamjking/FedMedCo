<%@ page import="softexcel.fedmedco.Query" %>

<div class="form-group has-feedback">
    <div class="form-inline">
            <label class="col-sm-4" for="count">
                <g:message code="user.bpaContractNumber.label" default="Please enter medicine name" />
            </label>
            <input type="text"  id="medicine" class="col-sm-6" name="medicine" value="" placeholder="Pharmacological name e.g. nonsteroidal anti-inflammatory drug"/>
            <label class="col-sm-offset-1" for="exactCount">
            <input type="checkbox" id="exactCount" name="exactCount"/> Exact </label>
    </div>
</div>