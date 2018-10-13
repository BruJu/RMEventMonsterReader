package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;

public class DefinitionUtilisation {
	public final ExprVariable variable;
	public final InstructionAffectation instructionAffectation;
	public final Set<ExprVariable> variablesUtilisees;
	public final List<InstructionGenerale> utilisations;
	
	
	public DefinitionUtilisation(InstructionAffectation instructionAffectation) {
		this.instructionAffectation = instructionAffectation;
		this.variable = instructionAffectation.variableAssignee;
		
		ListeurDePresence presences = new ListeurDePresence();
		presences.visit(instructionAffectation.expression);
		this.variablesUtilisees = presences.variablesPresentes;
		
		utilisations = new ArrayList<>();
	}
	
	public DefinitionUtilisation(DefinitionUtilisation origine) {
		this.instructionAffectation = origine.instructionAffectation;
		this.variable = origine.variable;
		this.variablesUtilisees = origine.variablesUtilisees;
		this.utilisations = new ArrayList<>(origine.utilisations);
	}
	
	public Integer getNumeroDeVariable() {
		return variable.idVariable;
	}
	
	public void ajouterUtilisation(InstructionGenerale instruction) {
		utilisations.add(instruction);
	}
	
	public boolean estMorte() {
		return utilisations.isEmpty();
	}
	
	public boolean estAUtilisationUnique() {
		return utilisations.size() == 1;
	}
}
