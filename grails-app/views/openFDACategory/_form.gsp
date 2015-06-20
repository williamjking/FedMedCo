<%@ page import="softexcel.fedmedco.OpenFDACategory" %>



<div class="fieldcontain ${hasErrors(bean: openFDACategoryInstance, field: 'category', 'error')} required">
    <label for="category">
        <g:message code="openFDACategory.category.label" default="Category"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="category" required="" value="${openFDACategoryInstance?.category}"/>

</div>

