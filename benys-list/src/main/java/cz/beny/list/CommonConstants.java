package cz.beny.list;

/**
 * Constants used in the whole app scope.
 *
 */
public final class CommonConstants {
	private CommonConstants() {	}
	
	public static final Long ROOT_CATEGORY_ID = new Long(1L);
	
	public static final String VIEW_NAME_HOME = "home";
	public static final String VIEW_NAME_LIST = "list";
	public static final String VIEW_NAME_UPLOAD_FILE_FORM = "upload-file-form";
	
	public static final String URL_HOME = "/";
	public static final String URL_LIST = "/list";
	
	public static final String PAR_CATEGORY_TREE = "categoryTree";
	public static final String PAR_CAN_UNDO = "canUndo";
	public static final String PAR_CAN_REDO = "canRedo";
}
