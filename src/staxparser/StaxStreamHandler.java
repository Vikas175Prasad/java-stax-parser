package staxparser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.stream.XMLStreamWriter;

public class StaxStreamHandler {

	public StringBuffer processXmlFile(File xmlFile)
			throws FileNotFoundException, XMLStreamException, FactoryConfigurationError {

		StringBuffer rawXML = new StringBuffer();
		XMLStreamReader xmlStreamReader = (XMLInputFactory.newInstance()
				.createXMLStreamReader(new FileInputStream(xmlFile)));
		while (xmlStreamReader.hasNext()) {
			switch (xmlStreamReader.next()) {
			case XMLStreamConstants.START_ELEMENT:
				rawXML.append("<" + xmlStreamReader.getLocalName() + ">");
				break;

			case XMLStreamConstants.CHARACTERS:
				if (!xmlStreamReader.isWhiteSpace())
					rawXML.append(xmlStreamReader.getText());
				break;
			case XMLStreamConstants.END_ELEMENT:
				rawXML.append("</" + xmlStreamReader.getLocalName() + ">");
				break;
			default:
				break;
			}
		}
		return rawXML;

	}

	String[][] tutorials = { { "Factory Method", "Abstract Factory", "Intro to JMS" },
			{ "Intro to JMS", "Point to Point", "Publish/Subscribe" } };

	/*********** XMLSteamWriter *************/

	public void writeXML(XMLStreamWriter xmlStreamWriter) throws XMLStreamException {

		xmlStreamWriter.writeStartDocument("1.0");
		xmlStreamWriter.writeStartElement("channel");
		xmlStreamWriter.writeAttribute("name", "Vikas");
		populateTopic(xmlStreamWriter, "OOPS", tutorials[0]);
		populateTopic(xmlStreamWriter, "Java", tutorials[1]);
		xmlStreamWriter.writeEndElement();
		xmlStreamWriter.writeEndDocument();
		xmlStreamWriter.flush();

	}

	public void populateTopic(XMLStreamWriter xmlStreamWriter, String topic, String[] tutorials)
			throws XMLStreamException {

		xmlStreamWriter.writeStartElement("topic");
		xmlStreamWriter.writeAttribute("name", topic);
		int i = 0;
		while (i < tutorials.length) {
			xmlStreamWriter.writeStartElement("tutorial");
			xmlStreamWriter.writeCharacters(tutorials[i++]);
			xmlStreamWriter.writeEndElement();

		}

		xmlStreamWriter.writeEndElement();
	}

	/******************
	 * XMLEventHandler
	 * 
	 * @throws FactoryConfigurationError
	 * @throws XMLStreamException
	 **********************/

	public String writeXML() throws XMLStreamException, FactoryConfigurationError {

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		XMLEventWriter xmlEventWriter = (XMLOutputFactory.newInstance().createXMLEventWriter(byteArrayOutputStream));
		XMLEventFactory xmlEventFactory = XMLEventFactory.newInstance();
		xmlEventWriter.add(xmlEventFactory.createStartDocument());

		xmlEventWriter.add(xmlEventFactory.createStartElement("", "", "channel"));
		xmlEventWriter.add(xmlEventFactory.createAttribute("name", "Vikas"));

		populateTopic(xmlEventFactory, xmlEventWriter, "OOPS", tutorials[0]);
		populateTopic(xmlEventFactory, xmlEventWriter, "Java", tutorials[1]);
		xmlEventWriter.add(xmlEventFactory.createEndElement("", "", "channel"));
		xmlEventWriter.add(xmlEventFactory.createEndDocument());
		xmlEventWriter.flush();
		xmlEventWriter.close();
		return byteArrayOutputStream.toString();
	}

	public void populateTopic(XMLEventFactory xmlEventFactory, XMLEventWriter xmlEventWriter, String topic,
			String[] tutorials) throws XMLStreamException {

		xmlEventWriter.add(xmlEventFactory.createStartElement("", "", "topic"));
		xmlEventWriter.add(xmlEventFactory.createAttribute("name", topic));
		int i = 0;
		while (i < tutorials.length) {
			xmlEventWriter.add(xmlEventFactory.createStartElement("", "", "tutorial"));
			xmlEventWriter.add(xmlEventFactory.createCharacters(tutorials[i++]));
			xmlEventWriter.add(xmlEventFactory.createEndElement("", "", "tutorial"));
		}
		xmlEventWriter.add(xmlEventFactory.createEndElement("", "", "topic"));

	}

}
