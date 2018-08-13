package fr.bruju.rmeventreader.actionmakers.xml;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class UtilXML {
	private UtilXML() {}
	
	public static Document lireDocument(String chemin) {
		try {
			File file = new File(chemin);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document document = builder.parse(file);
			
			return document;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Node chercherID(NodeList eventNodes, int idEvent) {
		return null;
	}

	public static Node chercherPage(Node eventNode, int idPage) {
		return null;
	}

	public static NodeList extraireEvenements(Node eventPage) {
		return null;
	}
	
	
	
}
