package cz.beny.list.db.service.bean;

import java.util.List;

import cz.beny.list.db.dao.EntryDAO;
import cz.beny.list.db.service.EntryService;
import cz.beny.list.model.Category;
import cz.beny.list.model.Entry;
import cz.beny.util.tree.TreeNode;

/**
 * Implementation of {@link EntryService}.
 * 
 */
public class EntryServiceBean implements EntryService {
	
	private EntryDAO entryDAO = new EntryDAO();

	@Override
	public List<Entry> getEntriesByCategoryId(Long categoryId) {
		return entryDAO.getEntriesByCategoryId(categoryId);
	}

	@Override
	public Entry getEntryById(Long entryId) {
		return entryDAO.getEntryById(entryId);
	}

	@Override
	public List<Entry> getAllEntries() {
		return entryDAO.getAllEntries();
	}

	@Override
	public int getEntryCount() {
		return entryDAO.getEntryCount();
	}

	@Override
	public Integer getHighestOrderInCategory(Long categoryId) {
		return entryDAO.getHighestOrderInCategory(categoryId);
	}
	
	@Override
	public void saveEntry(Entry entry) {
		entryDAO.saveEntry(entry);
	}

	@Override
	public void saveEntry(String hyperlink, String note, Long categoryId, int order) {
		entryDAO.saveEntry(hyperlink, note, categoryId, order);
	}

	@Override
	public void batchSave(List<Entry> list) {
		entryDAO.batchSave(list);
	}

	@Override
	public void updateEntry(Long entryId, Long newCategoryId,
			String newHyperlink, String newNote, int newOrder) {
		entryDAO.updateEntry(entryId, newCategoryId, newHyperlink, newNote, newOrder);
	}

	@Override
	public void adjustOrder(List<Long> orderedListOfIds) {
		int i = 0;
		for(Long entryId : orderedListOfIds) {
			Entry entryToUpdate = getEntryById(entryId);
			entryToUpdate.setOrder(i++);
			saveEntry(entryToUpdate);
		}
	}

	@Override
	public void deleteEntry(Long entryId) {
		entryDAO.deleteEntry(entryId);
	}

	@Override
	public void deleteAllEntries() {
		entryDAO.deleteAllEntries();
	}
	
	@Override
	public void populateCategoryTree(TreeNode<Category, Entry> treeRoot) {
		for (TreeNode<Category, Entry> node : treeRoot) {
			List<Entry> entries = getEntriesByCategoryId(node.getData().getId());
			for (Entry entry : entries) {
				node.getElements().add(entry);
			}
		}
	}
}
