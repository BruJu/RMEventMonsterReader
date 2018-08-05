package fr.bruju.rmeventreader.implementation.recomposeur.actionmaker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionArme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.NombreAleatoire;

/**
 * Constructeur de formules à partir d'un fichier pour donner le contenu des variables trackées en fonction d'autres
 * variables.
 * 
 * @author Bruju
 *
 */
public class ComposeurInitial implements ActionMakerDefalse {
	public static final int OFFSET_SWITCH = 5000;
	

	/** Association entre numéro de variables et traiteurs à appeler */
	private Set<Integer> variablesSpeciales;

	private EtatMemoire etat;

	/**
	 * Construit un constructeur de formules à partir d'une base de personnages
	 * 
	 * @param contexte La base de personnages
	 * @param affecterLesAffichage Si faux, les formules seront plus simples mais moins exactes
	 */
	public ComposeurInitial(List<Integer> variablesAPister) {
		variablesSpeciales = variablesAPister.stream().collect(Collectors.toSet());

		etat = new EtatMemoire();
	}

	// Entrées / Sorties

	/**
	 * Donne un objet résultat contenant les résultats de l'exécution du ComposeurInitial
	 */
	public Map<Integer, Algorithme> getResultat() {
		Map<Integer, Algorithme> resultat = new HashMap<>();
		variablesSpeciales.forEach(id -> resultat.put(id, etat.getVariable(id).toAlgorithme()));
		return resultat;
	}

	/* ============
	 * ACTION MAKER
	 * ============ */

	// CHANGEMENTS DE VALEUR

	// VARIABLE

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe valeurDroite) {
		etat.affecterVariable(variable.idVariable, operator, new Constante(valeurDroite.valeur));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire v) {
		etat.affecterVariable(variable.idVariable, operator, new NombreAleatoire(v.valeurMin, v.valeurMax));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, Variable v) {
		etat.affecterVariable(variable.idVariable, operator, etat.getVariable(v.idVariable));
	}

	// CONDITIONS - OFFSET_SWITCH

	@Override
	public boolean condOnSwitch(int number, boolean valeur) {
		etat = etat.creerFils(new ConditionValeur(etat.getVariable(number + OFFSET_SWITCH), valeur));
		return true;
	}

	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		etat = etat.creerFils(new ConditionArme(heroId, itemId));
		return true;
	}

	@Override
	public boolean condOnVariable(int idVariable, Operator operatorValue, ValeurFixe fixe) {
		etat = etat.creerFils(new ConditionValeur(etat.getVariable(idVariable), operatorValue, 
				new Constante(fixe.valeur)));
		return true;
	}

	@Override
	public boolean condOnVariable(int idVariable, Operator operatorValue, Variable variable) {
		etat = etat.creerFils(new ConditionValeur(etat.getVariable(idVariable), operatorValue, 
				etat.getVariable(variable.idVariable)));
		return true;
	}

	@Override
	public void condElse() {
		etat = etat.getPetitFrere();
	}

	@Override
	public void condEnd() {
		etat = etat.revenirAuPere();
	}
}
