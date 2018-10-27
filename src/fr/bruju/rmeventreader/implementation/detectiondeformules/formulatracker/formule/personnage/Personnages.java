package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage;

import java.util.Collection;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.LigneNonReconnueException;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.Ressources;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

public class Personnages {
	/** Map associant nom de personnage et objet */
	private Map<String, Individu<StatPerso>> personnagesReels;

	
	public Personnages() {
		personnagesReels = VariablesAssociees.remplirStatistiques(StatPerso::new);
	}
	
	/**
	 * Rempli les variables nommées dans les map données. Le fichier est au format "Numéro Type Nom" où le type est V
	 * pour les variables et S pour les interrupteurs.
	 * @param variablesExistantes Map de variables
	 * @param interrupteursExistants Map d'interrupteurs
	 */
	public void remplirVariablesNommees(Map<Integer, Valeur> variablesExistantes,
			Map<Integer, Bouton> interrupteursExistants) {
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(Ressources.NOMS, ligne -> {
			String[] donnees = ligne.split(" ");
			
			if (donnees.length != 3) {
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
	public Collection<Individu<StatPerso>> getPersonnages() {
		return personnagesReels.values();
	}


}
