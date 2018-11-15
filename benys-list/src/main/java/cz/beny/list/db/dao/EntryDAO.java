package cz.beny.list.db.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.googlecode.objectify.Key;

import cz.beny.list.model.Entry;

/**
 * Database Access Object for {@link Entry} class.
 *
 */
public class EntryDAO {
	/**
	 * Returns all (direct) Entries of a Category.
	 * @param categoryId
	 */
	public List<Entry> getEntriesByCategoryId(final Long categoryId) {
		List<Entry> entries = ofy().load().type(Entry.class)
				.filter("categoryId", categoryId).order("order").list();

		return entries;
	}

	/**
	 * Returns Entry by Id.
	 * @param entryId
	 */
	public Entry getEntryById(final Long entryId) {
		return ofy().load().type(Entry.class).id(entryId).now();
	}

	/**
	 * Returns all Entries in the list.
	 */
	public List<Entry> getAllEntries() {
		return ofy().load().type(Entry.class).list();
	}

	/**
	 * Returns the number of entries in the list.
	 */
	public int getEntryCount() {
		return ofy().load().type(Entry.class).count();
	}

	/**
	 * Returns the order of the last Entry in a Catogry.
	 * @param categoryId
	 */
	public Integer getHighestOrderInCategory(final Long categoryId) {
		Entry entry = ofy().load().type(Entry.class)
				.filter("categoryId", categoryId).order("-order").limit(1)
				.first().now();
		if (entry == null)
			return 0;
		else
			return entry.getOrder();
	}

	/**
	 * Persists passed Entry.
	 * @param entry
	 */
	public void saveEntry(Entry entry) {
		ofy().save().entity(entry).now();
	}

	/**
	 * Creates and persists Entry.
	 * @param hyperlink
	 * @param note
	 * @param categoryId
	 * @param order
	 */
	public void saveEntry(String hyperlink, String note, Long categoryId,
			int order) {
		Entry entry = new Entry(hyperlink, note, categoryId, order);
		ofy().save().entity(entry).now();
	}

	/**
	 * Persists a list of Entries.
	 * @param entries
	 */
	public void batchSave(List<Entry> entries) {
		ofy().save().entities(entries).now();
	}

	/**
	 * Finds Entry by Id and updates it. 
	 * @param entryId
	 * @param newCategoryId
	 * @param newHyperlink
	 * @param newNote
	 * @param newOrder
	 */
	public void updateEntry(Long entryId, Long newCategoryId,
			String newHyperlink, String newNote, int newOrder) {
		Entry entry = getEntryById(entryId);
		entry.setCategoryId(newCategoryId);
		entry.setHyperlink(newHyperlink);
		entry.setNote(newNote);
		entry.setOrder(newOrder);
		ofy().save().entity(entry).now();
	}

	/**
	 * Deletes Entry by Id.
	 * @param entryId
	 */
	public void deleteEntry(Long entryId) {
		Key<Entry> entryKey = Key.create(Entry.class, entryId);

		ofy().delete().key(entryKey).now();
	}

	/**
	 * Deletes all Entries in Category.
	 * @param categoryId
	 */
	public void deleteAllEntriesInCategory(Long categoryId) {
		List<Entry> entries = getEntriesByCategoryId(categoryId);
		for (Entry entry : entries) {
			deleteEntry(entry.getId());
		}
	}

	/**
	 * Deletes all Entries in the list.
	 */
	public void deleteAllEntries() {
		ofy().delete().type(Entry.class);
	}
}
