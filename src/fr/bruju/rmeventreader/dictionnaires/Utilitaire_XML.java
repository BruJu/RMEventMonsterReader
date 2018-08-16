package fr.bruju.rmeventreader.dictionnaires;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class Utilitaire_XML {
	private Utilitaire_XML() {}
	
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
		} catch (FileNotFoundException e) {
			// TODO : relancer les FileNotFoundException ?
			return null;
		} catch (ParserConfigurationException | SAXException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String transformerId(int idEvent) {
		return transformerId(idEvent, 4);
	}
	
	public static String transformerId(int idEvent, int nbChiffres) {
		String str = Integer.toString(idEvent);
		
		while (str.length() < nbChiffres) {
			str = "0" + str;
		}
		
		return str;
	}


	

	
	public static Node chercherFils(Node node, String nomFils) {
		return search(node.getChildNodes(), child -> child.getNodeName().equals(nomFils));
	}
	
	public static String extraireFils(Node node, String childNodeSearched) {
		Node fils = chercherFils(node, childNodeSearched);
		
		return fils == null ? null : fils.getTextContent();
	}	
	
	public static Object extraireDepuisXPath(String fichier, String xPath, QName returnType) {
		Document doc = Utilitaire_XML.lireDocument(fichier);
		if (doc == null)
			return null;

		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr;
		try {
			expr = xpath.compile(xPath);
			return expr.evaluate(doc, returnType);
		} catch (XPathExpressionException e) {
			return null;
		}
	}
	
	/* ===========================================
	 * Exploration des noeuds en style fonctionnel
	 * =========================================== */
	
	public static Stream<Node> streamXML(NodeList nodeList) {
		Iterator<Node> iterator = new IterateurNodeList(nodeList);
		Iterable<Node> iterable = () -> iterator;
		return StreamSupport.stream(iterable.spliterator(), false);
	}
	
	private static class IterateurNodeList implements Iterator<Node> {
		private NodeList nodeList;
		private int i;

		public IterateurNodeList(NodeList nodeList) {
			this.nodeList = nodeList;
			this.i = 0 ;
		}
		
		@Override
		public boolean hasNext() {
			return i == nodeList.getLength();
		}

		@Override
		public Node next() {
			return nodeList.item(i++);
		}
	}
	
	public static Node search(NodeList nodeList, Predicate<Node> predicat) {
		for (int i = 0; i != nodeList.getLength(); i++) {
			Node item = nodeList.item(i);
			if (predicat.test(item))
				return item;
		}
		
		return null;
	}
}
