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
	Map<Integer, InstructionGenerale> variablesVivantes = new HashMap<>();

	@Override
	public Algorithme simplifier(Algorithme algorithme) {
		algorithme.acceptInverse(this);
		return algorithme;
	}
	
	public void noterExpression(InstructionGenerale instruction, Expression expression) {
		ListeurDePresence listeur = new ListeurDePresence();
		listeur.visit(expression);
		Set<VariableInstanciee> variablesPresentes = listeur.variablesPresentes;
		
		for (VariableInstanciee variable : variablesPresentes) {
			int numeroDeCase = variable.caseMemoire.numeroCase;
			variablesMortes.remove(numeroDeCase);
			
			modifierVariablesVivantes(numeroDeCase, instruction);
		}
	}
	
	private void modifierVariablesVivantes(int numeroDeCase, InstructionGenerale instruction) {
		if (variablesVivantes.containsKey(numeroDeCase)) {
			variablesVivantes.put(numeroDeCase, null);
		} else {
			variablesVivantes.put(numeroDeCase, instruction);
		}
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		tuer(instructionAffectation.variableAssignee.caseMemoire.numeroCase, instructionAffectation);
		noterExpression(instructionAffectation, instructionAffectation.expression);
	}

	private void tuer(int numeroVariableAffectee, InstructionAffectation instructionAffectation) {
		if (variablesMortes.contains(numeroVariableAffectee)) {
			System.out.println(instructionAffectation.variableAssignee.getString() + " est mort");
		} else {
			variablesMortes.add(numeroVariableAffectee);
			
			if (variablesVivantes.remove(numeroVariableAffectee) != null) {
				System.out.println(instructionAffectation.variableAssignee.getString() + " est inlinable");
			}
		}
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		noterCondition(blocConditionnel, blocConditionnel.condition);
		
		variablesVivantes.clear();
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

	private void noterCondition(BlocConditionnel blocConditionnel, Condition condition) {
		if (condition instanceof ConditionVariable) { // La liste des conditions n'est pas amenée à évoluer
			ConditionVariable cVariable = (ConditionVariable) condition;
			
			noterExpression(blocConditionnel, cVariable.gauche);
			noterExpression(blocConditionnel, cVariable.droite);
		}
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		// Ignoré
	}




}
