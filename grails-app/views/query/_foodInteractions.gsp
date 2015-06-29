<%@ page import="softexcel.fedmedco.Query" %>

<div class="form-group has-feedback">
    <em class="col-sm-offset-1">When entering another drug, please make sure to try all names for the drug, such as its generic name, its brand name or its pharmacological classifications.</em>
    <div class="form-inline">

            <label class="col-xs-1 col-sm-1" for="medicine">
                <g:message code="user.bpaContractNumber.label" default="" />
            </label>
            <input type="text" required="" id="medicine" class="col-xs-5 col-sm-5" name="medicine" value="" placeholder="Generic/Brand names of your medicine e.g. advil or tylenol"/>


            <label class="col-xs-1 col-sm-1" for="food">
                <g:message code="user.bpaContractNumber.label" default="" />
            </label>

            <input type="text" required="" id="food" class="col-xs-4 col-sm-4" name="food" value="" placeholder="Does my medicine interact with this food?"/>
    </div>
</div>