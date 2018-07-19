package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

public class ConditionSwitch implements Condition {

	private Valeur interrupteur;
	private boolean value;

	public ConditionSwitch(Valeur interrupteur, boolean value) {
		this.interrupteur = interrupteur;
		this.value = value;
	}

	@Override
	public boolean[] tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		int[] evaluation = interrupteur.evaluer();
		int v = (value) ? 1 : 0;
		
		
		boolean[] resultat = new boolean[] {v == evaluation[0], v == evaluation[1]};
		
		return resultat;
	}

	@Override
	public String getString() {
		return ((value) ? "" : "!") + interrupteur.getString();
	}

	@Override
	public Condition revert() {
		return new ConditionSwitch(interrupteur, !value);
	}

	@Override
	public int degreDeSimilitude(Condition autre) {
		if (autre == null)
			return 0;
		
		if (!(autre instanceof ConditionSwitch))
			return 0;
		
		ConditionSwitch autreS = (ConditionSwitch) autre;
		
		if (this.interrupteur != autreS.interrupteur)
			return 1;
		
		if (this.value != autreS.value)
			return 2;
		
		return 3;
	}

	@Override
	public String getStringApresAutre(Condition autre) {
		if (degreDeSimilitude(autre) == 3) {
			return "";
		}
		
		return getString();
	}

	@Override
	public Valeur estVariableIdentiqueA() {
		return null;
	}

	@Override
	public Boolean resoudre(Affectation affectation) {
		Valeur valeurAffectee = interrupteur.evaluationPartielle(affectation);
		
		try {
			int[] evaluation = valeurAffectee.evaluer();
			
			if (evaluation[0] == evaluation[1]) {
				return (evaluation[0] == 1) ? value : !value;
			} else {
				return null;
			}
		} catch (NonEvaluableException | DependantDeStatistiquesEvaluation e) {
			return null;
		}
	}


}
