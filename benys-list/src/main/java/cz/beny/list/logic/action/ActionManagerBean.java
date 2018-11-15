package cz.beny.list.logic.action;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * Implementaion of {@link ActionManager}
 *
 */
public class ActionManagerBean implements ActionManager {
	private final static Integer DEQUE_CAPACITY = 10;
	
	private Deque<AbstractAction> undo;
	private Deque<AbstractAction> redo;
	
	public ActionManagerBean() {
		this.undo = new ArrayDeque<>();
		this.redo = new ArrayDeque<>();
	}

	@Override
	public void executeAction(AbstractAction action) {
		action.execute();
		if(undo.size() >= DEQUE_CAPACITY) {
			while(undo.size() >= DEQUE_CAPACITY)
				undo.removeFirst();
		}
		undo.addLast(action);
		redo.clear();
	}
	
	@Override
	public boolean canUndo() {
		return !undo.isEmpty();
	}
	
	@Override
	public boolean canRedo() {
		return !redo.isEmpty();
	}
	
	@Override
	public void undo() {
		AbstractAction action = undo.removeLast();
		action.revert();
		redo.addLast(action);
	}
	
	@Override
	public void redo() {
		AbstractAction action = redo.removeLast();
		action.execute();
		undo.addLast(action);
	}

	@Override
	public void clear() {
		undo.clear();
		redo.clear();
	}
}