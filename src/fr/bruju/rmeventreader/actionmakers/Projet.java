package fr.bruju.rmeventreader.actionmakers;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMEvenementCommun;
import fr.bruju.lcfreader.rmobjets.RMInstruction;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.rmobjets.RMPage;
import fr.bruju.lcfreader.services.LecteurDeLCF;
import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.projet.Dictionnaire;
import fr.bruju.rmeventreader.actionmakers.projet.DictionnaireNonSymbolique;
import fr.bruju.rmeventreader.actionmakers.projet.Explorateur;
import fr.bruju.rmeventreader.actionmakers.projet.ExplorateurDInstructions;
import fr.bruju.rmeventreader.actionmakers.reference.ReferenceEC;
import fr.bruju.rmeventreader.actionmakers.reference.ReferenceMap;
import fr.bruju.rmeventreader.utilitaire.lambda.TriConsumer;


/**
 * Cette classe centralise tous les services proposés. Elle permet d'explorer un projet en lisant les évènements
 * qu'elle contient au travers de diverses méthodes, ainsi que d'extraire certains noms de la base de données
 * (comme le nom d'un objet dont l'identifiant est connu, le nom d'un héros ...).
 * 
 * @author Bruju
 *
 */
public class Projet implements ExplorateurDInstructions {
	//
	//
	//
	
	private final LecteurDeLCF lecteur;
	private final ExplorateurDInstructions explorateur;
	private final Dictionnaire[] dictionnaires;
	
	public Projet(String chemin) {
		lecteur = new LecteurDeLCF(chemin);
		explorateur = new Explorateur(lecteur);
		dictionnaires = new Dictionnaire[Dictionnaires.values().length];
	}
	
	/* ==========================
	 * Explorateur d'instructions
	 * ========================== */

	public void executer(ExecuteurInstructions executeur, List<RMInstruction> instructions) {
        explorateur.executer(executeur, instructions);
    }

	public void lireEvenement(ExecuteurInstructions executeur, int idMap, int idEvenement, int idPage) {
        explorateur.lireEvenement(executeur, idMap, idEvenement, idPage);
    }

	public void lireEvenementCommun(ExecuteurInstructions executeur, int idEvenement) {
        explorateur.lireEvenementCommun(executeur, idEvenement);
    }
	
	public void explorerEvenementsCommuns(Consumer<RMEvenementCommun> actionSurLesEvenementCommuns) {
        explorateur.explorerEvenementsCommuns(actionSurLesEvenementCommuns);
    }

	public void explorerEvenements(TriConsumer<RMMap, RMEvenement, RMPage> actionSurLesPages) {
        explorateur.explorerEvenements(actionSurLesPages);
    }

	public void explorerCarte(int idCarte, BiConsumer<RMEvenement, RMPage> actionSurLesPages) {
        explorateur.explorerCarte(idCarte, actionSurLesPages);
    }
    
	public void referencerEvenementsCommuns(Function<ReferenceEC, ExecuteurInstructions> generateur) {
        explorateur.referencerEvenementsCommuns(generateur);
    }
	
	public void referencerCartes(Function<ReferenceMap, ExecuteurInstructions> generateur) {
        explorateur.referencerCartes(generateur);
    }
	

	/* =============
	 * Dictionnaires
	 * ============= */

	
	public enum Dictionnaires {
		PERSONNAGE("actors", Dictionnaire::new),
		INTERRUPTEUR("switches", Dictionnaire::new),
		VARIABLE("variables", Dictionnaire::new),
		OBJET("items", DictionnaireNonSymbolique::new);
		
		private String chaine;
		private Function<List<String>, Dictionnaire> instanciation;

		private Dictionnaires(String chaine, Function<List<String>, Dictionnaire> instanciation) {
			this.chaine = chaine;
			this.instanciation = instanciation;
		}
	}
	
	
	public String extraireHeros(int index) {
		return extraire(Dictionnaires.PERSONNAGE, index);
	}
	public String extraireInterrupteur(int index) {
		return extraire(Dictionnaires.INTERRUPTEUR, index);
	}
	public String extraireVariable(int index) {
		return extraire(Dictionnaires.VARIABLE, index);
	}
	public String extraireObjet(int index) {
		return extraire(Dictionnaires.OBJET, index);
	}
	
	
	public String extraire(Dictionnaires nom, int index) {
		assurerExistanceDictionnaire(nom);
		return dictionnaires[nom.ordinal()].extraire(index);
	}
	
	
	public void ecrireRessource(String dossier) {
		for (Dictionnaires nom : Dictionnaires.values()) {
			assurerExistanceDictionnaire(nom);
			dictionnaires[nom.ordinal()].ecrireFichier(dossier + "db_" + nom.chaine + ".txt");
		}
	}

	
	private void assurerExistanceDictionnaire(Dictionnaires nom) {
		if (dictionnaires[nom.ordinal()] == null) {
			dictionnaires[nom.ordinal()] = nom.instanciation.apply(lecteur.getListeDeNoms(nom.chaine));
		}
	}



	

}
