package cz.beny.list.db.service.bean;

import java.util.ArrayList;
import java.util.List;

import cz.beny.list.CommonConstants;
import cz.beny.list.db.dao.CategoryDAO;
import cz.beny.list.db.dao.EntryDAO;
import cz.beny.list.db.service.CategoryService;
import cz.beny.list.logic.CategoryOrderAdjustment;
import cz.beny.list.model.Category;
import cz.beny.list.model.Entry;
import cz.beny.util.tree.TreeNode;

/**
 * Implementation of {@link CategoryService}
 *
 */
public class CategoryServiceBean implements CategoryService {

	private CategoryDAO categoryDAO = new CategoryDAO();

	private EntryDAO entryDAO = new EntryDAO();

	@Override
	public TreeNode<Category, Entry> getCategoryTree(Long rootId) {
		return categoryDAO.getCategoryTree(rootId);
	}

	@Override
	public List<Category> getAllCategories() {
		List<Category> categories = new ArrayList<>();
		categories.add(Category.getRoot());
		getSubCategories(CommonConstants.ROOT_CATEGORY_ID, categories);
		return categories;
	}

	@Override
	public void getSubCategories(Long categoryId, List<Category> list) {
		categoryDAO.getSubCategories(categoryId, list);
	}

	@Override
	public Category getCategoryById(Long categoryId) {
		return categoryDAO.getCategoryById(categoryId);
	}

	@Override
	public void deleteCategory(Long categoryId) {
		List<Category> subCategories = new ArrayList<>();
		categoryDAO.getSubCategories(categoryId, subCategories);

		for (Category subCategory : subCategories) {
			entryDAO.deleteAllEntriesInCategory(subCategory.getId());
			categoryDAO.deleteCategory(subCategory.getId());
		}

		entryDAO.deleteAllEntriesInCategory(categoryId);
		categoryDAO.deleteCategory(categoryId);
	}

	@Override
	public void deleteAllCategories() {
		deleteCategory(CommonConstants.ROOT_CATEGORY_ID);
	}

	@Override
	public void updateCategory(Long categoryId, Long newParentCategoryId,
			String newName, int newOrder) {
		categoryDAO.updateCategory(categoryId, newParentCategoryId, newName,
				newOrder);
	}

	@Override
	public void saveCategory(Category category) {
		categoryDAO.saveCategory(category);
	}

	@Override
	public void saveCategory(String categoryName, Long parentCategoryId,
			int order) {
		categoryDAO.saveCategory(categoryName, parentCategoryId, order);
	}

	@Override
	public void batchSave(List<Category> list) {
		categoryDAO.batchSave(list);
	}

	@Override
	public Integer getHighestOrderInCategory(final Long parentCategoryId) {
		return categoryDAO.getHighestOrderInCategory(parentCategoryId);
	}

	@Override
	public void adjustCategoryOrder(final Long categoryId,
			final CategoryOrderAdjustment adjustment) {			
		Category category = categoryDAO.getCategoryById(categoryId);
		
		fixCategoriesOrder(category.getParentCategoryId());

		switch (adjustment) {
		case INCREMENT:
			incrementCategoryOrder(category);
			break;
		case DECREMENT:
			decrementCategoryOrder(category);
			break;
		}
	}

	private void incrementCategoryOrder(Category category) {
		if (categoryDAO.getHighestOrderInCategory(
				category.getParentCategoryId()).equals(category.getOrder())) {
			return;
		}
		int oldOrder = category.getOrder();
		int newOrder = oldOrder + 1;
		Category current = categoryDAO.getCategoryByOrder(newOrder);
		current.setOrder(oldOrder);
		category.setOrder(newOrder);

		categoryDAO.saveCategory(current);
		categoryDAO.saveCategory(category);
	}

	private void decrementCategoryOrder(Category category) {
		if (category.getOrder() == 0) {
			return;
		}
		int oldOrder = category.getOrder();
		int newOrder = oldOrder - 1;
		Category current = categoryDAO.getCategoryByOrder(newOrder);
		current.setOrder(oldOrder);
		category.setOrder(newOrder);

		categoryDAO.saveCategory(current);
		categoryDAO.saveCategory(category);
	}
	
	private void fixCategoriesOrder(final Long parentCategoryId) {
		List<Category> categories = categoryDAO.getDirectSubCategories(parentCategoryId);
		int i = 0;
		for(Category cat : categories) {
			cat.setOrder(i++);
		}
		categoryDAO.batchSave(categories);
	}

}