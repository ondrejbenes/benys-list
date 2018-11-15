package cz.beny.list.logic;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

/**
 * XML Unmarshaller.
 *
 */
public interface XMLDeserializer {
	/**
	 * Restores DB from XML.
	 * @param xml
	 * @throws SAXException
	 * @throws IOException
	 * @throws ParserConfigurationException
	 */
	void restoreBackupFromXML(String xml) throws SAXException, IOException, ParserConfigurationException;
}
