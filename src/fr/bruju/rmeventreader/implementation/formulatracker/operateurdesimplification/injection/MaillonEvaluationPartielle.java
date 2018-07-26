package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.injection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.Utilitaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Injecte des valeurs lues dans un fichier du type "30 ON", "170 7" pour dire que l'interrupteur 30 est activé et que
 * la variable 170 vaut 7 dans toutes les formules afin de retirer des conditions inutiles.
 * @author Bruju
 *
 */
public class MaillonEvaluationPartielle extends ConstructeurDeComposantR implements Maillon {
	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//  - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON -

	@Override
	public void traiter(Attaques attaques) {
		remplirAvecFichier("ressources/formulatracker/Injection.txt");
		
		attaques.transformerComposants(this::traiter);
	}

	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//        - LECTURE DE RESSOURCES - LECTURE DE RESSOURCES - LECTURE DE RESSOURCES - LECTURE DE RESSOURCES - 
	
	/**
	 * Evaluations connues d'interrupteurs
	 */
	private Map<Integer, Boolean> interrupteurs;
	
	/**
	 * Evaluations connues de variables
	 */
	private Map<Integer, Integer> variables;
	
	/**
	 * Rempli les évaluations connues avec le fichier donné
	 * @param chemin Le fichier ressource
	 */
	public void remplirAvecFichier(String chemin) {
		interrupteurs = new HashMap<>();
		variables = new HashMap<>();
		
		List<Pair<String, String>> ressources = Utilitaire.lireFichierRessource(chemin);
		
		ressources.forEach(paire -> {
			
			String idStr = paire.getLeft();
			Integer idInt = Integer.parseInt(idStr);
			String valeur = paire.getRight();
			
			if (valeur.equals("ON")) {
				interrupteurs.put(idInt, true);
			} else if (valeur.equals("OFF")) {
				interrupteurs.put(idInt, false);
			} else {
				variables.put(idInt, Integer.parseInt(valeur));
			}
		});
	}
	

	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//              - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT -

	@Override
	protected Composant traiter(BBase composant) {
		int numero = composant.numero;
		Boolean valeur = interrupteurs.get(numero);
		
		return (valeur == null) ? composant : BConstant.get(valeur);
	}

	@Override
	protected Composant traiter(VStatistique composant) {
		int numero = composant.statistique.position;
		return injecterVariable(composant, numero);
	}

	@Override
	protected Composant traiter(VBase composant) {
		int numero = composant.idVariable;
		return injecterVariable(composant, numero);
	}

	/**
	 * Renvoie le composant si le numéro de variable donné n'a pas de valeur. Sinon renvoie une constante avec la valeur
	 * @param composant Le composant à évaluer
	 * @param numero Le numéro de la variable représentée par le composant
	 * @return La nouvelle valeur en tenant compte de l'évaluation actuelle
	 */
	private Valeur injecterVariable(Valeur composant, int numero) {
		Integer valeur = variables.get(numero);		
		return (valeur == null) ? composant : new VConstante(valeur);
	}
}
