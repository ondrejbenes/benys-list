<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="beny" tagdir="/WEB-INF/tags"%>
<%@ taglib prefix="benytld" uri="/WEB-INF/tld/beny.tld"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>


<%@ attribute name="entries" required="true" type="java.util.List"%>
<%@ attribute name="categoryId" required="true"%>

<table class="entryTable">
	<tbody class="sortable" id="${categoryId}">
		<c:forEach items="${entries}" var="entry">
			<tr id="entry_${entry.id}" class="entryRow notBeingEdited">
				<td><span class="hiddenWhenEditing">${benytld:detecturls(entry.hyperlink)}
				</span> <input hidden="hidden" type="text" name="newEntryHyperlink"
						id="newEntryHyperlinkInput" value="${entry.hyperlink}" /></td>
				<td><span class="hiddenWhenEditing">${entry.note}</span> <input
						hidden="hidden" type="text" name="newEntryNoteInput"
						id="newEntryNoteInput" value="${entry.note}" /></td>
				<td><beny:remove-entry-form entryId="${entry.id}" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>