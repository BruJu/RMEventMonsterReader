package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;

public class Inliner implements Simplification, VisiteurDAlgorithme.IntegreConditionnel {
	HashMap<InstructionAffectation, InstructionAffectation> utilisations = new HashMap<>();
	
	@Override
	public Algorithme simplifier(Algorithme algorithme) {
		algorithme.accept(this);
		
		return algorithme;
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		ListeurDePresence listeur = new ListeurDePresence();
		listeur.visit(instructionAffectation.expression);
		Set<VariableInstanciee> variablesPresentes = listeur.variablesPresentes;
		
		for (VariableInstanciee present : variablesPresentes) {
			for (InstructionAffectation affectation : utilisations.keySet()) {
				if (affectation.variableAssignee != present) {
					continue;
				}
				
				actualiserUtilisation(affectation, instructionAffectation);
			}
		}
		
		utilisations.put(instructionAffectation, null);
	}

	private void actualiserUtilisation(InstructionAffectation precedent,
			InstructionAffectation actuel) {
		
		if (utilisations.get(precedent) == null) {
			utilisations.put(precedent, actuel);
		} else {
			utilisations.remove(precedent);
		}
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
		/*
		 * Les effets ne doivent être explorés que quand la variable est réecrite
		utilisations.forEach((instructionAffectee, utilisation) -> {
			if (utilisation == null) {
				System.out.print(instructionAffectee.variableAssignee.getString() + " inutile ;");
			} else {
				System.out.print(instructionAffectee.variableAssignee.getString() + " dans " + utilisation.variableAssignee.getString() + " ;");
			}
			System.out.println();
			
		});
		*/
		
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
