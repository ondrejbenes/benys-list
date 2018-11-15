<%@ attribute name="entryId" required="true"%>

<form action="/editOrRemoveEntry" method="post" class="editOrRemoveEntryForm">
	<div class="actionButtonsEntry">
		<input type="hidden" name="entryId" value="${entryId}" />
		<input type="hidden" id="newHyperlink" name="newHyperlink"/>
		<input type="hidden" id="newNote" name="newNote"/>
		<input type="submit" class="submitRemoveButton alterEntryBtn showOnMouseOver" name="action" value="x" />
		<input type="submit" class="submitEntryEditButton alterEntryBtn" name="action" value="Save"
			hidden="hidden" />
		<input type="button" class="cancelEntryEditButton alterEntryBtn" value="Cancel"
			hidden="hidden" />
		<input type="button" class="editEntryButton alterEntryBtn showOnMouseOver" />
	</div>
</form>