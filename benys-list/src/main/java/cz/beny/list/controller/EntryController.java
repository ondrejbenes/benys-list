package cz.beny.list.controller;

import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import cz.beny.list.CommonConstants;
import cz.beny.list.db.service.EntryService;
import cz.beny.list.logic.action.AbstractAction;
import cz.beny.list.logic.action.ActionManager;
import cz.beny.list.logic.action.AddEntryAction;
import cz.beny.list.logic.action.EditEntryAction;
import cz.beny.list.logic.action.RemoveEntryAction;
import cz.beny.list.model.Entry;

@Controller
public class EntryController {

	@Autowired
	private EntryService entryService;

	@Autowired
	private ActionManager actionManager;

	/**
	 * Creates {@link AddEntryAction} and executes it.
	 * 
	 * @param hyperlink
	 * @param note
	 * @param categoryId
	 * @return
	 */
	@RequestMapping(value = "/addEntry", method = RequestMethod.POST)
	public RedirectView addEntry(
			@RequestParam(value = "hyperlink", required = true) String hyperlink,
			@RequestParam(value = "note", required = true) String note,
			@RequestParam(value = "categoryId", required = true) Long categoryId) {
		
		if(note.length() >= 100)
			throw new IllegalArgumentException("Note too long!");

		AbstractAction action = new AddEntryAction(hyperlink, note, categoryId);
		actionManager.executeAction(action);

		final RedirectView rv = new RedirectView(CommonConstants.VIEW_NAME_LIST);
		return rv;
	}

	/**
	 * Based on user input, creates either a {@link RemoveEntryAction} or
	 * {@link EditEntryAction} and executes it.
	 * 
	 * @param entryId
	 * @param action
	 * @param newHyperlink
	 * @param newNote
	 * @return
	 */
	@RequestMapping(value = "/editOrRemoveEntry", method = RequestMethod.POST)
	public RedirectView editOrRemoveEntry(
			@RequestParam(value = "entryId", required = true) Long entryId,
			@RequestParam(value = "action", required = true) String action,
			@RequestParam(value = "newHyperlink") String newHyperlink,
			@RequestParam(value = "newNote") String newNote) {

		AbstractAction entryAction;

		if (action.equals("x")) {
			entryAction = new RemoveEntryAction(entryId);
			actionManager.executeAction(entryAction);
		} else if (action.equals("Save")) {
			
			if(newNote.length() >= 100)
				throw new IllegalArgumentException("Note too long!");
			
			Entry entry = entryService.getEntryById(entryId);
			entryAction = new EditEntryAction(entryId, entry.getCategoryId(),
					newHyperlink, newNote, entry.getOrder());
			actionManager.executeAction(entryAction);
		}

		final RedirectView rv = new RedirectView(CommonConstants.VIEW_NAME_LIST);
		return rv;
	}

	/**
	 * Called using AJAX. Adjusts {@link Entry} order and changes the category
	 * of that Entry, if it has changed.
	 * 
	 * @param body
	 * @return
	 */
	@RequestMapping(value = "/sortEntry", method = RequestMethod.POST)
	public RedirectView sortEntry(@RequestBody String body) {

		Long entryId = null;
		Long categoryId = null;

		List<Long> orderedListOfIds = new LinkedList<>();
		String[] tokens = body.split("&");
		for (String token : tokens) {
			String firstVal = token.split("=")[0];
			String secondVal = token.split("=")[1];

			if (firstVal.equals("entryId")) {
				entryId = Long.valueOf(secondVal);
			} else if (firstVal.equals("categoryId")) {
				categoryId = Long.valueOf(secondVal);
			} else {
				orderedListOfIds.add(Long.valueOf(secondVal));
			}
		}

		Entry entry = entryService.getEntryById(entryId);
		Long originalCategoryId = entry.getCategoryId();
		if (originalCategoryId != categoryId) {
			entry.setCategoryId(categoryId);
			entryService.saveEntry(entry);
		}
		entryService.adjustOrder(orderedListOfIds);

		final RedirectView rv = new RedirectView(CommonConstants.VIEW_NAME_LIST);
		return rv;
	}

	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception exception) {
		System.out.println("In ex handler");
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("error");
		return mav;
	}
}
