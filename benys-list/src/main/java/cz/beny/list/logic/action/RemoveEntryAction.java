package cz.beny.list.logic.action;

import cz.beny.list.model.Entry;

/**
 * When RemoveEntryAction is instantiated, an {@link Entry} with matching Id is loaded from DB.
 * When the action is executed, the Category is deleted.
 * When it is reverted, the Category is persisted back to the DB.
 * 
 */
public class RemoveEntryAction extends AbstractAction {
	
	private Entry entry;
	
	public RemoveEntryAction(Long entryId) {
		super();
		this.entry = entryService.getEntryById(entryId);
	}

	@Override
	public void execute() {
		entryService.deleteEntry(entry.getId());
	}

	@Override
	public void revert() {
		entryService.saveEntry(entry);
	}
}