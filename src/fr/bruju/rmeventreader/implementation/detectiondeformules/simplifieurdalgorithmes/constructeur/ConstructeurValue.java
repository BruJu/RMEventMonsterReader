package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.constructeur;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import fr.bruju.rmeventreader.implementation.detectiondeformules._variables.EtatInitial;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class ConstructeurValue implements ConstructeurDAlgorithme {
	
	private Algorithme algorithmeCourant = new Algorithme();
	private Stack<ExplorationConditionnelle> pile = new Stack<>();
	
	private Map<Integer, Integer> valeursCourantes = null;
	
	
	public ConstructeurValue() {
		EtatInitial etatInitial = EtatInitial.getEtatInitial();
		valeursCourantes = new HashMap<>();
		etatInitial.forEach(valeursCourantes::put);
	}
	
	public ConstructeurValue(Map<Integer, Integer> valeursInitiales) {
		this.valeursCourantes = new HashMap<>(valeursInitiales);
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
			Expression droite = substitueur.explorer(cv.droite);
			
			if (gauche != cv.gauche || droite != cv.droite) {
				cv = new ConditionVariable(gauche, cv.comparateur, droite);
				condition = cv;
			}
			
			Boolean test = cv.tester();
			
			if (test != null) {
				pile.push(new ExplorationPartielle());
				pile.peek().utiliser();
				return test ? 1 : 2;
			}
		}
		
		
		
		Element element = new Element(algorithmeCourant, condition, valeursCourantes);
		pile.push(element);
		element.utiliser();
		
		return 0;
	}

	@Override
	public void conditionElse() {
		pile.peek().recevoirSinon();
	}

	@Override
	public void conditionFinie() {
		pile.pop().recevoirFin();
	}

	
	private static Integer combiner(Integer vrai, Integer faux) {
		return Objects.equals(vrai, faux) ? vrai : null;
	}



	public static interface ExplorationConditionnelle {
		public void utiliser();
		public void recevoirSinon();
		public void recevoirFin();
	}
	
	
	public class ExplorationPartielle implements ExplorationConditionnelle {
		@Override
		public void recevoirFin() {
		}
		
		@Override
		public void recevoirSinon() {
		}
		
		@Override
		public void utiliser() {
		}
	}
	

	public class Element implements ExplorationConditionnelle {
		private final Algorithme pere;
		private final Condition condition;
		private final Algorithme vrai;
		private final Map<Integer, Integer> valeursCourantesVrai;
		private final Algorithme faux;
		private final Map<Integer, Integer> valeursCourantesFaux;

		public Element(Algorithme pere, Condition condition, Map<Integer, Integer> valeursCourantes) {
			this.pere = pere;
			this.condition = condition;
			this.vrai = new Algorithme();
			this.valeursCourantesVrai = new HashMap<>(valeursCourantes);
			this.faux = new Algorithme();
			this.valeursCourantesFaux = new HashMap<>(valeursCourantes);
		}
		
		public Map<Integer, Integer> getValeursCourantes() {
			Map<Integer, Integer> valeursCourantes = new HashMap<>();
			
			Utilitaire.Maps.combiner(valeursCourantes, valeursCourantesVrai, valeursCourantesFaux, ConstructeurValue::combiner);
			
			return valeursCourantes;
		}

		@Override
		public void recevoirSinon() {
			ConstructeurValue.this.algorithmeCourant = faux;
			ConstructeurValue.this.valeursCourantes = valeursCourantesFaux;
		}

		@Override
		public void recevoirFin() {
			pere.ajouterInstruction(new BlocConditionnel(condition, vrai, faux));
			ConstructeurValue.this.algorithmeCourant = pere;
			ConstructeurValue.this.valeursCourantes = getValeursCourantes();
		}

		@Override
		public void utiliser() {
			ConstructeurValue.this.algorithmeCourant = vrai;
			ConstructeurValue.this.valeursCourantes = valeursCourantesVrai;
		}
	}
	
}
