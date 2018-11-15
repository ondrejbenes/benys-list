package cz.beny.list.logic.action;

import cz.beny.list.model.Entry;

/**
 * When EditEntryAction is instantiated, an {@link Entry} with matching Id is loaded from DB.
 * When the action is executed, the Entry is updated with values passed in constructor.
 * When it is reverted, the Entry is returned to the state before execution.
 * 
 */
public class EditEntryAction extends AbstractAction {

	private Entry entryBeforeEdit;	
	private Long entryId;
	private Long newCategoryId;
	private String newHyperlink;
	private String newNote;
	private int newOrder;

	public EditEntryAction(Long entryId, Long newCategoryId,
			String newHyperlink, String newNote, int newOrder) {
		super();
		Entry entry = entryService.getEntryById(entryId);
		this.entryBeforeEdit = new Entry(entry.getHyperlink(), entry.getNote(), entry.getCategoryId(), entry.getOrder());
		this.entryId = entryId;
		this.newCategoryId = newCategoryId;
		this.newHyperlink = newHyperlink;
		this.newNote = newNote;
		this.newOrder = newOrder;
	}

	@Override
	public void execute() {
		entryService.updateEntry(entryId, newCategoryId, newHyperlink, newNote,
				newOrder);
	}

	@Override
	public void revert() {
		entryService.updateEntry(entryId, entryBeforeEdit.getCategoryId(),
				entryBeforeEdit.getHyperlink(), entryBeforeEdit.getNote(),
				entryBeforeEdit.getOrder());
	}
}