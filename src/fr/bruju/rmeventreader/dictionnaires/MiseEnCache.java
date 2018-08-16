package fr.bruju.rmeventreader.dictionnaires;

import fr.bruju.rmeventreader.dictionnaires.header.ElementComposite;
import fr.bruju.rmeventreader.dictionnaires.header.Evenement;
import fr.bruju.rmeventreader.dictionnaires.header.EvenementCommun;
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
import java.sql.Timestamp;
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
	
	public void construireCache(String destination, String source) {
		int nbDEventCommuns = eventCommuns(destination + "EC\\", source + "RPG_RT_DB.xml");
		List<Integer> mapExistantes = arbo(destination, "ressources_gen\\bdd_maps.txt", source + "Map");
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		int version = 1;
		
		StringBuilder sb = new StringBuilder();
		
		sb.append("Maps");
		mapExistantes.forEach(v -> sb.append(" ").append(v));
		sb.append("\n");
		
		sb.append("EC ").append(nbDEventCommuns).append("\n");
		sb.append("Date ").append(timestamp.getTime()).append("\n");
		sb.append("Version ").append(version).append("\n");
		
		ecrire(destination + "Contexte.txt", sb.toString());
	}

	private int eventCommuns(String dossier, String baseDeDonnees) {
		NodeList nodeList = (NodeList) ExtractionXML.extraireDepuisXPath(baseDeDonnees,
				"/LDB/Database/commonevents/CommonEvent", XPathConstants.NODESET);
		if (nodeList == null)
			return -1;

		File dossierF = new File(dossier);
		if (dossierF.isDirectory()) {
			supprimerDossier(dossierF);
		}
		dossierF.mkdirs();
		
		List<EvenementCommun> events = new ArrayList<>();
		forEachNodes(nodeList, n -> events.add(eventCommun(n, dossier)));
		
		return events.stream().mapToInt(ev -> ev.id).reduce(Math::max).getAsInt();
	}

	private EvenementCommun eventCommun(Node node, String dossier) {
		EvenementCommun ec = construire(node, "event_commands", "EventCommand", EvenementCommun::instancier,
				fils -> traduireEvent(fils));

		StringBuilder sb = new StringBuilder();
		ec.append(sb);
		sb.append("- Instructions -\n");
		ec.instructions.forEach(i -> sb.append(i.toLigne()));

		ecrire(dossier + "EC" + UtilXML.transformerId(ec.id) + ".txt", sb.toString());
		
		ec.viderCache();
		return ec;
	}

	private List<Integer> arbo(String prefixeDestination, String fichierArbre, String prefixeMaps) {
		List<String[]> s;
		try {
			s = FileReaderByLine.lireFichier(fichierArbre, 3);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		ArrayList<Pair<Integer, String>> arbo = new ArrayList<>(s.size());

		s.forEach(tableau -> arbo.add(new Pair<>(Integer.parseInt(tableau[1]), tableau[2])));

		@SuppressWarnings("unchecked")
		Pair<Integer, String>[] arbre = arbo.toArray(new Pair[0]);

		List<Integer> mapVues = new ArrayList<>();
		
		for (int i = 1; i != s.size(); i++) {
			MapRM hm = new MapRM(i, s.get(i)[2], arbre);
			boolean mapFaite = map(prefixeDestination, hm, prefixeMaps + s.get(i)[0] + ".xml");
			
			if (mapFaite)
				mapVues.add(hm.id); 
		}
		
		return mapVues;
	}

	private boolean map(String prefixeDestination, MapRM map, String fichierXML) {
		NodeList nodeList = (NodeList) ExtractionXML.extraireDepuisXPath(fichierXML, "/LMU/Map/events/Event",
				XPathConstants.NODESET);
		if (nodeList == null)
			return false;

		forEachNodes(nodeList, node -> map.ajouter(mapEvent(node)));

		creerDossier(prefixeDestination, map);

		map.viderMemoire();
		
		return true;
	}

	private void creerDossier(String prefixeDestination, MapRM map) {
		String dossier = prefixeDestination + "Map" + UtilXML.transformerId(map.id) + "\\";

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

		sb.append("- Evenements - \n").append("// Events présents\n")
				.append(listeDesEvents.stream().map(id -> Integer.toString(id)).collect(Collectors.joining(" ")))
				.append("\n")

				.append("// Events complexes\n")
				.append(eventsInteressants.stream().map(e -> Integer.toString(e.id)).collect(Collectors.joining(" ")))
				.append("\n")

				.append("// Events simples\n")
				.append(eventsIninteressants.stream().map(e -> Integer.toString(e.id)).collect(Collectors.joining(" ")))
				.append("\n\n")

				.append("- Evenements simples -\n");

		eventsIninteressants.forEach(event -> sb.append(event.id).append(" ").append(event.x).append(" ")
				.append(event.y).append(" ").append(event.nom).append("\n"));

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
			
			if (fichierPresent.isDirectory())
				supprimerDossier(fichierPresent);
			else
				fichierPresent.delete();
		}
		
		dossierF.delete();
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
