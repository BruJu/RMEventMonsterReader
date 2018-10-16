package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;

public class Reecrivain implements VisiteurDAlgorithme {
	private Algorithme source;
	private Algorithme resultat;
	
	//private Set<InstructionAffectation> ignorer;
	private Map<InstructionGenerale, Map<ExprVariable, InstructionAffectation>> inliner;

	public Reecrivain(Algorithme algorithme, DetecteurDeSimplifications detecteur) {
		this.source = algorithme;
		//this.ignorer = detecteur.instructionsAIgnorer;
		transformerEnMapDeMap(detecteur.affectationsInlinables);
	}

	private void transformerEnMapDeMap(Map<InstructionGenerale, List<InstructionAffectation>> affectationsInlinables) {
		inliner = new HashMap<>();
		
		affectationsInlinables.forEach((destination, sources) -> {
			Map<ExprVariable, InstructionAffectation> carteInterne = new HashMap<>();
			sources.forEach(instruction -> carteInterne.put(instruction.variableAssignee, instruction));
			inliner.put(destination, carteInterne);
		});
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		Condition condition;
		
		if (!inliner.containsKey(blocConditionnel)) {
			condition = blocConditionnel.condition;
		} else {
			ConditionVariable conditionVariable = (ConditionVariable) blocConditionnel.condition;
			
			Integrateur integrateur = new Integrateur(blocConditionnel, inliner);
			Expression nouvelleGauche = integrateur.explorer(conditionVariable.gauche);
			Expression nouvelleDroite = integrateur.explorer(conditionVariable.droite);
			
			condition = new ConditionVariable(nouvelleGauche, conditionVariable.comparateur, nouvelleDroite);
		}
		
		Algorithme sourcePere = source;
		Algorithme resultatPere = resultat;
		
		Algorithme filsVrai = construireResultat(blocConditionnel.siVrai);
		Algorithme filsFaux = construireResultat(blocConditionnel.siFaux);
		
		source = sourcePere;
		resultat = resultatPere;
		
		resultat.ajouterInstruction(new BlocConditionnel(condition, filsVrai, filsFaux));
	}

	private Algorithme construireResultat(Algorithme source) {
		this.source = source;
		resultat = new Algorithme();
		source.accept(this);
		return resultat;
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		if (inliner.containsKey(instructionAffectation)) {
			return;
		}
		
		InstructionAffectation instructionAAjouter;
		Map<ExprVariable, InstructionAffectation> instructionsAIntegrer = inliner.get(instructionAffectation);
		
		if (instructionsAIntegrer == null) {
			instructionAAjouter = instructionAffectation;
		} else {
			Integrateur integrateur = new Integrateur(instructionAffectation, inliner);
			instructionAAjouter = new InstructionAffectation(instructionAffectation.variableAssignee,
					integrateur.explorer(instructionAffectation.expression));
		}
		
		resultat.ajouterInstruction(instructionAAjouter);
	}

	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		resultat.ajouterInstruction(instructionAffichage);
	}

	public Algorithme produireResultat() {
		if (resultat == null) {
			resultat = new Algorithme();
			source.accept(this);
		}
		
		return resultat;
	}

}
