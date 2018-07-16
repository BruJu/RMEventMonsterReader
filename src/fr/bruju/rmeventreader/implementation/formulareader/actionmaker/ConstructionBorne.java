package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurBornement;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurNumerique;

public class ConstructionBorne {
	public enum Statut {
		CONDITIONLUE,
		CHGVARLU,
		MORT
	};
	
	private Statut statut;
	
	
	public Statut getStatut() {
		return statut;
	}
	
	
	private int idVariable;
	private Valeur valeurVariable;
	private boolean borneSup;
	@SuppressWarnings("unused")
	private int valeurDansCondition;
	private ValeurNumerique valeurDansAffectation;
	

	public ConstructionBorne(int idVariable, Valeur valeurVariable, Operator operator, int valeurCondition) {
		statut = Statut.CONDITIONLUE;
		
		this.idVariable = idVariable;
		this.valeurVariable = valeurVariable;
		
		switch (operator) {
		case INF:
		case INFEGAL:
			borneSup = false;
			break;
		case SUP:
		case SUPEGAL:
			borneSup = true;
			break;
		default:
			tuer();
		}
	}


	public void tuer() {
		statut = Statut.MORT;
	}


	public Valeur finir() {
		if (statut != Statut.CHGVARLU) {
			return null;
		}
		
		return new ValeurBornement(valeurVariable, valeurDansAffectation, borneSup);
	}


	public int getVariable() {
		return idVariable;
	}


	public void changeVariable(int idVar, Operator operator, ValeurNumerique valeurNumerique) {
		boolean doitEtreTue;
		
		doitEtreTue  = statut != Statut.CONDITIONLUE;
		doitEtreTue |= idVar != this.idVariable;
		doitEtreTue |= operator != Operator.AFFECTATION;
		
		if (doitEtreTue) {
			tuer();
			return;
		}
		
		statut = Statut.CHGVARLU;
		
		// TODO : verifier la cohérence
		
		this.valeurDansAffectation = valeurNumerique;
	}

}
