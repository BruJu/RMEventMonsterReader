package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

public class Instruction {
	private Variable variableAssignee;
	private Expression expression;
	
	
	public Instruction(int idVariable, int valeur) {
		this.variableAssignee = new Variable("VAR["+idVariable+"]"); // Ne pas faire ça
		this.expression = new Constante(valeur);
	}


	public void append(StringBuilder sb) {
		sb.append(variableAssignee.getString())
		  .append(" = ")
		  .append(expression.getString());
	}
}
