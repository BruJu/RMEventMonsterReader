package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.LigneNonReconnueException;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.Ressources;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

public class Personnages {
	/** Chemin vers la liste des variables et interrutpeurs nommés */
	private static String cheminNommes = Ressources.NOMS;
	
	/** Map associant nom de personnage et objet */
	private Map<String, PersonnageReel> personnagesReels = new HashMap<>();

	/**
	 * Lit la liste des variables associées aux personnages dans le fichier donné. Le format doit être
	 * "NomDuPersonnage NomDeLaStatistique Numéro". Il est possible d'assigner des interrupteurs en prefixant le numéro
	 * par un S. 
	 * @param chemin Chemin du fichier
	 * @throws IOException
	 */
	public void lirePersonnagesDansFichier(String chemin) throws IOException {
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(chemin, ligne -> {
			String[] donnees = ligne.split(" ");
			
			if (donnees == null || donnees.length != 3) {
				throw new LigneNonReconnueException("");
			}

			String nomPersonnage = donnees[0];
			String nomStatistique = donnees[1];
			
			boolean estPropriete = donnees[2].charAt(0) == 'S';
			
			Integer numeroVariable = Integer.decode((estPropriete ? donnees[2].substring(1) : donnees[2]));

			injecter(nomPersonnage, nomStatistique, numeroVariable, !estPropriete);
		});
	}
	

	/**
	 * Rempli les variables nommées dans les map données. Le fichier est au format "Numéro Type Nom" où le type est V
	 * pour les variables et S pour les interrupteurs.
	 * @param variablesExistantes Map de variables
	 * @param interrupteursExistants Map d'interrupteurs
	 */
	public void remplirVariablesNommees(Map<Integer, Valeur> variablesExistantes,
			Map<Integer, Bouton> interrupteursExistants) {
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(cheminNommes, ligne -> {
			String[] donnees = ligne.split(" ");
			
			if (donnees == null || donnees.length != 3) {
				throw new LigneNonReconnueException("");
			}
			
			Integer numero = Integer.decode(donnees[0]);
			boolean estVariable = donnees[1].equals("V");
			String nom = donnees[2];
			
			if (estVariable) {
				variablesExistantes.put(numero, new VBase(numero, nom));
			} else {
				interrupteursExistants.put(numero, new BBase(numero, nom));
			}
		});
	}

	/**
	 * Donne la liste des personnages identifiés
	 */
	public Collection<PersonnageReel> getPersonnages() {
		return personnagesReels.values();
	}

	/**
	 * Ajoute la statistique pour le personnage donné à la carte des personnages / statistiques connus.
	 */
	private void injecter(String nomPersonnage, String nomStatistique, Integer numeroVariable, boolean estStat) {
		PersonnageReel perso = personnagesReels.get(nomPersonnage);

		if (perso == null) {
			perso = new PersonnageReel(nomPersonnage);
			personnagesReels.put(nomPersonnage, perso);
		}

		if (estStat)
			perso.addStatistique(nomStatistique, numeroVariable);
		else
			perso.addPropriete(nomStatistique, numeroVariable);
	}

}
