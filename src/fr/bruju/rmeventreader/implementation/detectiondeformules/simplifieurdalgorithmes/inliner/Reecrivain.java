package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.List;
import java.util.Map;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.VisiteurDAlgorithme;

public class Reecrivain implements VisiteurDAlgorithme {
	private Algorithme source;
	private Algorithme resultat;
	private Set<InstructionAffectation> ignorer;
	private Map<InstructionGenerale, List<InstructionAffectation>> inliner;

	public Reecrivain(Algorithme algorithme, Set<InstructionAffectation> instructionsAIgnorer,
			Map<InstructionGenerale, List<InstructionAffectation>> affectationsInlinables) {
		this.source = algorithme;
		this.ignorer = instructionsAIgnorer;
		this.inliner = affectationsInlinables;
	}

	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		Algorithme sourcePere = source;
		Algorithme resultatPere = resultat;
		
		Algorithme filsVrai = construireResultat(blocConditionnel.siVrai);
		Algorithme filsFaux = construireResultat(blocConditionnel.siFaux);
		
		source = sourcePere;
		resultat = resultatPere;
		resultat.ajouterCondition(blocConditionnel.condition, filsVrai, filsFaux);
	}

	private Algorithme construireResultat(Algorithme source) {
		this.source = source;
		resultat = new Algorithme();
		source.accept(this);
		return resultat;
	}

	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		if (ignorer.contains(instructionAffectation)) {
			return;
		}
		
		InstructionAffectation instructionAAjouter;
		List<InstructionAffectation> instructionsAIntegrer = inliner.get(instructionAffectation);
		
		if (instructionsAIntegrer == null) {
			instructionAAjouter = instructionAffectation;
		} else {
			Integrateur integrateur = new Integrateur(instructionsAIntegrer);
			integrateur.visit(instructionAffectation.expression);
			instructionAAjouter = new InstructionAffectation(instructionAffectation.variableAssignee,
					integrateur.getResultat());
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
