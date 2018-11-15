package cz.beny.list.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import cz.beny.list.CommonConstants;
import cz.beny.list.logic.action.ActionManager;

@Controller
public class ActionController {

	@Autowired
	private ActionManager actionManager;
	
	/**
	 * If possible, undo last action.
	 */
	@RequestMapping(value = "/undo", method = RequestMethod.POST)
	public ModelAndView undo(RedirectAttributes redirectAttributes) {
		final ModelAndView mv = new ModelAndView(new RedirectView(CommonConstants.VIEW_NAME_LIST));
		
		if(actionManager.canUndo()) {
			actionManager.undo();
		}
		
		redirectAttributes.addFlashAttribute(CommonConstants.PAR_CAN_UNDO, actionManager.canUndo());
		redirectAttributes.addFlashAttribute(CommonConstants.PAR_CAN_REDO, actionManager.canRedo());

		return mv;
	}
	
	/**
	 * If possible, redo last action.
	 */
	@RequestMapping(value = "/redo", method = RequestMethod.POST)
	public ModelAndView redo(RedirectAttributes redirectAttributes) {
		final ModelAndView mv = new ModelAndView(new RedirectView(CommonConstants.VIEW_NAME_LIST));
		
		if(actionManager.canRedo()) {
			actionManager.redo();
		}
		
		redirectAttributes.addFlashAttribute(CommonConstants.PAR_CAN_UNDO, actionManager.canUndo());
		redirectAttributes.addFlashAttribute(CommonConstants.PAR_CAN_REDO, actionManager.canRedo());

		return mv;
	}
}
