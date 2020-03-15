package staxparser;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class Driver {

	public static void main(String[] args)
			throws FileNotFoundException, XMLStreamException, FactoryConfigurationError, TransformerException {

//		System.out.println(new StaxStreamHandler().processXmlFile(new File("input.xml")).toString());
		System.out.println(
				Driver.transformXML(4, (new StaxStreamHandler()).processXmlFile(new File("input.xml")).toString()));

		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		XMLStreamWriter xmlStreamWriter = (XMLOutputFactory.newInstance().createXMLStreamWriter(byteArrayOutputStream));
		(new StaxStreamHandler()).writeXML(xmlStreamWriter);
		System.out.println(transformXML(4, byteArrayOutputStream.toString()));

		System.out.println(Driver.transformXML(4, (new StaxStreamHandler().writeXML())));

	}

	public static String transformXML(int indentation, String rawXML) throws TransformerException {

		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		transformerFactory.setAttribute("indent-number", indentation);
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		StreamResult streamResult = new StreamResult(new StringWriter());
		transformer.transform(new StreamSource(new StringReader(rawXML)), streamResult);
		return streamResult.getWriter().toString();

	}

}
