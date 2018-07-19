package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

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
	public int degreDeSimilitude(Condition autre) {
		if (autre == null)
			return 0;
		
		if (autre.getClass() != this.getClass())
			return 0;
		
		ConditionArme autreC = (ConditionArme) autre;
		
		if (autreC.numeroPersonnage != numeroPersonnage)
			return 0;
		
		if (autreC.numeroArme != numeroArme)
			return 1;
		
		if (autreC.has != has)
			return 2;
		
		return 3;
	}

	@Override
	public String getStringApresAutre(Condition autre) {
		if (degreDeSimilitude(autre) == 2)
			return "";
		
		return getString();
	}

	@Override
	public Valeur estVariableIdentiqueA() {
		return null;
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


}
