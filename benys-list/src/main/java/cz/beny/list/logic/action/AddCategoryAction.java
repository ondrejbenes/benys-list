package cz.beny.list.logic.action;

import cz.beny.list.model.Category;

/**
 * When AddCategoryAction is instantiated, a new {@link Category} is created.
 * When the action is executed, the Category is added as the last in the list of
 * sibling Categories. 
 * When it is reverted, the Category is deleted.
 * 
 */
public class AddCategoryAction extends AbstractAction {

	private Category category;

	public AddCategoryAction(String categoryName, Long parentCategoryId) {
		super();
		int order = categoryService.getHighestOrderInCategory(parentCategoryId) + 1;
		this.category = new Category(categoryName, parentCategoryId, order);
	}

	@Override
	public void execute() {
		categoryService.saveCategory(category);
	}

	@Override
	public void revert() {
		categoryService.deleteCategory(category.getId());
	}
}