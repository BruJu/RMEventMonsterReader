package fr.bruju.rmeventreader.dictionnaires.liblcfreader;

import fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML;
import fr.bruju.rmeventreader.dictionnaires.header.Evenement;
import fr.bruju.rmeventreader.dictionnaires.header.EvenementCommun;
import fr.bruju.rmeventreader.dictionnaires.header.MapArbre;
import fr.bruju.rmeventreader.dictionnaires.header.MapRM;
import fr.bruju.rmeventreader.dictionnaires.header.Page;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.xpath.XPathConstants;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML.streamXML;

/**
 * Cette classe a pour but de créer des fichiers .txt simples à partir des fichiers xml
 * 
 * @author Bruju
 *
 */
public class MiseEnCache {
	private int numeroMap = -1;
	
	public MiseEnCache(int choixMap) {
		numeroMap = choixMap;
	}

	public MiseEnCache() {
	}

	public void construireCache(String destination, String source) {
		if (numeroMap > 0) {
			map(destination, new MapRM(numeroMap, "Test", Utilitaire.toArrayList("Test")),
					source + "Map" + Utilitaire_XML.transformerId(numeroMap) + ".xml");
			return;
		}
		
		
		int nbDEventCommuns = eventCommuns(destination + "EC\\", source + "RPG_RT_DB.xml");
		
		if (numeroMap == -2) 
			return;
		
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
		
		Utilitaire.Fichier_Ecrire(destination + "Contexte.txt", sb.toString());
	}

	private int eventCommuns(String dossier, String baseDeDonnees) {
		NodeList nodeList = (NodeList) Utilitaire_XML.extraireDepuisXPath(baseDeDonnees,
				"/LDB/Database/commonevents/CommonEvent", XPathConstants.NODESET);
		if (nodeList == null)
			return -1;
		
		File dossierF = new File(dossier);
		if (dossierF.isDirectory()) {
			Utilitaire.Fichier_supprimerDossier(dossierF);
		}
		dossierF.mkdirs();
		
		return streamXML(nodeList)
				.map(n -> eventCommun(n, dossier))
				.mapToInt(ev -> ev.id)
				.reduce(Math::max)
				.getAsInt();
	}

	/**
	 * 
	 * <br>
	 * <i>A un effet de bord</i>
	 * 
	 * @param node
	 * @param dossier
	 * @return
	 */
	private EvenementCommun eventCommun(Node node, String dossier) {
		EvenementCommun ec = InstancieurXML.construire(node, "event_commands", "EventCommand",
				InstancieurXML::evenementCommun, InstancieurXML::instruction);

		StringBuilder sb = new StringBuilder();
		ec.append(sb);
		sb.append("- Instructions -\n");
		ec.instructions.forEach(i -> sb.append(i.toLigne()));

		Utilitaire.Fichier_Ecrire(dossier + "EC" + Utilitaire_XML.transformerId(ec.id) + ".txt", sb.toString());
		
		ec.viderCache();
		return ec;
	}
	
	private List<Integer> arbo(String prefixeDestination, String fichierArbre, String prefixeMaps) {
		MapArbre[] arbre = MapArbre.extraireArbre(fichierArbre);
		return explorerCartes(prefixeDestination, prefixeMaps, arbre);
	}

	private List<Integer> explorerCartes(String prefixeDestination, String prefixeMaps, MapArbre[] arbre) {
		List<Integer> mapVues = new ArrayList<>();
		
		for (int id = 1 ; id != arbre.length ; id++) {
			MapRM mapRM = new MapRM(id, arbre);

			if (map(prefixeDestination, mapRM, prefixeMaps + Utilitaire_XML.transformerId(id) + ".xml"))
				mapVues.add(mapRM.id); 
		}
		
		return mapVues;
	}




	private void creerDossier(String prefixeDestination, MapRM map) {
		String dossier = prefixeDestination + "Map" + Utilitaire_XML.transformerId(map.id) + "\\";

		File dossierF = new File(dossier);

		if (dossierF.isDirectory()) {
			Utilitaire.Fichier_supprimerDossier(dossierF);
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
		String chemin = dossier + "Event" + Utilitaire_XML.transformerId(event.id) + ".txt";

		StringBuilder sb = new StringBuilder();

		map.append(sb);
		event.append(sb);

		event.pages.forEach(page -> page.append(sb));

		Utilitaire.Fichier_Ecrire(chemin, sb.toString());
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

		Utilitaire.Fichier_Ecrire(chemin, chaineAEcrire);
	}



	private boolean map(String prefixeDestination, MapRM map, String fichierXML) {
		NodeList nodeList = (NodeList) Utilitaire_XML.extraireDepuisXPath(fichierXML, "/LMU/Map/events/Event",
				XPathConstants.NODESET);
		if (nodeList == null)
			return false;

		streamXML(nodeList).forEach(node -> map.ajouter(mapEvent(node)));

		creerDossier(prefixeDestination, map);

		map.viderMemoire();
		
		return true;
	}

	private Evenement mapEvent(Node node) {
		return InstancieurXML.construire(node, "pages", "EventPage",
				InstancieurXML::evenement, fils -> mapEventPage(fils));
	}

	private Page mapEventPage(Node node) {
		return InstancieurXML.construire(node, "event_commands", "EventCommand",
				InstancieurXML::page, InstancieurXML::instruction);
	}
	
	
}
