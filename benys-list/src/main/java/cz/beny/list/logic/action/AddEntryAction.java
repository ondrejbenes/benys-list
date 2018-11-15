package cz.beny.list.logic.action;

import cz.beny.list.model.Entry;

/**
 * When AddEntryAction is instantiated, a new {@link Entry} is created.
 * When the action is executed, the Entry is added as the last in the list of
 * sibling Entries. 
 * When it is reverted, the Entry is deleted.
 * 
 */
public class AddEntryAction extends AbstractAction {
	
	private Entry entry;

	public AddEntryAction(String hyperlink, String note, Long categoryId) {
		super();
		int order = entryService.getHighestOrderInCategory(categoryId) + 1;
		this.entry = new Entry(hyperlink, note, categoryId, order);
	}

	@Override
	public void execute() {
		entryService.saveEntry(entry);
	}
	
	@Override
	public void revert() {
		entryService.deleteEntry(entry.getId());
	}
}