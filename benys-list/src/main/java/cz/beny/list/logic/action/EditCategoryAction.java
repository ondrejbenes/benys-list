package cz.beny.list.logic.action;

import cz.beny.list.model.Category;

/**
 * When EditCategoryAction is instantiated, a {@link Category} with matching Id is loaded from DB.
 * When the action is executed, the Category is updated with values passed in constructor.
 * When it is reverted, the Category is returned to the state before execution.
 * 
 */
public class EditCategoryAction extends AbstractAction {

	private Category categoreBeforeEdit;
	private Long categoryId;
	private String newName;
	private Long newParentCategoryId;
	private int newOrder;

	public EditCategoryAction(Long categoryId, Long newParentCategoryId,
			String newName, int newOrder) {
		super();
		Category category = categoryService.getCategoryById(categoryId);
		this.categoreBeforeEdit = new Category(category.getName(),
				category.getParentCategoryId(), category.getOrder());
		this.categoryId = categoryId;
		this.newName = newName;
		this.newParentCategoryId = newParentCategoryId;
		this.newOrder = newOrder;
	}

	@Override
	public void execute() {
		categoryService.updateCategory(categoryId, newParentCategoryId,
				newName, newOrder);
	}

	@Override
	public void revert() {
		categoryService.updateCategory(categoryId,
				categoreBeforeEdit.getParentCategoryId(),
				categoreBeforeEdit.getName(), categoreBeforeEdit.getOrder());
	}

}
