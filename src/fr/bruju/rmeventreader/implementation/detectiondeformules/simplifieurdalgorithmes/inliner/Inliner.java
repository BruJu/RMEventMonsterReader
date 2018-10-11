package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.function.BiFunction;

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

public class Inliner implements Simplification, VisiteurDAlgorithme {
	Map<CaseMemoire, DefinitionUtilisation> utilisations = new HashMap<>();
	Map<VariableInstanciee, StatutVariable> statut = new HashMap<>();
	
	
	@Override
	public Algorithme simplifier(Algorithme algorithme) {
		algorithme.accept(this);
		
		return algorithme;
	}
	
	public void noterExpression(InstructionGenerale instruction, Expression expression) {
		ListeurDePresence listeur = new ListeurDePresence();
		listeur.visit(expression);
		Set<VariableInstanciee> variablesPresentes = listeur.variablesPresentes;
		
		for (VariableInstanciee variable : variablesPresentes) {
			noterUtilisation(instruction, variable);
		}
	}
	
	public void noterUtilisation(InstructionGenerale instruction, VariableInstanciee variable) {
		statut.remove(variable);
		CaseMemoire caseMemoire = variable.caseMemoire;
		
		DefinitionUtilisation utilisation = utilisations.get(caseMemoire);
		if (utilisation == null) {
			return;
		}
		
		utilisation.ajouterUtilisation(instruction);
	}
	
	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		noterExpression(instructionAffectation, instructionAffectation.expression);
		
		CaseMemoire caseMemoire = instructionAffectation.variableAssignee.caseMemoire;
		
		DefinitionUtilisation definition = utilisations.get(caseMemoire);
		if (definition != null) {
			statut.computeIfPresent(definition.variable, StatutVariable::tuer);
			
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
	public void visit(BlocConditionnel blocConditionnel) {
		// UTILISATION
		Condition condition = blocConditionnel.condition;
		
		if (condition instanceof ConditionVariable) { // La liste des conditions n'est pas amenée à évoluer
			ConditionVariable cVariable = (ConditionVariable) condition;
			
			noterExpression(blocConditionnel, cVariable.gauche);
			noterExpression(blocConditionnel, cVariable.droite);
		}
		
		// Redéfinitions / Utilisations dans les sous blocs
		
		Inliner blocSi = cloner();
		Inliner blocSinon = cloner();
		blocSi.visit(blocConditionnel.siVrai);
		blocSinon.visit(blocConditionnel.siFaux);
		
		Iterator<Entry<CaseMemoire, DefinitionUtilisation>> iterateur = utilisations.entrySet().iterator();
		
		while (iterateur.hasNext()) {
			Entry<CaseMemoire, DefinitionUtilisation> entree = iterateur.next();
			DefinitionUtilisation definition = entree.getValue();
			
			VariableInstanciee variableDonnee = definition.variable;
			StatutVariable statutSi = blocSi.statut.get(variableDonnee);
			StatutVariable statutSinon = blocSinon.statut.get(variableDonnee);
			
			if (statutSi == StatutVariable.DEFINIE && statutSinon == StatutVariable.DEFINIE) {
				// il faut garder
			} else if (statutSi == StatutVariable.MORTE && statutSinon == StatutVariable.MORTE) {
				System.out.println(definition.variable.getString() + " est morte");
			} else {
				// On enlève
				iterateur.remove();
			}
		}
	}

	private Inliner cloner() {
		Inliner clone = new Inliner();
		remplirStatut(clone.statut);
		return clone;
	}

	private void remplirStatut(Map<VariableInstanciee, StatutVariable> statutARemplir) {
		utilisations.values().forEach(definition -> statutARemplir.put(definition.variable, StatutVariable.DEFINIE));
	}
}
