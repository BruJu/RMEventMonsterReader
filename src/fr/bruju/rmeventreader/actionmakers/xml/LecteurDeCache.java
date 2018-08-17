package fr.bruju.rmeventreader.actionmakers.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.BoucleTraitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Constructeur;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Instr;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.LigneAttendue;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.PaireIDString;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.TableauInt;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.header.EvenementCommun;
import fr.bruju.rmeventreader.dictionnaires.header.EvenementCommun.Builder;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class LecteurDeCache {

	public static List<Instruction> chargerInstructions(int idMap, int idEvent, int idPage) {
		if (idPage == -1)
			return chargerInstructionsEC(idEvent);
		else
			return chargerInstructionsEM(idMap, idEvent, idPage);
	}


	private static List<Instruction> chargerInstructionsEC(int idEvent) {
		String fichier = "cache_xml\\EC\\EC" + Utilitaire_XML.transformerId(idEvent) + ".txt";
		EvenementCommun ec = EvenementCommun.construire(fichier);
		return ec.instructions;
	}

	private static List<Instruction> chargerInstructionsEM(int idMap, int idEvent, int idPage) {
		String prefixe = "cache_xml\\Map" + Utilitaire_XML.transformerId(idMap) + "\\";

		
		
		return null;
	}
}
