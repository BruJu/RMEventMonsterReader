package fr.bruju.rmeventreader.actionmakers.xml;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class UtilXML {
	private UtilXML() {}
	
	public static Document lireDocument(String chemin) {
		try {
			File file = new File(chemin);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			InputStream inputStream = new FileInputStream(file);
			InputSource is = new InputSource(inputStream);
			is.setEncoding("windows-1252");
			Document document = builder.parse(is);
			
			return document;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Node chercherID(NodeList eventNodes, int idEvent) {
		String id = transformerId(idEvent);
		
		for (int i = 0 ; i != eventNodes.getLength() ; i++) {
			Node node = eventNodes.item(i);
			if (node.getAttributes().getNamedItem("id").getTextContent().equals(id)) {
				return node;
			}
		}
		
		return null;
	}

	public static String transformerId(int idEvent) {
		String str = Integer.toString(idEvent);
		
		while (str.length() < 4) {
			str = "0" + str;
		}
		
		return str;
	}

	public static Node chercherPage(Node eventNode, int idPage) {
		eventNode = extraire(eventNode, "pages");
		
		String id = transformerId(idPage);
		
		NodeList children = eventNode.getChildNodes();
		
		for (int i = 0 ; i != children.getLength() ; i++) {
			Node node = children.item(i);
			
			if (node.getNodeName().equals("EventPage")
					&& node.getAttributes().getNamedItem("id").getTextContent().equals(id))
				return node;
		}
		
		return null;
	}
	
	public static Node goToEventCommands(Node page) {
		return extraire(page, "event_commands");
	}
	

	private static Node extraire(Node node, String childNodeSearched) {
		NodeList children = node.getChildNodes();
		
		for (int i = 0 ; i != children.getLength() ; i++) {
			Node child = children.item(i);
			
			if (child.getNodeName().equals(childNodeSearched)) {
				return child;
			}
		}
		
		return null;
	}

	public static List<Node> extraireEvenements(Node event_commands) {
		List<Node> nodes = new ArrayList<>();
		
		NodeList children = event_commands.getChildNodes();

		for (int i = 0 ; i != children.getLength() ; i++) {
			Node node = children.item(i);
			
			if (node.getNodeName().equals("EventCommand"))
				nodes.add(node);
		}
		
		return nodes;
	}
	
	
	
}
