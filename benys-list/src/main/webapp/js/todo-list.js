function initWindow() {
	$(window).scroll(function() {
		localStorage['scroll'] = $(window).scrollTop();
	});

	window.onload = function() {
		var scroll = localStorage['scroll'];
		if (typeof scroll !== 'undefined' && scroll !== null) {
			window.scrollTo(0, scroll);
		}
	};
}

function initList() {
	$(".categoryHeader, .entryRow, .addEntryTable").mouseenter(function() {
		if($(this).hasClass("notBeingEdited")) {
			$(this).find(".showOnMouseOver").stop().fadeTo("fast", "1");
		}
	}).mouseleave(function() {
		if($(this).hasClass("notBeingEdited")) {
			$(this).find(".showOnMouseOver").fadeTo("slow", "0.1");
		}
	});

	$(".sortable").sortable({
		connectWith : ".sortable",
		update : function(event, ui) {
			serial = $(this).sortable('serialize');
			entryId = "&entryId=" + ui.item.attr('id').replace('entry_', '');
			categoryId = "&categoryId=" + $(this).attr('id');
			dataString = serial + entryId + categoryId;
			$.ajax({
				url : "sortEntry",
				type : "post",
				data : dataString,
				error : function() {
					alert("theres an error with AJAX");
				}
			});
		}
	}).disableSelection();
}

function initButtons() {
	$(".wrapBtn").click(function() {
		var categoryId = $(this).attr("id").replace('wb', '');
		var categoryIdString = "#" + categoryId;

		if ($(categoryIdString).hasClass("unwrapped")) {
			wrapChildren(categoryId);
		} else {
			unwrapChildren(categoryId);
		}
	});

	$(".editButton").click(function() {
		$(this).closest(".categoryHeader").removeClass("notBeingEdited");
		
		$(this).hide();
		$(this).siblings(".categoryLabel").hide();
		$(this).siblings(".submitRemoveButton").hide();
		$(this).closest("tr").find(".hiddenWhenEditing").hide();

		$(this).siblings("#newCategoryNameInput").show();
		$(this).siblings(".submitEditButton").show();
		$(this).siblings(".cancelEditButton").show();
	});
	$(".cancelEditButton").click(function() {
		$(this).closest(".categoryHeader").addClass("notBeingEdited");
		
		$(this).siblings(".categoryLabel").show();
		$(this).siblings(".editButton").show();
		$(this).siblings(".submitRemoveButton").show();
		$(this).closest("tr").find(".hiddenWhenEditing").show();

		$(this).hide();
		$(this).siblings("#newCategoryNameInput").hide();
		$(this).siblings(".submitEditButton").hide();
	});

	$(".editEntryButton").click(function() {
		$(this).closest(".entryRow").removeClass("notBeingEdited");
		
		$(this).hide();
		$(this).siblings(".submitRemoveButton").hide();
		$(this).closest("tr").find(".hiddenWhenEditing").hide();

		$(this).closest("tr").find("#newEntryHyperlinkInput").show();
		$(this).closest("tr").find("#newEntryNoteInput").show();
		$(this).siblings(".submitEntryEditButton").show();
		$(this).siblings(".cancelEntryEditButton").show();

		$(".sortable").enableSelection();
	});
	$(".cancelEntryEditButton").click(function() {
		$(this).closest(".entryRow").addClass("notBeingEdited");
		
		$(this).siblings(".editEntryButton").show();
		$(this).siblings(".submitRemoveButton").show();
		$(this).closest("tr").find(".hiddenWhenEditing").show();

		$(this).hide();
		$(this).closest("tr").find("#newEntryHyperlinkInput").hide();
		$(this).closest("tr").find("#newEntryNoteInput").hide();
		$(this).siblings(".submitEntryEditButton").hide();
		$(this).siblings(".cancelEntryEditButton").hide();

		$(".sortable").disableSelection();
	});
	$(".editOrRemoveEntryForm")
			.submit(
					function(event) {
						$(this).find("#newHyperlink").val(
								$(this).closest("tr").find(
										"#newEntryHyperlinkInput").val());
						$(this).find("#newNote").val(
								$(this).closest("tr")
										.find("#newEntryNoteInput").val());

						$(".sortable").disableSelection();
					});
	$(".uploadFileForm").submit(function(event) {
		window.close();
	});

	$("#restoreBtn").click(function() {
		LoadByName(150, 300);
	});
}

function wrapChildren(categoryId) {
	var childClass = ".childOf" + categoryId;
	$(childClass).each(function() {
		wrapChildren($(this).attr("id"));
		$(this).hide();
		$(this).removeClass("unwrapped");
		$(this).addClass("wrapped");
	});
	$("#" + categoryId).find(".entryTable").hide();
	$("#" + categoryId).removeClass("unwrapped");
	$("#" + categoryId).addClass("wrapped");
}

function unwrapChildren(categoryId) {
	var childClass = ".childOf" + categoryId;
	$(childClass).each(function() {
		unwrapChildren($(this).attr("id"));
		$(this).show();
		$(this).addClass("unwrapped");
		$(this).removeClass("wrapped");
	});
	$("#" + categoryId).find(".entryTable").show();
	$("#" + categoryId).addClass("unwrapped");
	$("#" + categoryId).removeClass("wrapped");
}