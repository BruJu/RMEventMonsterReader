package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.simples;

import fr.bruju.rmeventreader.implementation.detectiondeformules._variables.EtatInitial;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.Maillon;

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
		this.etatDesVariables = EtatInitial.getEtatInitial();
		attaques.transformerComposants(this::traiter);
	}

	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//              - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT -

	private EtatInitial etatDesVariables;
	
	@Override
	protected Composant modifier(BBase composant) {
		int numero = composant.numero;
		Boolean valeur = etatDesVariables.getInterrupteur(numero);
		
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
		Integer valeur = etatDesVariables.getValeur(numero);
		return (valeur == null) ? composant : new VConstante(valeur);
	}
}
