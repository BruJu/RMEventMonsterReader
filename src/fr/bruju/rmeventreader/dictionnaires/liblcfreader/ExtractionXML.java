package fr.bruju.rmeventreader.dictionnaires.liblcfreader;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML;

import static fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML.streamXML;

public class ExtractionXML {

	public String[] extraireDictionnaire(String chemin, String nomGroupe, String nomElement) {
		Document doc = Utilitaire_XML.lireDocument(chemin);
		if (doc == null)
			return null;

		return streamXML(doc.getElementsByTagName(nomGroupe).item(0).getChildNodes())
				.filter(node -> node.getNodeName().equals(nomElement))
				.map(node -> Utilitaire_XML.extraireFils(node, "name"))
				.collect(Collectors.toList())
				.toArray(new String[0]);
	}
	
	public static String getIdStr(Node node) {
		return node.getAttributes().getNamedItem("id").getTextContent();
	}
	
	public static int getId(Node node) {
		return Integer.parseInt(getIdStr(node));
	}

	public static List<Node> extraireEvenements(Node event_commands) {
		return streamXML(event_commands.getChildNodes())
					.filter(node -> node.getNodeName().equals("EventCommand"))
					.collect(Collectors.toList());
	}

	public static LinkedHashMap<String, String[]> extraireDonnees(String fichier, String xPath, String[] fils) {
		NodeList nodes = (NodeList) Utilitaire_XML.extraireDepuisXPath(fichier, xPath, XPathConstants.NODESET);

		if (nodes == null)
			return null;

		LinkedHashMap<String, String[]> map = new LinkedHashMap<>();

		streamXML(nodes).forEach(node -> {
			String id = getIdStr(node);
			String[] donneesFils = new String[fils.length];

			for (int j = 0; j != fils.length; j++) {
				donneesFils[j] = Utilitaire_XML.extraireFils(node, fils[j]);
			}

			map.put(id, donneesFils);
		});

		return map;
	}
}
