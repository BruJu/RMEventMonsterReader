package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

import java.util.Set;

public class VariableInstanciee implements VariableUtilisee {
	public final CaseMemoire caseMemoire;
	public final int id = (int) (Math.random() * 1000);
	
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
		  .append(".")
		  .append(String.format("%03d", id))
		  .append(":")
		  .append(nom.trim())
		  .append("]");
		
		return sb.toString();
	}
	

	@Override
	public VariableInstanciee nouvelleInstance() {
		return new VariableInstanciee(caseMemoire);
	}

	@Override
	public void ajouterVariables(Set<VariableInstanciee> ensemble) {
		ensemble.add(this);
	}
}
