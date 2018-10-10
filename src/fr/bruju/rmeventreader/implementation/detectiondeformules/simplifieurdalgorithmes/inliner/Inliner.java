package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.CaseMemoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;

public class Inliner implements Simplification, VisiteurDAlgorithme.IntegreConditionnel {
	Map<CaseMemoire, DefinitionUtilisation> utilisations = new HashMap<>();
	
	//HashMap<InstructionAffectation, InstructionAffectation> utilisations = new HashMap<>();
	
	@Override
	public Algorithme simplifier(Algorithme algorithme) {
		algorithme.accept(this);
		
		return algorithme;
	}
	
	public void noterUtilisation(InstructionAffectation instruction, VariableInstanciee variable) {
		CaseMemoire caseMemoire = variable.caseMemoire;
		
		DefinitionUtilisation utilisation = utilisations.get(caseMemoire);
		if (utilisation == null) {
			return;
		}
		
		utilisation.ajouterUtilisation(instruction);
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {

		ListeurDePresence listeur = new ListeurDePresence();
		listeur.visit(instructionAffectation.expression);
		Set<VariableInstanciee> variablesPresentes = listeur.variablesPresentes;
		
		for (VariableInstanciee variable : variablesPresentes) {
			noterUtilisation(instructionAffectation, variable);
		}
		
		CaseMemoire caseMemoire = instructionAffectation.variableAssignee.caseMemoire;
		
		DefinitionUtilisation definition = utilisations.get(caseMemoire);
		if (definition != null) {
			if (definition.estMorte()) {
				System.out.println(definition.variable.getString() + " est morte");
			} else if (definition.estAUtilisationUnique()) {
				System.out.println(definition.variable.getString() + " n'a qu'une utilisation");
			}
		}
		
		DefinitionUtilisation nouvelleDefinition = new DefinitionUtilisation(instructionAffectation);
		utilisations.put(caseMemoire, nouvelleDefinition);
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		// Ignoré
	}

	@Override
	public void conditionDebut(Condition condition) {
		fermerUtilisations();
	}

	private void fermerUtilisations() {
		utilisations.clear();
	}

	@Override
	public void conditionSinon() {
		fermerUtilisations();
	}

	@Override
	public void conditionFin() {
		fermerUtilisations();
	}
}
