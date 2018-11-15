package cz.beny.list.logic.action;

/**
 * Used to manage Actions.
 * 
 */
public interface ActionManager {

	/**
	 * Executes action, adds it to the undo stack and clears the redo stack.
	 * 
	 * @param action
	 */
	void executeAction(AbstractAction action);

	/**
	 * Returns true, if there are any actions that can be undone.
	 * @return
	 */
	boolean canUndo();

	/**
	 * Returns true, if there are any actions that can be redone.
	 * @return
	 */
	boolean canRedo();

	/**
	 * Undoes the action at the top of the undo stack, adds it to the redo stack.
	 */
	void undo();

	/**
	 * Redoes the action at the top of the redo stack, adds it to the undo stack.
	 */
	void redo();

	/**
	 * Clears both the undo and redo stacks.
	 */
	void clear();
}