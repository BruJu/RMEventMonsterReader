package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.actionmaker;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurAleatoire;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmdechiffreur.modele.Condition.CondHerosPossedeObjet;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.NombreAleatoire;

/**
 * Constructeur de formules à partir d'un fichier pour donner le contenu des variables trackées en fonction d'autres
 * variables.
 * <p>
 * Les interrupteurs sont considérés comme étant des variables "comme les autres" numérotées de 5001 à 10000
 * 
 * @author Bruju
 *
 */
public class ComposeurInitial implements ExtChangeVariable.SansAffectation, ExtCondition {
	/** Décalage donné aux interrupteurs */
	public static final int OFFSET_SWITCH = 5000;
	
	/** Association entre numéro de variables et traiteurs à appeler */
	private Set<Integer> variablesSpeciales;
	/** Etat mémoire actuel */
	private EtatMemoire etat;

	/**
	 * Construit un constructeur de formules à partir d'une base de personnages
	 * 
	 * @param contexte La base de personnages
	 * @param affecterLesAffichage Si faux, les formules seront plus simples mais moins exactes
	 */
	public ComposeurInitial(Set<Integer> set) {
		variablesSpeciales = set;

		etat = new EtatMemoire();
	}

	// Entrées / Sorties

	/**
	 * Donne un objet résultat contenant les résultats de l'exécution du ComposeurInitial
	 */
	public Map<Integer, Algorithme> getResultat() {
		Map<Integer, Algorithme> resultat = new HashMap<>();
		variablesSpeciales.forEach(id -> {
			Algorithme algo = etat.getVariable(id).toAlgorithme();
		
			if (!algo.equals(new Entree(id).toAlgorithme())) {
				resultat.put(id, algo);
			}
		});
		
		return resultat;
	}

	/* ============
	 * ACTION MAKER
	 * ============ */

	// CHANGEMENTS DE VALEUR

	// VARIABLE

	@Override
	public void changerVariable(Variable variable, OpMathematique operator, ValeurFixe valeurDroite) {
		etat.affecterVariable(variable.idVariable, operator, new Constante(valeurDroite.valeur));
	}

	@Override
	public void changerVariable(Variable variable, OpMathematique operator, ValeurAleatoire v) {
		etat.affecterVariable(variable.idVariable, operator, new NombreAleatoire(v.valeurMin, v.valeurMax));
	}

	@Override
	public void changerVariable(Variable variable, OpMathematique operator, Variable v) {
		etat.affecterVariable(variable.idVariable, operator, etat.getVariable(v.idVariable));
	}

	// CONDITIONS

	

	@Override
	public boolean herosObjet(CondHerosPossedeObjet c) {
		etat = etat.creerFils(new ConditionArme(c.idHeros, c.idObjet));
		return true;
	}

	@Override
	public boolean interrupteur(CondInterrupteur c) {
		etat = etat.creerFils(new ConditionValeur(etat.getVariable(c.interrupteur + OFFSET_SWITCH), c.etat));
		return true;
	}

	@Override
	public boolean variableVariable(int variable, Comparateur comparateur, Variable droite) {
		etat = etat.creerFils(new ConditionValeur(etat.getVariable(variable), comparateur, 
				etat.getVariable(droite.idVariable)));
		return true;
	}

	@Override
	public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		etat = etat.creerFils(new ConditionValeur(etat.getVariable(variable), comparateur, 
				new Constante(droite.valeur)));
		return true;
	}

	@Override
	public void Flot_siFin() {
		etat = etat.revenirAuPere();
	}

	@Override
	public void Flot_siNon() {
		etat = etat.getPetitFrere();
	}

	@Override
	public boolean getBooleenParDefaut() {
		return false;
	}

}
