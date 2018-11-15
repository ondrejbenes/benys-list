package cz.beny.list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import cz.beny.list.CommonConstants;
import cz.beny.list.db.service.CategoryService;
import cz.beny.list.logic.CategoryOrderAdjustment;
import cz.beny.list.logic.action.AbstractAction;
import cz.beny.list.logic.action.ActionManager;
import cz.beny.list.logic.action.AddCategoryAction;
import cz.beny.list.logic.action.EditCategoryAction;
import cz.beny.list.logic.action.RemoveCategoryAction;
import cz.beny.list.model.Category;

@Controller
public class CategoryController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private ActionManager actionManager;

	/**
	 * Creates {@link AddCategoryAction} and executes it.
	 * 
	 * @param categoryName
	 * @param parentCategoryId
	 */
	@RequestMapping(value = "/addCategory", method = RequestMethod.POST)
	public RedirectView addCategory(
			@RequestParam(value = "categoryName", required = true) String categoryName,
			@RequestParam(value = "parentCategoryId", required = true) Long parentCategoryId) {
		final RedirectView rv = new RedirectView(CommonConstants.VIEW_NAME_LIST);

		AbstractAction action = new AddCategoryAction(categoryName,
				parentCategoryId);
		actionManager.executeAction(action);

		return rv;
	}

	/**
	 * Based on user input, creates either a {@link RemoveCategoryAction} or
	 * {@link EditCategoryAction} and executes it.
	 * 
	 * @param categoryName
	 * @param parentCategoryId
	 */
	@RequestMapping(value = "/editOrRemoveCategory", method = RequestMethod.POST)
	public RedirectView editOrRemoveCategory(
			@RequestParam(value = "categoryId", required = true) Long categoryId,
			@RequestParam(value = "action", required = true) String action,
			@RequestParam(value = "newCategoryName") String newCategoryName) {

		AbstractAction categoryAction;

		if (action.equals("x")) {
			categoryAction = new RemoveCategoryAction(categoryId);
			actionManager.executeAction(categoryAction);
		} else if (action.equals("Save")) {
			Category category = categoryService.getCategoryById(categoryId);
			categoryAction = new EditCategoryAction(categoryId,
					category.getParentCategoryId(), newCategoryName,
					category.getOrder());
			actionManager.executeAction(categoryAction);
		}

		final RedirectView rv = new RedirectView(CommonConstants.VIEW_NAME_LIST);
		return rv;
	}

	/**
	 * Changes the order of the {@link Category}, relative to sibling
	 * Categories. The order is either decremented or incremented.
	 * 
	 * @param categoryId
	 * @param action
	 *            decrement or increment
	 * @return
	 */
	@RequestMapping(value = "/changeCategoryOrder", method = RequestMethod.POST)
	public RedirectView changeCategoryOrder(
			@RequestParam(value = "categoryId", required = true) Long categoryId,
			@RequestParam(value = "action", required = true) String action) {
		if (action.equals("up")) {
			categoryService.adjustCategoryOrder(categoryId,
					CategoryOrderAdjustment.DECREMENT);
		} else if (action.equals("down")) {
			categoryService.adjustCategoryOrder(categoryId,
					CategoryOrderAdjustment.INCREMENT);
		}

		final RedirectView rv = new RedirectView(CommonConstants.VIEW_NAME_LIST);
		return rv;
	}
}