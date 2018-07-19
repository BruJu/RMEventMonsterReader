package fr.bruju.rmeventreader.implementation.formulareader.formule.condition;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.rmdatabase.Affectation;

public interface Condition {
	public boolean testerMax() throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	
	public boolean testerMin() throws NonEvaluableException, DependantDeStatistiquesEvaluation;
	
	public String getString();
	
	public Condition revert();
	
	public int degreDeSimilitude(Condition autre);
	
	public String getStringApresAutre(Condition autre);
	
	public Valeur estVariableIdentiqueA();
	
	/**
	 * Evalue la condition avec l'affectation donnée
	 * @param affectation L'affectation
	 * @return null si l'affectation ne permet pas de déduire si la condition est respectée ou non, vrai si elle est
	 * respectée, faux sinon.
	 */
	public Boolean resoudre(Affectation affectation);
	
}