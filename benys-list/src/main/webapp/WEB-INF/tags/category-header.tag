<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ attribute name="category" required="true"
	type="cz.beny.list.model.Category"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<table class="categoryHeader notBeingEdited">
	<tr>
		<td class="categoryInfoCell">
			<div class="actionButtonsCategory">
				<form action="/editOrRemoveCategory" method="post">
					<label class="categoryLabel">${category.name}</label>
					<input hidden="hidden" type="text" name="newCategoryName"
						id="newCategoryNameInput" value="${category.name}" />
					<input type="hidden" name="categoryId" value="${category.id}" />
					<input type="hidden" name="parentCategoryId"
						value="${category.parentCategoryId}" />
					<input type="submit" class="submitRemoveButton categoryHeaderBtn showOnMouseOver"
						name="action" value="x" />
					<input type="submit" class="submitEditButton categoryHeaderBtn"
						name="action" value="Save" hidden="hidden" />
					<input type="button" class="cancelEditButton categoryHeaderBtn"
						value="Cancel" hidden="hidden" />
					<input type="button" class="editButton categoryHeaderBtn showOnMouseOver" />
				</form>
				<div>
					<button id="wb${category.id}"
						class="hiddenWhenEditing wrapBtn categoryHeaderBtn showOnMouseOver"></button>
				</div>
				<form action="/changeCategoryOrder" method="post">
					<input type="hidden" name="categoryId" value="${category.id}" />
					<input type="submit" name="action" value="up"
						class="hiddenWhenEditing upBtn categoryHeaderBtn showOnMouseOver" />
					<input type="submit" name="action" value="down"
						class="hiddenWhenEditing  downBtn categoryHeaderBtn showOnMouseOver" />
				</form>
			</div>
		</td>
		<td class="addSubCatCell">
			<form action="/addCategory" method="post">
				<div>
					<c:set var="placeholderNewCat"/>
					<spring:message code="entry-table.placeholder.newCategory" var="placeholderNewCat" />
					<input type="text" name="categoryName" class="showOnMouseOver"
						placeholder="${placeholderNewCat}" />
					<input type="hidden" name="parentCategoryId" value="${category.id}" />
					<input type="submit" value=""
						class="addCategoryBtn categoryHeaderBtn showOnMouseOver" />
				</div>
			</form>
		</td>
	</tr>
</table>