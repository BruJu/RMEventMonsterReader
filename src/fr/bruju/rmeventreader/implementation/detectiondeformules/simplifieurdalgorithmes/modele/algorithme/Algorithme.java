package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Calcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;

public class Algorithme {
	private List<InstructionGenerale> instructions = new ArrayList<>();
	private int derniereVariable = 0;

	public void ajouterInstruction(InstructionGenerale instruction) {
		instructions.add(instruction);
		derniereVariable = 0;
	}
	
	public void ajouterInstruction(InstructionAffectation instruction) {
		instructions.add(instruction);
		derniereVariable = instruction.variableAssignee.caseMemoire.numeroCase;
	}
	
	public boolean accumuler(int idVariable, OpMathematique operateur, Expression expression) {
		if (idVariable != derniereVariable)
			return false;
		
		InstructionAffectation derniereInstruction = (InstructionAffectation) instructions.get(instructions.size() - 1);
		instructions.remove(instructions.size() - 1);
		
		VariableInstanciee variableAssignee = derniereInstruction.variableAssignee;
		Expression nouvelleDroite;
		
		if (operateur == OpMathematique.AFFECTATION) {
			nouvelleDroite = expression;
		} else {
			nouvelleDroite = new Calcul(derniereInstruction.expression, operateur, expression);
		}
		
		InstructionAffectation instructionReecrite = new InstructionAffectation(variableAssignee, nouvelleDroite);
		
		instructions.add(instructionReecrite);
		
		return true;
	}
	

	public boolean estVide() {
		for (InstructionGenerale instruction : instructions) {
			if (!instruction.estVide())
				return false;
		}
		
		return true;
	}
	
	public String getString() {
		ListeurDInstructions sb = new ListeurDInstructions();
		append(sb);
		return sb.toString();
	}

	public void ajouterCondition(Condition conditionSeparatrice, Algorithme algorithmeVrai, Algorithme algorithmeFaux) {
		instructions.add(new BlocConditionnel(conditionSeparatrice, algorithmeVrai, algorithmeFaux));
		this.derniereVariable = 0;
	}

	public void append(ListeurDInstructions sb) {
		for (InstructionGenerale instruction : instructions) {
			instruction.append(sb);
		}
	}

	public void accept(VisiteurDAlgorithme visiteurDAlgorithme) {
		instructions.forEach(visiteurDAlgorithme::visit);
	}
	
	public void acceptInverse(VisiteurDAlgorithme visiteurDAlgorithme) {
		for (int i = instructions.size() - 1 ; i >= 0 ; i--) {
			instructions.get(i).accept(visiteurDAlgorithme);
		}
	}
}
