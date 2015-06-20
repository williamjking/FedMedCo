<%@ page import="softexcel.fedmedco.Query" %>



<div class="fieldcontain ${hasErrors(bean: queryInstance, field: 'criteria', 'error')} required">
	<label for="criteria">
		<g:message code="query.criteria.label" default="Criteria" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="criteria" required="" value="${queryInstance?.criteria}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: queryInstance, field: 'queryField', 'error')} required">
	<label for="queryField">
		<g:message code="query.queryField.label" default="Query Field" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="queryField" required="" value="${queryInstance?.queryField}"/>

</div>

