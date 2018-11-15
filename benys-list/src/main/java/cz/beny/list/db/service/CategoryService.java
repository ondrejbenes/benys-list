package cz.beny.list.db.service;

import java.util.List;

import cz.beny.list.db.dao.CategoryDAO;
import cz.beny.list.logic.CategoryOrderAdjustment;
import cz.beny.list.model.Category;
import cz.beny.list.model.Entry;
import cz.beny.util.tree.TreeNode;

/**
 * Service layer component. Exposes {@link CategoryDAO} methods.
 *
 */
public interface CategoryService {
	/**
	 * Returns a Tree of Cateogries.
	 * @param rootId
	 * @return
	 */
	TreeNode<Category, Entry> getCategoryTree(Long rootId);
	
	/**
	 * Returns all Categories in a List.
	 * @return
	 */
	List<Category> getAllCategories();
	
	/**
	 * Adds subcategories to the passed list.
	 * @param categoryId
	 * @param list
	 */
	void getSubCategories(Long categoryId, List<Category> list);
	
	/**
	 * Returns a Category with matching Id.
	 * @param categoryId
	 * @return
	 */
	Category getCategoryById(Long categoryId);
	
	/**
	 * Returns the order value of the last Category in a subcategory group.
	 * @param parentCategoryId
	 * @return
	 */
	Integer getHighestOrderInCategory(final Long parentCategoryId);
	
	/**
	 * Increments or Decrements order of Category.
	 * @param categoryId
	 * @param adjustment
	 */
	void adjustCategoryOrder(final Long categoryId, final CategoryOrderAdjustment adjustment);

	/**
	 * Persists passed {@link Category} to the DB.
	 * @param category
	 */
	void saveCategory(Category category);
	
	/**
	 * Creates a {@link Category} isntance and persists it to the DB.
	 * 
	 * @param category
	 */
	void saveCategory(String categoryName, Long parentCategoryId, int order);
	
	/**
	 * Persists a list of {@link Category} instances.
	 * 
	 * @param categories
	 */
	void batchSave(List<Category> list);
	
	/**
	 * Gets a Category by Id and updates it.
	 * @param categoryId
	 * @param newParentCategoryId
	 * @param newName
	 * @param newOrder
	 */
	void updateCategory(Long categoryId, Long newParentCategoryId, String newName, int newOrder);

	/**
	 * Deletes Category including its Entries and Subcategories.
	 * 
	 * @param categoryId
	 */
	void deleteCategory(Long categoryId);
	
	/**
	 * Deletes all Categories (and Entries) in the list.
	 */
	void deleteAllCategories();
}
