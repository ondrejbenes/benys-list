package cz.beny.list.logic.action;

import java.util.ArrayList;
import java.util.List;

import cz.beny.list.model.Category;
import cz.beny.list.model.Entry;

/**
 * When RemoveCategoryAction is instantiated, a {@link Category} with matching Id is loaded from DB.
 * When the action is executed, the Category is deleted.
 * When it is reverted, the Category is persisted back to the DB.
 * 
 */
public class RemoveCategoryAction extends AbstractAction {
	
	private Category category;
	private List<Entry> entries;
	private List<Category> subCategories;

	public RemoveCategoryAction(Long categoryId) {
		super();
		this.category = categoryService.getCategoryById(categoryId);
		subCategories = new ArrayList<>();
		categoryService.getSubCategories(categoryId, subCategories);
		entries = entryService.getEntriesByCategoryId(category.getId());
		for(Category subC : subCategories) {
			entries.addAll(entryService.getEntriesByCategoryId(subC.getId()));
		}		
	}

	@Override
	public void execute() {
		categoryService.deleteCategory(category.getId());		
	}

	@Override
	public void revert() {
		categoryService.saveCategory(category);
		categoryService.batchSave(subCategories);
		entryService.batchSave(entries);
	}

}