package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNumerique;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurSwitch;
import fr.bruju.rmeventreader.rmdatabase.Affectation;
import fr.bruju.rmeventreader.rmdatabase.AffectationFlexible;

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

	@Override
	public Condition evaluationPartielle(Affectation affectation) {
		return new ConditionSwitch(interrupteur.evaluationPartielle(affectation), value);
	}

	@Override
	public void modifierAffectation(AffectationFlexible affectation) throws AffectationNonFaisable {
		if (!(interrupteur instanceof ValeurSwitch)) {
			if (interrupteur instanceof ValeurNumerique) {
				if (((ValeurNumerique) interrupteur).getValue() == 1) {
					return;
				}
			}
			
			throw new AffectationNonFaisable();
		}
		
		ValeurSwitch valeurSwitch = (ValeurSwitch) interrupteur;
		
		Boolean valeurDansAffectation = affectation.getInterrupteur(valeurSwitch.getIdSwitch());
		
		if (valeurDansAffectation == null) {
			affectation.putInterrupteur(valeurSwitch.getIdSwitch(), value);
		} else {
			if (valeurDansAffectation != value) {
				throw new AffectationNonFaisable();
			}
		}	
	}

	@Override
	public Condition integrerCondition(Condition aInclure) {
		// Types identiques
		if (!(aInclure instanceof ConditionSwitch)) {
			return this;
		}
		
		ConditionSwitch aInc = (ConditionSwitch) aInclure;
		
		// Concerne la même valeur
		if (!(interrupteur == aInc.interrupteur)) {
			return this;
		}
		
		// Simplification
		if (value == aInc.value) {
			return ConditionFixe.VRAI;
		} else {
			return ConditionFixe.FAUX;
		}
	}


}
