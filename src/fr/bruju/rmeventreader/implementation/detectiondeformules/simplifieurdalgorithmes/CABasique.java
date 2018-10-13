package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import java.util.Stack;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;

public class CABasique implements ConstructeurDAlgorithme {
	
	private Algorithme algorithmeCourant = new Algorithme();
	private Stack<Element> pile = new Stack<>();

	public Algorithme get() {
		if (!pile.isEmpty())
			throw new RuntimeException("Exécution non terminée " + pile.size());
		
		return algorithmeCourant;
	}
	
	@Override
	public void ajouter(InstructionAffectation affectation) {
		algorithmeCourant.ajouterInstruction(affectation);
	}

	@Override
	public void ajouter(InstructionAffichage affichage) {
		algorithmeCourant.ajouterInstruction(affichage);
	}

	@Override
	public int commencerCondition(Condition condition) {
		Element element = new Element(algorithmeCourant, condition);
		pile.push(element);
		algorithmeCourant = element.vrai;

		return 0;
	}

	@Override
	public void conditionElse() {
		algorithmeCourant = pile.peek().faux;
	}

	@Override
	public void conditionFinie() {
		Element element = pile.pop();
		element.integrer();
		algorithmeCourant = element.pere;
	}

	
	
	
	public static class Element {
		private final Algorithme pere;
		private final Condition condition;
		private final Algorithme vrai;
		private final Algorithme faux;

		public Element(Algorithme pere, Condition condition) {
			this.pere = pere;
			this.condition = condition;
			this.vrai = new Algorithme();
			this.faux = new Algorithme();
		}
		
		public void integrer() {
			pere.ajouterInstruction(new BlocConditionnel(condition, vrai, faux));
		}
	}
	
}
