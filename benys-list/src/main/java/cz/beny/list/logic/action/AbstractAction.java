package cz.beny.list.logic.action;

import org.springframework.beans.factory.annotation.Configurable;

import cz.beny.list.db.service.CategoryService;
import cz.beny.list.db.service.EntryService;
import cz.beny.list.db.service.bean.CategoryServiceBean;
import cz.beny.list.db.service.bean.EntryServiceBean;

/**
 * Ancestor of different action types.
 */
@Configurable
public abstract class AbstractAction implements Executable, Revertable {

	protected EntryService entryService;
	
	protected CategoryService categoryService;
	
	public AbstractAction() {
		entryService = new EntryServiceBean();
		categoryService = new CategoryServiceBean();		
	}
}
