package fr.bruju.rmeventreader.dictionnaires;

import static fr.bruju.rmeventreader.dictionnaires.UtilXML.forEachNodes;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import javax.xml.xpath.XPathConstants;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Triplet;

/**
 * Cette classe a pour but de créer des fichiers .txt simples à partir des fichiers xml
 * 
 * @author Bruju
 *
 */
public class MiseEnCache {
	
	public void arbo(String prefixeDestination, String fichierArbre, String prefixeMaps) {
		List<String[]> s;
		try {
			s = FileReaderByLine.lireFichier(fichierArbre, 3);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		ArrayList<Pair<Integer, String>> arbo = new ArrayList<>(s.size());
		
		s.forEach(tableau -> arbo.add(new Pair<>(Integer.parseInt(tableau[1]), tableau[2])));
		
		@SuppressWarnings("unchecked")
		Pair<Integer, String>[] arbre = arbo.toArray(new Pair[0]);
		
		for (int i = 1 ; i != s.size() ; i++) {
			HeaderMap hm = new HeaderMap(i, s.get(i)[2], arbre);
			map(prefixeDestination + s.get(i)[0] + "_", hm, prefixeMaps + s.get(i)[0] + ".xml");
		}
	}
	
	
	private void map(String prefixeDestination, HeaderMap headerMap, String map) {
		NodeList nodeList = (NodeList) ExtractionXML.extraireDepuisXPath(map, "/LMU/Map/events/Event",
				XPathConstants.NODESET);

		if (nodeList == null)
			return;

		forEachNodes(nodeList, node -> mapEvent(prefixeDestination, headerMap, node));
	}

	private void mapEvent(String prefixeDestination, HeaderMap headerMap, Node node) {
		HeaderMapEvent header = HeaderMapEvent.instancier(node);

		NodeList pagesFils = ExtractionXML.chercherFils(node, "pages").getChildNodes();

		StringBuilder sb = new StringBuilder();
		
		forEachNodes(pagesFils, n -> n.getNodeName().equals("EventPage"),
				n -> ajouterSBPage(sb, mapEventPage(prefixeDestination, headerMap, header, n)));
		
		File f = new File(prefixeDestination + header.id + ".txt");

		try {
			if (f.exists())
				f.delete();

			f.createNewFile();
			FileWriter ff = new FileWriter(f);

			ff.write(sb.toString());

			ff.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	private void ajouterSBPage(StringBuilder sb, String mapEventPage) {
		sb.append("=====\n")
		  .append(mapEventPage)
		  .append("\n");
	}


	private String mapEventPage(String prefixeDestination, HeaderMap headerMap, HeaderMapEvent header, Node node) {
		HeaderPageCondition pageConditions = HeaderPageCondition.instancier(node);

		Node event_commands = ExtractionXML.chercherFils(node, "event_commands");

		StringBuilder sb = new StringBuilder();

		// Header
		
		sb.append("-- MAP --\n")
		  .append("ID ").append(headerMap.id).append("\n")
		  .append("Nom ").append(headerMap.nom).append("\n")
		  .append("CHEMIN");
		
		
		headerMap.arborescence.forEach(map -> sb.append(">").append(map));
		sb.append("\n");
		
		
		sb.append("-- EVENT --\n").append("ID ").append(header.id).append("\n").append("Nom ").append(header.nom)
				.append("\n").append("Position ").append(header.x).append(",").append(header.y).append("\n")
				.append("Page ").append(pageConditions.id).append("\n").append("\n");

		// Page
		sb.append("-- PAGE --\n");
		pageConditions.appendConditions(sb);
		sb.append("\n");

		sb.append("-- INSTRUCTIONS --\n");
		forEachNodes(event_commands.getChildNodes(), n -> n.getNodeName().equals("EventCommand"),
				n -> sb.append(traduireEvent(n)));
		
		
		return sb.toString();
		/*
		File f = new File(prefixeDestination + header.id + "_" + pageConditions.id + ".txt");

		try {
			if (f.exists())
				f.delete();

			f.createNewFile();
			FileWriter ff = new FileWriter(f);

			ff.write(sb.toString());

			ff.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		*/
	}

	private String traduireEvent(Node n) {
		Triplet<Long, String, int[]> triplet = ExtractionXML.decrypterNoeudEventCommand(n);
		
		if (triplet.a == 10L) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(triplet.a).append(" ");
		
		for(int v : triplet.c) {
			sb.append(v).append(" ");
		}
		
		sb.append("; ").append(triplet.b).append("\n");
		
		
		
		return sb.toString();
	}

	public static class HeaderPageCondition {
		public final int id;
		public final LinkedHashMap<String, int[]> conditions;

		public HeaderPageCondition(int id, LinkedHashMap<String, int[]> conditions) {
			this.id = id;
			this.conditions = conditions;
		}

		public static HeaderPageCondition instancier(Node node) {
			int id = UtilXML.getId(node);
			LinkedHashMap<String, int[]> map = new LinkedHashMap<>();

			Node condition = ExtractionXML.chercherFils(node, "condition");
			condition = ExtractionXML.chercherFils(condition, "EventPageCondition");

			Node flags = ExtractionXML.chercherFils(condition, "flags");
			flags = ExtractionXML.chercherFils(flags, "EventPageCondition_Flags");

			extraireID(map, condition, flags, new String[] { "switch_a", "switch_b", "item", "actor" }, "_id");
			extraireID(map, condition, flags, new String[] { "timer", "timer2" }, "_sec");
			
			if (ExtractionXML.extraireFils(flags, "variable").equals("T")) {
				map.put("variable", new int[] { Integer.parseInt(ExtractionXML.extraireFils(condition, "variable_id")),
						Integer.parseInt(ExtractionXML.extraireFils(condition, "variable_value")),
						Integer.parseInt(ExtractionXML.extraireFils(condition, "compare_operator"))
				});
			}
			
			return new HeaderPageCondition(id, map);
		}

		private static void extraireID(LinkedHashMap<String, int[]> map, Node condition, Node flags, String[] cles,
				String suffixe) {
			for (String cle : cles) {
				if (ExtractionXML.extraireFils(flags, cle).equals("T")) {
					map.put(cle, new int[] { Integer.parseInt(ExtractionXML.extraireFils(condition, cle + suffixe)) });
				}
			}
		}

		public void appendConditions(StringBuilder sb) {
			conditions.forEach((nom, tableau) -> {
				sb.append(nom);

				for (int valeur : tableau) {
					sb.append(" ").append(valeur);
				}
				sb.append("\n");
			});
		}
	}

	public static class HeaderMapEvent {
		public final int id;
		public final String nom;
		public final int x;
		public final int y;

		public HeaderMapEvent(int id, String nom, int x, int y) {
			this.id = id;
			this.nom = nom;
			this.x = x;
			this.y = y;
		}

		public static HeaderMapEvent instancier(Node eventNode) {
			int id = UtilXML.getId(eventNode);
			String nom = ExtractionXML.extraireFils(eventNode, "nom");
			int x = Integer.parseInt(ExtractionXML.extraireFils(eventNode, "x"));
			int y = Integer.parseInt(ExtractionXML.extraireFils(eventNode, "y"));

			return new HeaderMapEvent(id, nom, x, y);
		}
	}
	
	public static class HeaderMap {
		public final int id;
		public final String nom;
		public final List<String> arborescence;
		
		public HeaderMap(int id, String nom, Pair<Integer, String>[] arbre) {
			this.id = id;
			this.nom = nom;
			this.arborescence = new ArrayList<>();
			
			remplirArbre(arborescence, id, arbre);
		}

		private static void remplirArbre(List<String> arborescence, int id, Pair<Integer, String>[] arbre) {
			if (id != 0) {
				Pair<Integer, String> paire = arbre[id];
				remplirArbre(arborescence, paire.getLeft(), arbre);
			}
			
			arborescence.add(arbre[id].getRight());
		}
	}
	
}
