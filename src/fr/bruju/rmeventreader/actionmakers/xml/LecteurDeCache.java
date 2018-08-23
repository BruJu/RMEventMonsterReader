package fr.bruju.rmeventreader.actionmakers.xml;

import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.ConvertisseurLigneVersObjet;
import fr.bruju.rmeventreader.dictionnaires.header.Evenement;
import fr.bruju.rmeventreader.dictionnaires.header.EvenementCommun;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.header.MapGeneral;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class LecteurDeCache {

	public static List<Instruction> chargerInstructions(int idMap, int idEvent, int idPage) {
		if (idPage == -1)
			return chargerInstructionsEC(idEvent);
		else
			return chargerInstructionsEM(idMap, idEvent, idPage);
	}


	private static List<Instruction> chargerInstructionsEC(int idEvent) {
		String fichier = "cache_xml\\EC\\EC" + Utilitaire_XML.transformerId(idEvent) + ".txt";
		EvenementCommun ec = ConvertisseurLigneVersObjet.construire(fichier, EvenementCommun.sousObjet());
		return ec == null ? null : ec.instructions;
	}

	private static List<Instruction> chargerInstructionsEM(int idMap, int idEvent, int idPage) {
		String prefixe = "cache_xml\\Map" + Utilitaire_XML.transformerId(idMap) + "\\";

		MapGeneral mg = ConvertisseurLigneVersObjet.construire(prefixe + "General.txt", MapGeneral.sousObjet());
		
		if (mg == null) {
			return null;
		}
		
		if (mg.evenementsComplexes.contains(idEvent)) {
			String fichier = prefixe + "Event" + Utilitaire_XML.transformerId(idEvent) + ".txt";
			
			Evenement evenement = ConvertisseurLigneVersObjet.construire(fichier, Evenement.sousObjet());
			
			return evenement
					.pages
					.get(idPage - 1)
					.instructions;
			
		} else {
			return Utilitaire.toArrayList(new Instruction(12410, "Pas d'instruction", new int[0]));
		}
	}
}
