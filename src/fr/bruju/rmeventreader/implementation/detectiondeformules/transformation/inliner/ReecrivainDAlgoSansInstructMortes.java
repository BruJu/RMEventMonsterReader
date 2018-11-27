package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.inliner;

import java.util.*;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.util.Pair;

class ReecrivainDAlgoSansInstructMortes implements VisiteurDAlgorithme {
	public static Algorithme reecrireSansAffectationMorte(Algorithme algorithme,
														  AnalyseurDUtilisationsDesInstructions analyse) {
		ReecrivainDAlgoSansInstructMortes instance = new ReecrivainDAlgoSansInstructMortes(analyse);
		return instance.construireResultat(algorithme);
	}

	/* ============
	 * CONSTRUCTEUR
	 * ============ */

	private Algorithme resultat;
	
	private Set<InstructionAffectation> ignorer;
	private Map<InstructionGenerale, Map<ExprVariable, InstructionAffectation>> inliner;

	private ReecrivainDAlgoSansInstructMortes(AnalyseurDUtilisationsDesInstructions detecteur) {
		this.ignorer = detecteur.instructionsAIgnorer;
		this.inliner = new HashMap<>();

		detecteur.affectationsInlinables.forEach(this::ajouter);
	}

	/**
	 * Transforme la liste d'affectations à inliner en une table associant Variable modifiée et instruction
	 * d'affectation et ajouter dans la liste des instructions à inliner.
	 * <br>ie chaque InstructionAffectation x = z devient une paire (x, x = z)
	 */
	private void ajouter(InstructionGenerale instructionModifiee, List<InstructionAffectation> affectations) {
		Map<ExprVariable, InstructionAffectation> carte = affectations.stream()
				.map(affectation -> new Pair<>(affectation.variableAssignee, affectation))
				.collect(Pair.toMapWithDuplicate());

		inliner.put(instructionModifiee, carte);
	}


	/* ===================
	 * CONSTRUIRE RESULTAT
	 * =================== */

	private Algorithme construireResultat(Algorithme source) {
		resultat = new Algorithme();
		source.accept(this);
		return resultat;
	}


	/* =======================
	 * VISITE DES INSTRUCTIONS
	 * ======================= */

	@Override
	public void visit(BlocConditionnel blocConditionnel) {
		Condition condition;
		
		if (!inliner.containsKey(blocConditionnel)) {
			condition = blocConditionnel.condition;
		} else {
			// Reecriture de la condition
			ConditionVariable conditionVariable = (ConditionVariable) blocConditionnel.condition;
			
			InlinerDExpressions inlinerDExpressions = new InlinerDExpressions(blocConditionnel, inliner);
			Expression nouvelleGauche = inlinerDExpressions.explorer(conditionVariable.gauche);
			Expression nouvelleDroite = inlinerDExpressions.explorer(conditionVariable.droite);
			
			condition = new ConditionVariable(nouvelleGauche, conditionVariable.comparateur, nouvelleDroite);
		}

		// Reecriture des branches

		Algorithme resultatPere = resultat;
		
		Algorithme filsVrai = construireResultat(blocConditionnel.siVrai);
		Algorithme filsFaux = construireResultat(blocConditionnel.siFaux);

		resultat = resultatPere;
		
		resultat.ajouterInstruction(new BlocConditionnel(condition, filsVrai, filsFaux));
	}


	@Override
	public void visit(InstructionAffectation instructionAffectation) {
		if (ignorer.contains(instructionAffectation)) {
			// Morte
			return;
		}

		// Intégration des instructions à inliner si nécessaire

		Map<ExprVariable, InstructionAffectation> instructionsAIntegrer = inliner.get(instructionAffectation);

		InstructionAffectation instructionAAjouter;
		if (instructionsAIntegrer == null) {
			instructionAAjouter = instructionAffectation;
		} else {
			InlinerDExpressions inlinerDExpressions = new InlinerDExpressions(instructionAffectation, inliner);
			instructionAAjouter = new InstructionAffectation(instructionAffectation.variableAssignee,
					inlinerDExpressions.explorer(instructionAffectation.expression));
		}
		
		resultat.ajouterInstruction(instructionAAjouter);
	}


	@Override
	public void visit(InstructionAffichage instructionAffichage) {
		// Les affichages sont recopiés
		resultat.ajouterInstruction(instructionAffichage);
	}
}
