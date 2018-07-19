package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.rmdatabase.Affectation;
import fr.bruju.rmeventreader.rmdatabase.AffectationFlexible;

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
	public Boolean resoudre(Affectation affectation) {
		Boolean etat = affectation.herosPossedeEquipement(numeroPersonnage, numeroArme);
		
		if (etat == null) {
			return null;
		}
		
		if (etat.booleanValue() == has) {
			return Boolean.TRUE;
		} else {
			return Boolean.FALSE;
		}
	}


	@Override
	public Condition evaluationPartielle(Affectation affectation) {
		Boolean etat = affectation.herosPossedeEquipement(numeroPersonnage, numeroArme);
		
		if (etat == null) {
			return this;
		} else {
			return new ConditionFixe(etat);
		}
	}


	@Override
	public void modifierAffectation(AffectationFlexible affectation) throws AffectationNonFaisable {
		if (has) {
			affectation.putObjetEquipe(numeroPersonnage, numeroArme);
		} else {
			Boolean isOwned = affectation.herosPossedeEquipement(numeroPersonnage, numeroArme);
			
			if (isOwned) {
				throw new AffectationNonFaisable();
			}
		}
	}


}
