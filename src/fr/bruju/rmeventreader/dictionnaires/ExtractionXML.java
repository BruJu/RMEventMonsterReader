package fr.bruju.rmeventreader.dictionnaires;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


public class ExtractionXML {

	
	public String[] extraireDictionnaire(String chemin, String nomGroupe, String nomElement) {
		Document doc = UtilXML.lireDocument(chemin);
		if (doc == null) return null;
		
		NodeList children = doc.getElementsByTagName(nomGroupe).item(0).getChildNodes();
		List<String> donnees = new ArrayList<>();
		
		for (int i = 0 ; i != children.getLength() ; i++) {
			Node node = children.item(i);
			
			if (!node.getNodeName().equals(nomElement)) {
				continue;
			}
			
			String nom = extraireFils(node, "name");
			
			donnees.add(nom);
		}
		
		return donnees.toArray(new String[0]);
		
		
	}
	
	private static String extraireFils(Node node, String childNodeSearched) {
		NodeList children = node.getChildNodes();
		
		for (int i = 0 ; i != children.getLength() ; i++) {
			Node child = children.item(i);
			
			if (child.getNodeName().equals(childNodeSearched)) {
				return child.getTextContent();
			}
		}
		
		return null;
	}
	
	
	public static LinkedHashMap<String, String[]> extraireDonnees(String fichier, String xPath, String[] fils) {
		Document doc = UtilXML.lireDocument(fichier);
		if (doc == null) return null;
		
		XPathFactory xpathFactory = XPathFactory.newInstance();
		XPath xpath = xpathFactory.newXPath();
		XPathExpression expr;
		try {
			expr = xpath.compile(xPath);
			NodeList nodes = (NodeList) expr.evaluate(doc, XPathConstants.NODESET);
			
			LinkedHashMap<String, String[]> map = new LinkedHashMap<>();
			
			for (int i = 0 ; i != nodes.getLength() ; i++) {
				Node node = nodes.item(i);
				
				String id = node.getAttributes().getNamedItem("id").getNodeValue();
				String[] donneesFils = new String[fils.length];
				
				for (int j = 0 ; j != fils.length ; j++) {
					donneesFils[j] = extraireFils(node, fils[j]);
				}
				
				map.put(id, donneesFils);
			}
			
			return map;
		} catch (XPathExpressionException e) {
			e.printStackTrace();
			return null;
		}		
		
	}
	
}
