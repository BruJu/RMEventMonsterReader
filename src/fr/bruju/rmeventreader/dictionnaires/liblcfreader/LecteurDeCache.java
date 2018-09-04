package fr.bruju.rmeventreader.dictionnaires.liblcfreader;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.ConvertisseurLigneVersObjet;
import fr.bruju.rmeventreader.dictionnaires.modele.Contexte;
import fr.bruju.rmeventreader.dictionnaires.modele.Evenement;
import fr.bruju.rmeventreader.dictionnaires.modele.EvenementCommun;
import fr.bruju.rmeventreader.dictionnaires.modele.Instruction;
import fr.bruju.rmeventreader.dictionnaires.modele.MapGeneral;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class LecteurDeCache {
	
	
	
	
	

	public static List<Instruction> chargerInstructions(int idMap, int idEvent, int idPage) {
		try {
			if (idMap == -1 || idPage == -1)
				return getEvenementCommun(idEvent).instructions;
			else
				return getEvenement(idMap, idEvent).pages.get(idPage - 1).instructions;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public static MapGeneral getMapGeneral(int idMap) {
		String prefixe = "cache_xml\\Map" + Utilitaire_XML.transformerId(idMap) + "\\";
		return ConvertisseurLigneVersObjet.construire(prefixe + "General.txt", MapGeneral.sousObjet());
	}
	
	public static List<Evenement> getEvenementsDepuisMapGeneral(MapGeneral mapGeneral) {
		String prefixe = "cache_xml\\Map" + Utilitaire_XML.transformerId(mapGeneral.map.id) + "\\";
		
		return mapGeneral.evenements
				.stream()
				.map(numero -> mapGeneral.evenementsComplexes.contains(numero) ?
						getEvenementComplexe(prefixe, mapGeneral.map.id, numero) : getEvenementSimple(numero))
				.collect(Collectors.toList());
	}


	public static EvenementCommun getEvenementCommun(int idEvent) {
		String fichier = "cache_xml\\EC\\EC" + Utilitaire_XML.transformerId(idEvent) + ".txt";
		EvenementCommun ec = ConvertisseurLigneVersObjet.construire(fichier, EvenementCommun.sousObjet());
		return ec;
	}

	public static Evenement getEvenement(int idMap, int idEvent) {
		String prefixe = "cache_xml\\Map" + Utilitaire_XML.transformerId(idMap) + "\\";

		MapGeneral mg = getMapGeneral(idMap);
		
		if (mg == null) {
			return null;
		}
		
		if (mg.evenementsComplexes.contains(idEvent)) {
			return getEvenementComplexe(prefixe, idMap, idEvent);
		} else {
			return getEvenementSimple(idEvent);
		}
	}
	
	private static Evenement getEvenementComplexe(String dossierMap, int idMap, int idEvent) {
		String fichier = dossierMap + "Event" + Utilitaire_XML.transformerId(idEvent) + ".txt";
		return ConvertisseurLigneVersObjet.construire(fichier, Evenement.sousObjet());
	}

	private static Evenement getEvenementSimple(int idEvent) {
		// TODO : mettre les bonnes données
		return Evenement.creerEvenementSimple(idEvent, "", -1, -1);
	}
	

	public static Pair<Integer, List<Integer>> getInformations() {
		Contexte ec = ConvertisseurLigneVersObjet.construire("cache_xml\\Contexte.txt" , Contexte.sousObjet());
		
		return new Pair<>(ec.nombreEC, ec.maps);
	}
}
