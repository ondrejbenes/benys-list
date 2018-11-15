<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="categoryId" required="true" %>

<c:set var="placeholderHyperlink"/>
<spring:message code="entry-table.placeholder.hyperlink" var="placeholderHyperlink" />
<c:set var="placeholderNote"/>
<spring:message code="entry-table.placeholder.note" var="placeholderNote" />

<form action="/addEntry" method="post" class="addEntryForm">
	<input type="hidden" name="categoryId" value="${categoryId}" />
	<table class="addEntryTable notBeingEdited">
		<tr>
		<td>
			<input name="hyperlink" placeholder="${placeholderHyperlink}" class="hyperlinkInput showOnMouseOver"/>
		</td>
		<td>
			<input name="note" placeholder="${placeholderNote}" class="noteInput showOnMouseOver"/>
		</td>
		<td>
			<input type="submit" class="submitEntryButton showOnMouseOver"/>
		</td>
	</table>
</form>