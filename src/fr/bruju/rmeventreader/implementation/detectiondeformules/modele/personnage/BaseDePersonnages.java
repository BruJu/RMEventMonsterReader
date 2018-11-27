package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Statistique;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;
import fr.bruju.util.MapsUtils;

import java.util.*;

/**
 * Une base de personnages regroupe la liste de tous les personnages et fourni des méthodes utilitaires pour pouvoir
 * unifier les personnages.
 */
public class BaseDePersonnages {
	/**
	 * Fichier ressource où sont stockés la liste des personnages au format
	 * "NomDuPersonnaege NomStatistique NuméroVariable".
	 */
	public static String FICHIER_PERSONNAGES = "ressources/formulatracker/Statistiques.txt";

	/** Association numéro de variable - objet représentant la variable */
	private Map<Integer, ExprVariable> variablesInstanciees;
	/** Association nom de personnage - objet représentant le personnage */
	private Map<String, Personnage> personnages;

	// RPG Maker utilise des variables de 1 à 5000. On utilise donc les variables supérieures à 5000 pour représenter
	// le numéro des variables de groupes de personnages
	// L'implémentation impose qu'une statistique est reliée à une variable. Le but de ce module étant de trouver des
	// similitudes entre algorithmes, on utilise ce procédé pour palier à une faiblesse de l'implémentation.
	private int numeroVariable = 5001;
	private int numeroInterrupteur = -5001;

	/**
	 * Construit la base de personnages en se reposant sur le fichier ressource défini par FICHIER_PERSONNAGES
	 */
	public BaseDePersonnages() {
		variablesInstanciees = new HashMap<>();
		personnages = new HashMap<>();

		LecteurDeFichiersLigneParLigne.lectureFichierRessources(FICHIER_PERSONNAGES, ligne -> {
			String[] donnees = ligne.split(" ", 3);

			String nomPersonnage = donnees[0];
			String nomStatistique = donnees[1];
			boolean estPropriete = donnees[2].charAt(0) == 'S';
			Integer numeroVariable = Integer.decode((estPropriete ? donnees[2].substring(1) : donnees[2]));

			if (estPropriete) {
				numeroVariable = -numeroVariable;
			}

			Personnage personnage = MapsUtils.getY(personnages, nomPersonnage, Individu::new);
			Statistique statistique = new Statistique(numeroVariable, personnage, nomStatistique);
			variablesInstanciees.put(numeroVariable, statistique);
			personnage.ajouterStatistique(statistique);
		});
	}


	/**
	 * Donne la table d'association numéro de variables - instanciation de la statistique
	 */
	public Map<Integer, ExprVariable> getVariablesInstanciees() {
		return variablesInstanciees;
	}

	/**
	 * Donne l'objet personnage qui représente les deux personnages donnés unifiés.
	 * @param p1 Le premier personnage
	 * @param p2 Le second personnage
	 * @return Un objet Groupe qui représente l'unification des deux personnages (ie un meta personnage qui possède les
	 * statistiques des deux personnages unifiés et dont le nom représente les deux personnages regroupés)
	 */
	public Personnage getPersonnageUnifie(Personnage p1, Personnage p2) {
		Set<Individu> individus = new TreeSet<>();
		p1.ajouterPersonnage(individus);
		p2.ajouterPersonnage(individus);

		String nomPersonnageUnifie = Groupe.definirNom(individus);
		Personnage personnage = personnages.get(nomPersonnageUnifie);

		if (personnage == null) {
			Groupe personnageGroupe = new Groupe(nomPersonnageUnifie, individus);
			personnage = personnageGroupe;
			personnages.put(nomPersonnageUnifie, personnageGroupe);

			personnageGroupe.ajouterNouvellesStatistiquesIssuesDe(p1, this);
			personnageGroupe.ajouterNouvellesStatistiquesIssuesDe(p2, this);
		}

		return personnage;
	}


	/** Donne un numéro de variable libre */
	int getNouvelleVariable() {
		return numeroVariable++;
	}

	/** Donne un numéro d'interrupteur libre */
	int getNouvelInterrupteur() {
		return numeroInterrupteur--;
	}
}
