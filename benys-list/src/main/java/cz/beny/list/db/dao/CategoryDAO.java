package cz.beny.list.db.dao;

import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import cz.beny.list.CommonConstants;
import cz.beny.list.model.Category;
import cz.beny.list.model.Entry;
import cz.beny.util.tree.TreeNode;

/**
 * Database Access Object for {@link Category} class.
 * 
 * @author Admin
 * 
 */
public class CategoryDAO {
	/**
	 * Creates a Tree (using {@link TreeNode}) of Categories.
	 * 
	 * @param rootId
	 * @return
	 */
	public TreeNode<Category, Entry> getCategoryTree(Long rootId) {
		TreeNode<Category, Entry> tree = new TreeNode<>(getCategoryById(rootId));
		if(tree.getData() == null)
		{
			Category root = new Category("root", CommonConstants.ROOT_CATEGORY_ID, 0);
			tree.setData(root);
			saveCategory(root);
			System.out.println("Creating new root");
		}
		addSubCategories(tree);
		return tree;
	}

	/**
	 * Returns a Category with matching Id.
	 * @param categoryId
	 * @return
	 */
	public Category getCategoryById(Long categoryId) {
		return ofy().load().type(Category.class).id(categoryId).now();
	}

	/**
	 * Returns a category with matching order.
	 * 
	 * @param order
	 * @return
	 */
	public Category getCategoryByOrder(int order) {
		return ofy().load().type(Category.class).filter("order", order).first()
				.now();
	}

	/**
	 * Deletes category without checking for subcategories or entries. If there
	 * are such, they remain in the DB.
	 * 
	 * @param categoryId
	 */
	public void deleteCategory(Long categoryId) {
		ofy().delete().type(Category.class).id(categoryId).now();
	}

	/**
	 * Returns the order value of the last Category in a subcategory group.
	 * 
	 * @param parentCategoryId
	 * @return
	 */
	public Integer getHighestOrderInCategory(final Long parentCategoryId) {
		Category category = ofy().load().type(Category.class)
				.filter("parentCategoryId", parentCategoryId).order("-order")
				.limit(1).first().now();
		if (category == null)
			return 0;
		else
			return category.getOrder();
	}

	/**
	 * Returns the order value of the first Category in a subcategory group.
	 * 
	 * @param parentCategoryId
	 * @return
	 */
	public Integer getLowestOrderInCategory(final Long parentCategoryId) {
		Category category = ofy().load().type(Category.class)
				.filter("parentCategoryId", parentCategoryId).order("order")
				.limit(1).first().now();
		if (category == null)
			return 0;
		else
			return category.getOrder();
	}

	/**
	 * Gets a Category by Id and updates it.
	 * 
	 * @param categoryId
	 * @param newParentCategoryId
	 * @param newName
	 * @param newOrder
	 */
	public void updateCategory(Long categoryId, Long newParentCategoryId,
			String newName, int newOrder) {
		Category category = getCategoryById(categoryId);
		category.setParentCategoryId(newParentCategoryId);
		category.setName(newName);
		category.setOrder(newOrder);
		ofy().save().entity(category).now();
	}

	/**
	 * Persists passed {@link Category} to the DB.
	 * 
	 * @param category
	 */
	public void saveCategory(Category category) {
		ofy().save().entity(category).now();
	}

	/**
	 * Creates a {@link Category} isntance and persists it to the DB.
	 * 
	 * @param category
	 */
	public void saveCategory(String categoryName, Long parentCategoryId,
			int order) {
		Category category = new Category(categoryName, parentCategoryId, order);
		ofy().save().entity(category).now();
	}

	/**
	 * Persists a list of {@link Category} instances.
	 * 
	 * @param categories
	 */
	public void batchSave(List<Category> categories) {
		ofy().save().entities(categories).now();
	}

	/**
	 * Returns a list of direct subcategories.
	 * 
	 * @param parentCategoryId
	 * @return
	 */
	public List<Category> getDirectSubCategories(Long parentCategoryId) {
		return ofy().load().type(Category.class)
				.filter("parentCategoryId", parentCategoryId).order("order")
				.list();
	}

	/**
	 * Gets subcategories and adds them to the list.
	 * 
	 * @param categoryId
	 * @param list
	 */
	public void getSubCategories(Long categoryId, List<Category> list) {
		List<Category> subCategories = ofy().load().type(Category.class)
				.filter("parentCategoryId", categoryId).order("order").list();
		if (!subCategories.isEmpty()) {
			list.addAll(subCategories);
			for (Category subCategory : subCategories) {
				getSubCategories(subCategory.getId(), list);
			}
		}
	}

	/*************************************
	 * PRIVATE METHODS
	 *************************************/

	/**
	 * Gets a list of categories and adds them to the parent's list of children.
	 * Uses recursion to construct the Tree.
	 * 
	 * @param target
	 */
	private void addSubCategories(TreeNode<Category, Entry> target) {
		if(target == null || target.getData() == null) return;
		List<Category> subCategories = ofy().load().type(Category.class)
				.filter("parentCategoryId", 
						target
						.getData()
						.getId())
				.order("order").list();

		for (Category subCategory : subCategories) {
			TreeNode<Category, Entry> subC = new TreeNode<Category, Entry>(
					target, subCategory);
			target.addChild(subC);
			addSubCategories(subC);
		}
	}

}