package cz.beny.list.db.service;

import java.util.List;

import cz.beny.list.db.dao.EntryDAO;
import cz.beny.list.model.Category;
import cz.beny.list.model.Entry;
import cz.beny.util.tree.TreeNode;

/**
 * Service layer component. Exposes {@link EntryDAO} methods.
 * 
 */
public interface EntryService {
	/**
	 * Returns all (direct) Entries of a Category.
	 * 
	 * @param categoryId
	 */
	List<Entry> getEntriesByCategoryId(final Long categoryId);

	/**
	 * Returns all Entries in the list.
	 */
	List<Entry> getAllEntries();

	/**
	 * Returns the number of entries in the list.
	 */
	int getEntryCount();

	/**
	 * Returns Entry by Id.
	 * 
	 * @param entryId
	 */
	Entry getEntryById(Long entryId);

	/**
	 * Returns the order of the last Entry in a Catogry.
	 * 
	 * @param categoryId
	 */
	Integer getHighestOrderInCategory(final Long categoryId);

	/**
	 * Persists passed Entry.
	 * 
	 * @param entry
	 */
	void saveEntry(Entry entry);

	/**
	 * Creates and persists Entry.
	 * 
	 * @param hyperlink
	 * @param note
	 * @param categoryId
	 * @param order
	 */
	void saveEntry(String hyperlink, String note, Long categoryId, int order);

	/**
	 * Persists a list of Entries.
	 * 
	 * @param entries
	 */
	void batchSave(List<Entry> list);

	/**
	 * Finds Entry by Id and updates it.
	 * 
	 * @param entryId
	 * @param newCategoryId
	 * @param newHyperlink
	 * @param newNote
	 * @param newOrder
	 */
	void updateEntry(Long entryId, Long newCategoryId, String newHyperlink,
			String newNote, int newOrder);

	/**
	 * Adjusts order of Entries in a category so that it matches the order of
	 * Entries in the passed list.
	 * 
	 * @param categoryId
	 * @param adjustment
	 */
	void adjustOrder(List<Long> orderedListOfIds);

	/**
	 * Deletes Entry by Id.
	 * @param entryId
	 */
	void deleteEntry(Long entryId);

	/**
	 * Deletes all Entries in the list.
	 */
	void deleteAllEntries();

	/**
	 * Adds Entries to the passed Tree of Categories.
	 * @param treeRoot
	 */
	void populateCategoryTree(TreeNode<Category, Entry> treeRoot);

}
