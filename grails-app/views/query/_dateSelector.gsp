<%@ page import="softexcel.fedmedco.Query" %>

<div class="form-group has-feedback">
    <div class="form-inline">

            <label class="col-md-2" for="medicine">
                <g:message code="user.bpaContractNumber.label" default="Starting year" />
            </label>
            <g:select class="col-md-3" name="beginDate" from="${2000..2015}" noSelection="['':'-Choose Starting Year-']"/>

            <label class="col-md-2" for="food">
                <g:message class="col-md-3" code="user.bpaContractNumber.label" default="Ending Year" />
            </label>
            <g:select class="col-md-3" name="endDate" from="${2001..2016}" noSelection="['':'-Choose Ending Year-']"/>

    </div>
</div>