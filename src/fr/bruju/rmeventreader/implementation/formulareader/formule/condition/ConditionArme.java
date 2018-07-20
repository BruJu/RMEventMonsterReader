package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;

public class ConditionArme implements Condition {
	private int numeroPersonnage;
	private int numeroArme;
	private boolean has;
	
	
	public ConditionArme(int numeroPersonnage, int numeroArme) {
		this.numeroPersonnage = numeroPersonnage;
		this.numeroArme = numeroArme;
		this.has = true;
	}
	

	@Override
	public boolean[] tester() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		throw new DependantDeStatistiquesEvaluation();
	}

	@Override
	public String getString() {
		return numeroPersonnage + " has " + (has ? "" : "not " ) + numeroArme;
	}

	@Override
	public Condition revert() {
		ConditionArme miroir = new ConditionArme(numeroPersonnage, numeroArme);
		miroir.has = !this.has;
		return miroir;
	}


	@Override
	public Condition integrerCondition(Condition aInclure) {
		// Types identiques
		if (!(aInclure instanceof ConditionArme)) {
			return this;
		}
		
		ConditionArme aInc = (ConditionArme) aInclure;
		
		// Concerne le même personnage et la même arme
		if (!(aInc.numeroPersonnage == this.numeroPersonnage && aInc.numeroArme == this.numeroArme)) {
			return this;
		}
		
		// Simplification
		if (has == aInc.has) {
			return ConditionFixe.VRAI;
		} else {
			return ConditionFixe.FAUX;
		}
	}


}
