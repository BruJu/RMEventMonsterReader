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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

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
		
		for (int i = 1 ; i != s.size() ; i++) {
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
		String dossier = prefixeDestination + "\\Map" + map.id + "\\";
		
		File dossierF = new File(dossier);
		
		if (dossierF.isDirectory()) {
			supprimerDossier(dossierF);
		}
		
		dossierF.mkdirs();
		
		creerFichiers(dossier, map);
	}


	private void creerFichiers(String dossier, MapRM map) {
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
