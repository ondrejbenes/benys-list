package cz.beny.list.logic;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import cz.beny.list.db.service.CategoryService;
import cz.beny.list.db.service.EntryService;
import cz.beny.list.model.Category;
import cz.beny.list.model.Entry;

public class XMLDeserializerBean implements XMLDeserializer {

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private EntryService entryService;

	@Override
	public void restoreBackupFromXML(String xml) throws SAXException,
			IOException, ParserConfigurationException {
		Document document = DocumentBuilderFactory.newInstance()
				.newDocumentBuilder()
				.parse(new InputSource(new StringReader(xml)));

		List<Category> categories = deserializeCategories(document
				.getElementsByTagName("category"));

		List<Entry> entries = deserializeEntries(document
				.getElementsByTagName("entry"));

		categoryService.deleteAllCategories();
		categoryService.batchSave(categories);
		entryService.batchSave(entries);
	}

	private List<Category> deserializeCategories(NodeList categoriesList) {
		List<Category> categories = new ArrayList<>();

		for (int i = 0; i < categoriesList.getLength(); i++) {
			Node node = categoriesList.item(i);

			if (categoriesList.item(i).getNodeType() == Node.ELEMENT_NODE) {
				categories.add(deserializeCategory((Element) node));
			}
		}

		return categories;
	}

	private List<Entry> deserializeEntries(NodeList entriesList) {
		List<Entry> entries = new ArrayList<>();

		for (int i = 0; i < entriesList.getLength(); i++) {
			Node node = entriesList.item(i);

			if (node.getNodeType() == Node.ELEMENT_NODE) {
				entries.add(deserializeEntry((Element) node));
			}
		}

		return entries;
	}

	private Category deserializeCategory(Element categoryElement) {
		Long id = Long.valueOf(categoryElement.getAttribute("id"));
		Long parentCategoryId;
		try {
			parentCategoryId = Long.valueOf(categoryElement
					.getAttribute("parentCategoryId"));
		} catch (NumberFormatException ex) {
			parentCategoryId = null;
		}
		String name = categoryElement.getElementsByTagName("name").item(0)
				.getTextContent();
		int order = Integer.valueOf(categoryElement
				.getElementsByTagName("order").item(0).getTextContent());
		return new Category(id, parentCategoryId, order, name);
	}
	
	private Entry deserializeEntry(Element entryElement) {
		Long id = Long.valueOf(entryElement.getAttribute("id"));
		Long categoryId = Long.valueOf(entryElement
				.getAttribute("categoryId"));
		String hyperlink = entryElement
				.getElementsByTagName("hyperlink").item(0)
				.getTextContent();
		String note = entryElement.getElementsByTagName("note").item(0)
				.getTextContent();
		int order = Integer
				.valueOf(entryElement.getElementsByTagName("order")
						.item(0).getTextContent());

		return new Entry(id, categoryId, order, hyperlink, note);
	}
}
