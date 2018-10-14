package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import fr.bruju.rmeventreader.implementation.detectiondeformules._variables.EtatInitial;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur.SubstitutionDeValeurs;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class CABasique implements ConstructeurDAlgorithme {
	
	private Algorithme algorithmeCourant = new Algorithme();
	private Stack<Element> pile = new Stack<>();
	
	private Map<Integer, Integer> valeursCourantes = null;
	
	
	public CABasique() {
		EtatInitial etatInitial = EtatInitial.getEtatInitial();
		valeursCourantes = new HashMap<>();
		etatInitial.forEach(valeursCourantes::put);
	}
	
	

	public Algorithme get() {
		if (!pile.isEmpty())
			throw new RuntimeException("Exécution non terminée " + pile.size());
		
		return algorithmeCourant;
	}
	
	@Override
	public void ajouter(InstructionAffectation affectation) {
		affectation = reecrire(affectation);
		algorithmeCourant.ajouterInstruction(affectation);
	}

	private InstructionAffectation reecrire(InstructionAffectation affectation) {
		SubstitutionDeValeurs substitueur = new SubstitutionDeValeurs(valeursCourantes);
		
		Expression nouvelleExpression = substitueur.explorer(affectation.expression);
		
		if (nouvelleExpression != affectation.expression) {
			affectation = new InstructionAffectation(affectation.variableAssignee, nouvelleExpression);
		}
		
		Integer evaluation = nouvelleExpression.evaluer();
		valeursCourantes.put(affectation.variableAssignee.idVariable, evaluation);
		
		return affectation;
	}



	@Override
	public void ajouter(InstructionAffichage affichage) {
		algorithmeCourant.ajouterInstruction(affichage);
	}

	@Override
	public int commencerCondition(Condition condition) {
		if (condition instanceof ConditionVariable) {
			ConditionVariable cv = (ConditionVariable) condition;

			SubstitutionDeValeurs substitueur = new SubstitutionDeValeurs(valeursCourantes);
			
			Expression gauche = substitueur.explorer(cv.gauche);
			Expression droite = substitueur.explorer(cv.gauche);
			
			if (gauche != cv.gauche || droite != cv.droite) {
				cv = new ConditionVariable(gauche, cv.comparateur, droite);
				condition = cv;
			}
			
			Boolean test = cv.tester();
			if (test != null) {
				Element element = new Element(algorithmeCourant, condition, valeursCourantes, test);
				pile.push(element);
				algorithmeCourant = element.vrai;
				valeursCourantes = element.valeursCourantesVrai;

				return 0;
			}
			
			
			
			
		}
		
		
		
		Element element = new Element(algorithmeCourant, condition, valeursCourantes);
		pile.push(element);
		algorithmeCourant = element.vrai;
		valeursCourantes = element.valeursCourantesVrai;

		return 0;
	}

	@Override
	public void conditionElse() {
		algorithmeCourant = pile.peek().faux;
		valeursCourantes = pile.peek().valeursCourantesFaux;
	}

	@Override
	public void conditionFinie() {
		Element element = pile.pop();
		element.integrer();
		algorithmeCourant = element.pere;
		
		valeursCourantes = element.getValeursCourantes();

	}

	
	private static Integer combiner(Integer vrai, Integer faux) {
		return Objects.equals(vrai, faux) ? vrai : null;
	}




	public static class Element {
		private final Algorithme pere;
		private final Condition condition;
		private final Algorithme vrai;
		private final Map<Integer, Integer> valeursCourantesVrai;
		private final Algorithme faux;
		private final Map<Integer, Integer> valeursCourantesFaux;
		
		private final Boolean c;

		public Element(Algorithme pere, Condition condition, Map<Integer, Integer> valeursCourantes) {
			this.pere = pere;
			this.condition = condition;
			this.vrai = new Algorithme();
			this.valeursCourantesVrai = new HashMap<>(valeursCourantes);
			this.faux = new Algorithme();
			this.valeursCourantesFaux = new HashMap<>(valeursCourantes);
			c = null;
		}
		
		public Element(Algorithme pere, Condition condition, Map<Integer, Integer> valeursCourantes, Boolean c) {
			this.pere = pere;
			this.condition = condition;
			this.vrai = new Algorithme();
			this.valeursCourantesVrai = new HashMap<>(valeursCourantes);
			this.faux = new Algorithme();
			this.valeursCourantesFaux = new HashMap<>(valeursCourantes);
			this.c = c;
		}
		
		public void integrer() {
			if (c != null) {
				pere.ajouter(c ? vrai : faux);
			} else {
				pere.ajouterInstruction(new BlocConditionnel(condition, vrai, faux));
			}
		}
		
		public Map<Integer, Integer> getValeursCourantes() {
			Map<Integer, Integer> valeursCourantes = new HashMap<>();
			
			Utilitaire.Maps.combiner(valeursCourantes, valeursCourantesVrai, valeursCourantesFaux, CABasique::combiner);
			
			return valeursCourantes;
		}
	}
	
}
