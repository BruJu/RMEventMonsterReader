package fr.bruju.rmeventreader.dictionnaires.liblcfreader;

import static fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML.streamXML;

import java.util.LinkedHashMap;
import java.util.function.Function;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML;
import fr.bruju.rmeventreader.dictionnaires.modele.ElementComposite;
import fr.bruju.rmeventreader.dictionnaires.modele.Evenement;
import fr.bruju.rmeventreader.dictionnaires.modele.EvenementCommun;
import fr.bruju.rmeventreader.dictionnaires.modele.Instruction;
import fr.bruju.rmeventreader.dictionnaires.modele.Page;

public class InstancieurXML {
	public static Evenement evenement(Node eventNode) {
		int id = ExtractionXML.getId(eventNode);
		String nom = Utilitaire_XML.extraireFils(eventNode, "name");
		int x = Integer.parseInt(Utilitaire_XML.extraireFils(eventNode, "x"));
		int y = Integer.parseInt(Utilitaire_XML.extraireFils(eventNode, "y"));

		return new Evenement(id, nom, x, y);
	}

	public static EvenementCommun evenementCommun(Node eventNode) {
		int id = ExtractionXML.getId(eventNode);
		String nom = Utilitaire_XML.extraireFils(eventNode, "name");
		int trigger = Integer.parseInt(Utilitaire_XML.extraireFils(eventNode, "trigger"));
		int switch_id = Integer.parseInt(Utilitaire_XML.extraireFils(eventNode, "switch_id"));
		boolean switch_flag = Utilitaire_XML.extraireFils(eventNode, "switch_flag").equals("T");
		
		return new EvenementCommun(id, nom, trigger, (switch_flag) ? switch_id : -1);
	}
	
	public static Instruction instruction(Node noeud) {
		// Lecture du noeud
		NodeList children = noeud.getChildNodes();
		
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
		
		return new Instruction((int) codeD, string, parametersD);
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
	
	public static Page page(Node node) {
		int id = ExtractionXML.getId(node);
		LinkedHashMap<String, int[]> map = new LinkedHashMap<>();

		Node condition = Utilitaire_XML.chercherFils(node, "condition");
		condition = Utilitaire_XML.chercherFils(condition, "EventPageCondition");

		Node flags = Utilitaire_XML.chercherFils(condition, "flags");
		flags = Utilitaire_XML.chercherFils(flags, "EventPageCondition_Flags");

		extraireID(map, condition, flags, new String[] { "switch_a", "switch_b", "item", "actor" }, "_id");
		extraireID(map, condition, flags, new String[] { "timer", "timer2" }, "_sec");
		
		if (Utilitaire_XML.extraireFils(flags, "variable").equals("T")) {
			map.put("variable", new int[] { Integer.parseInt(Utilitaire_XML.extraireFils(condition, "variable_id")),
					Integer.parseInt(Utilitaire_XML.extraireFils(condition, "variable_value")),
					Integer.parseInt(Utilitaire_XML.extraireFils(condition, "compare_operator"))
			});
		}
		
		return new Page(id, map);
	}

	private static void extraireID(LinkedHashMap<String, int[]> map, Node condition, Node flags, String[] cles,
			String suffixe) {
		for (String cle : cles) {
			if (Utilitaire_XML.extraireFils(flags, cle).equals("T")) {
				map.put(cle, new int[] { Integer.parseInt(Utilitaire_XML.extraireFils(condition, cle + suffixe)) });
			}
		}
	}
	
	public static <S, T extends ElementComposite<S>> T construire(Node node,
			String nomGroupeDeFils, String nomElementFils,
			Function<Node, T> fonctionInstanciation, Function<Node, S> creationDeFils) {
		T elementPere = fonctionInstanciation.apply(node);

		Node pagesFils = Utilitaire_XML.chercherFils(node, nomGroupeDeFils);

		streamXML(pagesFils.getChildNodes())
			.filter(n -> n.getNodeName().equals(nomElementFils))
			.forEach(n -> elementPere.ajouter(creationDeFils.apply(n)));
		
		return elementPere;
	}
}
