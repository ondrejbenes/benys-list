package cz.beny.list.logic;

import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import cz.beny.list.db.service.CategoryService;
import cz.beny.list.db.service.EntryService;
import cz.beny.list.model.Category;
import cz.beny.list.model.Entry;

public class XMLSerializerBean implements XMLSerializer {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private EntryService entryService;

	@Override
	public void createBackupXML(Result outputStream)
			throws ParserConfigurationException,
			TransformerFactoryConfigurationError, TransformerException {

		Document document = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder().newDocument();

		Element root = document.createElement("backup");
		document.appendChild(root);

		serializeCategories(document);
		serializeEntries(document);

		Transformer transformer = TransformerFactory.newInstance()
				.newTransformer();
		transformer.transform(new DOMSource(document), outputStream);
	}

	private void serializeCategories(Document document) {
		Element categories = document.createElement("categories");

		List<Category> categoriesList = categoryService.getAllCategories();

		for (Category category : categoriesList) {
			categories.appendChild(createCategoryElement(document, category));
		}

		document.getFirstChild().appendChild(categories);
	}

	private void serializeEntries(Document document) {
		Element entries = document.createElement("entries");

		List<Entry> entriesList = entryService.getAllEntries();

		for (Entry entry : entriesList) {			
			entries.appendChild(createEntryElement(document, entry));
		}

		document.getFirstChild().appendChild(entries);
	}

	private Element createCategoryElement(Document document, Category category) {
		Element categoryElement = document.createElement("category");

		categoryElement.setAttribute("id", category.getId().toString());

		if (category.getParentCategoryId() != null)
			categoryElement.setAttribute("parentCategoryId", category
					.getParentCategoryId().toString());

		Element categoryName = document.createElement("name");
		categoryName.setTextContent(category.getName());

		Element categoryOrder = document.createElement("order");
		categoryOrder.setTextContent(Integer.toString(category.getOrder()));

		categoryElement.appendChild(categoryName);
		categoryElement.appendChild(categoryOrder);

		return categoryElement;
	}
	
	private Element createEntryElement(Document document, Entry entry) {
		Element entryElement = document.createElement("entry");

		entryElement.setAttribute("id", entry.getId().toString());
		entryElement.setAttribute("categoryId", entry.getCategoryId()
				.toString());

		Element hyperlinkElement = document.createElement("hyperlink");
		hyperlinkElement.setTextContent(entry.getHyperlink());

		Element noteElement = document.createElement("note");
		noteElement.setTextContent(entry.getNote());

		Element orderElement = document.createElement("order");
		orderElement.setTextContent(Integer.toString(entry.getOrder()));

		entryElement.appendChild(hyperlinkElement);
		entryElement.appendChild(noteElement);
		entryElement.appendChild(orderElement);
		
		return entryElement;
	}

}
