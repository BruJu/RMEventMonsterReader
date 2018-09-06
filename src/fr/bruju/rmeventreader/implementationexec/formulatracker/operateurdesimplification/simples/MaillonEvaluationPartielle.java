package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.simples;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.Maillon;

/**
 * Injecte des valeurs lues dans un fichier du type "30 ON", "170 7" pour dire que l'interrupteur 30 est activé et que
 * la variable 170 vaut 7 dans toutes les formules afin de retirer des conditions inutiles.
 * @author Bruju
 *
 */
public class MaillonEvaluationPartielle extends ConstructeurDeComposantsRecursif implements Maillon {
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
		
		List<String[]> ressources;
		
		try {
			ressources = FileReaderByLine.lireFichier(chemin, 2);
		} catch (IOException e) {
			return;
		}
		
		ressources.forEach(paire -> {
			
			String idStr = paire[0];
			Integer idInt = Integer.parseInt(idStr);
			String valeur = paire[1];
			
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
	protected Composant modifier(BBase composant) {
		int numero = composant.numero;
		Boolean valeur = interrupteurs.get(numero);
		
		return (valeur == null) ? composant : BConstant.get(valeur);
	}
	
	@Override
	protected Composant modifier(VBase composant) {
		int numero = composant.idVariable;
		return injecterVariable(composant, numero);
	}

	@Override
	protected Composant modifier(VStatistique composant) {
		int numero = composant.statistique.position;
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
