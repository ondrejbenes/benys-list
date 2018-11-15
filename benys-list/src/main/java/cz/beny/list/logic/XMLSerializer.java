package cz.beny.list.logic;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactoryConfigurationError;

/**
 * XML Marshaller.
 *
 */
public interface XMLSerializer {
	/**
	 * Creates backup XML and transforms it to the pass outputStream.
	 * @param outputStream
	 * @throws ParserConfigurationException
	 * @throws TransformerConfigurationException
	 * @throws TransformerFactoryConfigurationError
	 * @throws TransformerException
	 */
	void createBackupXML(Result outputStream) throws ParserConfigurationException, TransformerConfigurationException, TransformerFactoryConfigurationError, TransformerException;

}
