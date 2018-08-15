package fr.bruju.rmeventreader.dictionnaires;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.bruju.rmeventreader.utilitaire.Triplet;

public class ExtractionXML {

	public String[] extraireDictionnaire(String chemin, String nomGroupe, String nomElement) {
		Document doc = UtilXML.lireDocument(chemin);
		if (doc == null)
			return null;

		NodeList children = doc.getElementsByTagName(nomGroupe).item(0).getChildNodes();
		List<String> donnees = new ArrayList<>();

		for (int i = 0; i != children.getLength(); i++) {
			Node node = children.item(i);

			if (!node.getNodeName().equals(nomElement)) {
				continue;
			}

			String nom = extraireFils(node, "name");

			donnees.add(nom);
		}

		return donnees.toArray(new String[0]);

	}
	
	public static Node chercherFils(Node node, String nomFils) {
		NodeList children = node.getChildNodes();

		for (int i = 0; i != children.getLength(); i++) {
			Node child = children.item(i);

			if (child.getNodeName().equals(nomFils)) {
				return child;
			}
		}

		return null;
	}

	public static String extraireFils(Node node, String childNodeSearched) {
		Node fils = chercherFils(node, childNodeSearched);
		
		return fils == null ? null : fils.getTextContent();
	}

	public static Object extraireDepuisXPath(String fichier, String xPath, QName returnType) {
		Document doc = UtilXML.lireDocument(fichier);
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

	public static LinkedHashMap<String, String[]> extraireDonnees(String fichier, String xPath, String[] fils) {
		NodeList nodes = (NodeList) extraireDepuisXPath(fichier, xPath, XPathConstants.NODESET);

		if (nodes == null)
			return null;

		LinkedHashMap<String, String[]> map = new LinkedHashMap<>();

		for (int i = 0; i != nodes.getLength(); i++) {
			Node node = nodes.item(i);

			String id = node.getAttributes().getNamedItem("id").getNodeValue();
			String[] donneesFils = new String[fils.length];

			for (int j = 0; j != fils.length; j++) {
				donneesFils[j] = extraireFils(node, fils[j]);
			}

			map.put(id, donneesFils);
		}

		return map;

	}
	
	public static Triplet<Long, String, int[]> decrypterNoeudEventCommand(Node item) {
		// Lecture du noeud
		NodeList children = item.getChildNodes();
		
		String code = null;
		String string = null;
		String parameters = null;
		
		for (int i = 0 ; i != children.getLength() ; i++) {
			Node child = children.item(i);
			
			if (child.getNodeName().equals("code")) {
				code = child.getTextContent();
			}
			if (child.getNodeName().equals("string")) {
				string = child.getTextContent();
			}
			if (child.getNodeName().equals("parameters")) {
				parameters = child.getTextContent();
			}
		}
		
		if (code == null || string == null || parameters == null) {
			throw new RuntimeException("Element invalide");
		}
		
		// Decodage
		long codeD = Long.valueOf(code);
		int[] parametersD = decrypterParameters(parameters);
		
		Triplet<Long, String, int[]> triplet = new Triplet<>(codeD, string, parametersD);
		return triplet;
	}

	private static int[] decrypterParameters(String parameters) {
		if (parameters.length() == 0) {
			return new int[0];
		}
		
		String[] parametersS = parameters.split(" ");
		int[] parametersD = new int[parametersS.length];
		for (int i = 0 ; i != parametersS.length ; i++) {
			parametersD[i] = Integer.decode(parametersS[i]);
		}
		
		return parametersD;
	}

}
