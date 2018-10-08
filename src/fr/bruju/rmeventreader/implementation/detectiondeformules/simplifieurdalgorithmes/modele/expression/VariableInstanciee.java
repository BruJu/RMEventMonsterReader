package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class VariableInstanciee implements Expression {
	public final CaseMemoire caseMemoire;
	
	public VariableInstanciee(CaseMemoire caseMemoire) {
		this.caseMemoire = caseMemoire;
	}

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();
		String nom;
		int coef;
		
		if (caseMemoire.numeroCase < 0) {
			coef = -1;
			sb.append("S[");
			nom = PROJET.extraireInterrupteur(-caseMemoire.numeroCase);
		} else {
			coef = 1;
			sb.append("V[");
			nom = PROJET.extraireVariable(caseMemoire.numeroCase);
		}
		
		sb.append(String.format("%04d", caseMemoire.numeroCase * coef))
		  .append(":")
		  .append(nom.trim())
		  .append("]");
		
		return sb.toString();
	}
	
	public VariableInstanciee nouvelleInstance() {
		return new VariableInstanciee(caseMemoire);
	}
}
