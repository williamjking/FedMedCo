<%@ page import="softexcel.fedmedco.EndPoint" %>



<div class="fieldcontain ${hasErrors(bean: endPointInstance, field: 'endPoint', 'error')} required">
	<label for="endPoint">
		<g:message code="endPoint.endPoint.label" default="End Point" />
		<span class="required-indicator">*</span>
	</label>
	<g:textField name="endPoint" required="" value="${endPointInstance?.endPoint}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: endPointInstance, field: 'openFDACategory', 'error')} required">
	<label for="openFDACategory">
		<g:message code="endPoint.openFDACategory.label" default="Open FDAC ategory" />
		<span class="required-indicator">*</span>
	</label>
	<g:select id="openFDACategory" name="openFDACategory.id" from="${softexcel.fedmedco.OpenFDACategory.list()}" optionKey="id" required=""  optionValue="category" class="many-to-one"/>

</div>
