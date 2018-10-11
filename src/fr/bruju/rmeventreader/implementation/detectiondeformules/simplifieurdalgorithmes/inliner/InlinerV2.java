package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.CaseMemoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;

public class InlinerV2 implements Simplification, VisiteurDAlgorithme {
	
	Set<Integer> variablesMortes = new HashSet<>(); 

	@Override
	public Algorithme simplifier(Algorithme algorithme) {
		algorithme.acceptInverse(this);
		return algorithme;
	}
	
	public void noterExpression(Expression expression) {
		ListeurDePresence listeur = new ListeurDePresence();
		listeur.visit(expression);
		Set<VariableInstanciee> variablesPresentes = listeur.variablesPresentes;
		
		for (VariableInstanciee variable : variablesPresentes) {
			variablesMortes.remove(variable.caseMemoire.numeroCase);
		}
	}
	
	
	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		tuer(instructionAffectation.variableAssignee.caseMemoire.numeroCase, instructionAffectation);
		noterExpression(instructionAffectation.expression);
	}

	private void tuer(int numeroVariableAffectee, InstructionAffectation instructionAffectation) {
		if (variablesMortes.contains(numeroVariableAffectee)) {
			System.out.println(instructionAffectation.variableAssignee.getString() + " est mort");
		} else {
			variablesMortes.add(numeroVariableAffectee);
		}
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		noterCondition(blocConditionnel.condition);
		
		Set<Integer> variablesMortesSi = new HashSet<>(variablesMortes);
		Set<Integer> variablesMortesSinon = new HashSet<>(variablesMortes);
		Set<Integer> variablesMortesApres = variablesMortes;
		
		
		variablesMortes = variablesMortesSi;
		blocConditionnel.siVrai.acceptInverse(this);
		variablesMortes = variablesMortesSinon;
		blocConditionnel.siFaux.acceptInverse(this);

		variablesMortes = variablesMortesApres;
		variablesMortes.clear();
		
		variablesMortesSi.forEach(caseMemoire -> {
			if (variablesMortesSinon.contains(caseMemoire)) {
				variablesMortes.add(caseMemoire);
			}
		});
		
	}

	private void noterCondition(Condition condition) {
		if (condition instanceof ConditionVariable) { // La liste des conditions n'est pas amenée à évoluer
			ConditionVariable cVariable = (ConditionVariable) condition;
			
			noterExpression(cVariable.gauche);
			noterExpression(cVariable.droite);
		}
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		// Ignoré
	}




}
