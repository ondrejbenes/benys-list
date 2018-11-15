package cz.beny.list.controller;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.stream.StreamResult;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import org.xml.sax.SAXException;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;

import cz.beny.list.CommonConstants;
import cz.beny.list.db.service.CategoryService;
import cz.beny.list.db.service.EntryService;
import cz.beny.list.logic.LoginManager;
import cz.beny.list.logic.XMLDeserializer;
import cz.beny.list.logic.XMLSerializer;
import cz.beny.list.logic.action.ActionManager;
import cz.beny.list.model.Category;
import cz.beny.list.model.Entry;
import cz.beny.util.tree.TreeNode;

@Controller
public class ListController {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private EntryService entryService;

	@Autowired
	private ActionManager actionManager;

	@Autowired
	private XMLSerializer xmlSerializer;

	@Autowired
	private XMLDeserializer xmlDeserializer;

	/**
	 * Gets the whole list and passes it to the View. Also finds out, if it is
	 * possible to undo and redo actions.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/list")
	public ModelAndView showList() {

		if (!LoginManager.isValidUserLoggedIn())
			return new ModelAndView(new RedirectView(CommonConstants.URL_HOME));

		final ModelAndView mv = new ModelAndView(CommonConstants.VIEW_NAME_LIST);

		final TreeNode<Category, Entry> tree = categoryService
				.getCategoryTree(CommonConstants.ROOT_CATEGORY_ID);
		entryService.populateCategoryTree(tree);

		mv.addObject(CommonConstants.PAR_CATEGORY_TREE, tree);

		mv.addObject(CommonConstants.PAR_CAN_UNDO, actionManager.canUndo());

		mv.addObject(CommonConstants.PAR_CAN_REDO, actionManager.canRedo());

		return mv;
	}

	/**
	 * Calls {@link XMLSerializer} to get a backup of entries and categories.
	 * The file is sent as a response.
	 * 
	 * @param response
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	@RequestMapping(value = "/createBackup")
	public void createBackup(HttpServletResponse response) throws IOException,
			ParserConfigurationException, TransformerFactoryConfigurationError,
			TransformerException {
		if (!LoginManager.isValidUserLoggedIn())
			return;

		initResponse(response);
		xmlSerializer.createBackupXML(new StreamResult(response
				.getOutputStream()));
	}

	/**
	 * Gets the upload URL so that the uploaded backup can be sent to Google
	 * Blobstore.
	 * 
	 * @return
	 */
	@RequestMapping(value = "/restore-from-backup")
	public ModelAndView restoreFromBackup() {
		final ModelAndView mv = new ModelAndView(
				CommonConstants.VIEW_NAME_UPLOAD_FILE_FORM);

		mv.addObject("loggedIn", LoginManager.isValidUserLoggedIn());

		BlobstoreService blobStoreService = BlobstoreServiceFactory
				.getBlobstoreService();

		final String uploadUrl = blobStoreService
				.createUploadUrl("/restore-from-backup-form");
		mv.addObject("uploadUrl", uploadUrl);

		return mv;
	}

	/**
	 * Gets the uploaded file and calls {@link XMLDeserializer} in order to
	 * restore the DB.
	 * 
	 * @param req
	 */
	@RequestMapping(value = "/restore-from-backup-form", method = RequestMethod.POST)
	public void restoreFromBackupForm(HttpServletRequest req) throws Exception {
		BlobstoreService blobStoreService = BlobstoreServiceFactory
				.getBlobstoreService();
		BlobKey key = blobStoreService.getUploads(req).get("file").get(0);
		String xml = new String(blobStoreService.fetchData(key, 0,
				BlobstoreService.MAX_BLOB_FETCH_SIZE - 1));
		
		xmlDeserializer.restoreBackupFromXML(xml);		

		actionManager.clear();
	}

	/*************************************
	 * PRIVATE METHODS
	 *************************************/

	/**
	 * Sets the backup file name and response content type and header.
	 * @param response
	 */
	private void initResponse(HttpServletResponse response) {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		String xmlFileName = format.format(Calendar.getInstance().getTime())
				+ "-list-backup.xml";

		response.setContentType("application/csv");

		String headerKey = "Content-Disposition";
		String headerValue = String.format("attachment; filename=\"%s\"",
				xmlFileName);
		response.setHeader(headerKey, headerValue);
	}

}