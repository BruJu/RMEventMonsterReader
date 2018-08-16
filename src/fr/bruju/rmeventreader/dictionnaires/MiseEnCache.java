package fr.bruju.rmeventreader.dictionnaires;

import fr.bruju.rmeventreader.dictionnaires.header.ElementComposite;
import fr.bruju.rmeventreader.dictionnaires.header.Evenement;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.header.MapRM;
import fr.bruju.rmeventreader.dictionnaires.header.Page;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.utilitaire.Pair;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static fr.bruju.rmeventreader.dictionnaires.UtilXML.forEachNodes;

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

		for (int i = 1; i != s.size(); i++) {
			MapRM hm = new MapRM(i, s.get(i)[2], arbre);
			map(prefixeDestination, hm, prefixeMaps + s.get(i)[0] + ".xml");
		}
	}

	private void map(String prefixeDestination, MapRM map, String fichierXML) {
		NodeList nodeList = (NodeList) ExtractionXML.extraireDepuisXPath(fichierXML, "/LMU/Map/events/Event",
				XPathConstants.NODESET);
		if (nodeList == null)
			return;

		forEachNodes(nodeList, node -> map.ajouter(mapEvent(node)));

		creerDossier(prefixeDestination, map);

		map.viderMemoire();
	}

	private void creerDossier(String prefixeDestination, MapRM map) {
		String dossier = prefixeDestination + "\\Map" + UtilXML.transformerId(map.id) + "\\";

		File dossierF = new File(dossier);

		if (dossierF.isDirectory()) {
			supprimerDossier(dossierF);
		}

		dossierF.mkdirs();

		creerFichiers(dossier, map);
	}

	private void creerFichiers(String dossier, MapRM map) {
		List<Integer> listeDesEvents = new ArrayList<>();
		List<Evenement> eventsIninteressants = new ArrayList<>();
		List<Evenement> eventsInteressants = new ArrayList<>();

		map.evenements.forEach(evenement -> {
			listeDesEvents.add(evenement.id);

			if (evenement.estInteressant()) {
				eventsInteressants.add(evenement);
			} else {
				eventsIninteressants.add(evenement);
			}
		});

		creerFichierMap(dossier, map, listeDesEvents, eventsInteressants, eventsIninteressants);
		eventsInteressants.forEach(event -> creerFichierEvent(dossier, event, map));
	}

	private void creerFichierEvent(String dossier, Evenement event, MapRM map) {
		String chemin = dossier + "Event" + UtilXML.transformerId(event.id) + ".txt";
		
		StringBuilder sb = new StringBuilder();
		
		map.append(sb);
		event.append(sb);
		
		event.pages.forEach(page -> page.append(sb));
		
		ecrire(chemin, sb.toString());
	}

	private void creerFichierMap(String dossier, MapRM map, List<Integer> listeDesEvents,
			List<Evenement> eventsInteressants, List<Evenement> eventsIninteressants) {
		String chemin = dossier + "General.txt";
		
		StringBuilder sb = new StringBuilder();
		
		map.append(sb);
		
		sb.append("- Evenements - \n")
		  .append("// Events présents\n")
		  .append(listeDesEvents.stream().map(id -> Integer.toString(id)).collect(Collectors.joining(" ")))
		  .append("\n")
		  
		  .append("// Events complexes\n")
		  .append(eventsInteressants.stream().map(e -> Integer.toString(e.id)).collect(Collectors.joining(" ")))
		  .append("\n")
		  
		  .append("// Events simples\n")
		  .append(eventsIninteressants.stream().map(e -> Integer.toString(e.id)).collect(Collectors.joining(" ")))
		  .append("\n\n")
		  
		  .append("- Evenements simples -\n");
		
		eventsIninteressants.forEach(event -> sb.append(event.id).append(" ")
												.append(event.x).append(" ")
												.append(event.y).append(" ")
												.append(event.nom).append("\n"));
		
		
		String chaineAEcrire = sb.toString();
		
		ecrire(chemin, chaineAEcrire);
	}

	private void ecrire(String chemin, String chaineAEcrire) {
		File f = new File(chemin);

		try {
			f.createNewFile();
			FileWriter ff = new FileWriter(f);

			ff.write(chaineAEcrire);

			ff.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void supprimerDossier(File dossierF) {
		for (File fichierPresent : dossierF.listFiles()) {
			fichierPresent.delete();
		}
	}

	private <S, T extends ElementComposite<S>> T construire(Node node, String nomGroupeDeFils, String nomElementFils,
			Function<Node, T> fonctionInstanciation, Function<Node, S> creationDeFils) {
		T elementPere = fonctionInstanciation.apply(node);

		Node pagesFils = ExtractionXML.chercherFils(node, nomGroupeDeFils);

		forEachNodes(pagesFils.getChildNodes(), n -> n.getNodeName().equals(nomElementFils),
				n -> elementPere.ajouter(creationDeFils.apply(n)));

		return elementPere;
	}

	private Evenement mapEvent(Node node) {
		return construire(node, "pages", "EventPage", Evenement::instancier, fils -> mapEventPage(fils));
	}

	private Page mapEventPage(Node node) {
		return construire(node, "event_commands", "EventCommand", Page::instancier, fils -> traduireEvent(fils));
	}

	private Instruction traduireEvent(Node n) {
		return ExtractionXML.decrypterNoeudEventCommand(n);
	}

}
