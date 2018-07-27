package fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;

// TODO : transformer evaluation simple en évaluation rapide

public class EvaluationSimple extends VisiteurRetourneur<Integer> {
	/*
	 * Dans la mesure où il s'agit d'une classe utilitaire utilisée par tous les visiteurs constructeurs de composants,
	 * cette classe est mise en singleton.
	 */
	private static EvaluationSimple instance;

	private EvaluationSimple() {
	}

	public static EvaluationSimple getInstance() {
		if (null == instance) {
			instance = new EvaluationSimple();
		}
		return instance;
	}
	
	/* =========
	 * VISITEURS
	 * ========= */

	@Override
	protected Integer traiter(BConstant boutonConstant) {
		return (boutonConstant.value) ? 1 : 0;
	}

	@Override
	protected Integer traiter(BTernaire boutonTernaire) {
		Integer c = traiter(boutonTernaire.condition);
		
		if (c == null)
			return null;
		
		return (c == 1) ? traiter(boutonTernaire.siVrai) : traiter(boutonTernaire.siFaux);
	}

	@Override
	protected Integer traiter(CSwitch conditionSwitch) {
		Integer r = traiter(conditionSwitch.interrupteur);
		
		if (r == null)
			return null;
		
		if (conditionSwitch.valeur)
			return r;
		else
			return 1 - r;
	}

	@Override
	protected Integer traiter(CVariable conditionVariable) {
		Integer g = traiter(conditionVariable.gauche);
		Integer d = traiter(conditionVariable.droite);
		
		if (g == null || d == null)
			return null;
		
		return conditionVariable.operateur.test(g, d) ? 1 : 0;
	}

	@Override
	protected Integer traiter(VCalcul variableCalcul) {
		Integer g = traiter(variableCalcul.gauche);
		Integer d = traiter(variableCalcul.droite);
		
		if (g == null || d == null)
			return null;
		
		return variableCalcul.operateur.compute(g, d);
	}

	@Override
	protected Integer traiter(VConstante variableConstante) {
		return variableConstante.valeur;
	}

	@Override
	protected Integer traiter(VTernaire variableTernaire) {
		Integer c = traiter(variableTernaire.condition);
		
		if (c == null)
			return null;
		
		return (c == 1) ? traiter(variableTernaire.siVrai) : traiter(variableTernaire.siFaux);
	}
	
	
	public void visit(CFixe composant) {
		this.composant = traiter(composant);
	}
	
	protected Integer traiter(CFixe composant) {
		return composant.value ? 1 : 0;
	}

	@Override
	protected Integer comportementParDefaut(Composant composant) {
		return null;
	}

}
