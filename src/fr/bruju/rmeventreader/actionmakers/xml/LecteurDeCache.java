package fr.bruju.rmeventreader.actionmakers.xml;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier.BoucleTraitement;
import fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier.Constructeur;
import fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier.Instr;
import fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier.LigneAttendue;
import fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier.PaireIDString;
import fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier.TableauInt;
import fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.Utilitaire_XML;
import fr.bruju.rmeventreader.dictionnaires.header.EvenementCommun;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.dictionnaires.lecteurs.LecteurEC;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;

public class LecteurDeCache {

	public static List<Instruction> chargerInstructions(int idEvent, int idPage) {
		if (idPage == -1)
			return chargerInstructionsEC(idEvent);
		else
			return chargerInstructionsEM(idEvent, idPage);
	}


	private static List<Instruction> chargerInstructionsEC(int idEvent) {
		String fichier = "cache_xml\\EC\\EC" + Utilitaire_XML.transformerId(idEvent) + ".txt";
		EvenementCommun ec = creerEventCommun(fichier);
		return ec.instructions;
	}

	@SuppressWarnings("unchecked")
	private static EvenementCommun creerEventCommun(String fichier) {
		return Constructeur.construire(fichier,
				new Traitement[] {
					new LigneAttendue("-- EVENT COMMUN --"),
					new TableauInt("ID"),
					new PaireIDString("Nom"),
					new TableauInt("Trigger"),
					new LigneAttendue("- Instructions -"),
					new BoucleTraitement(new Instr())
				},
				donnees -> {
					
					int id = ((int[]) donnees[1])[0];
					String nom = (String) donnees[2];
					int triggerType = ((int[]) donnees[3])[0];
					int triggerNom = ((int[]) donnees[3])[1];
					
					EvenementCommun ec = new EvenementCommun(id, nom, triggerType, triggerNom);
					
					((List<Object>) (donnees[5])).stream().map(objet -> (Instruction) objet)
						.forEach(ec.instructions::add);
					
					return ec;
				});
	}


	private static List<Instruction> chargerInstructionsEM(int idEvent, int idPage) {
		return null;
	}
}
