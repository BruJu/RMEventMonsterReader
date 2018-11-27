package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Stack;

import fr.bruju.rmeventreader.implementation.detectiondeformules.EtatInitial;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffichage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurReecrivainDExpression;
import fr.bruju.util.MapsUtils;

/**
 * Un constructeur d'algorithme qui retient les instructions qu'il lit afin de déduire des valeurs pour les variables.
 * L'objectif étant de remplacer "x = 3; y = x;" par "x = 3; y = 3;" afin de simplifier l'algorithme ainsi constitué.
 * <br><br>Cette classe implémente VisiteurReecrivainDExpression : elle peut visiter des expressions afin de
 * substituer les variables par des valeurs qu'elle connait.
 */
public class ConstructeurValue extends VisiteurReecrivainDExpression {
	/** Algorithme en cours de construction */
	private Algorithme algorithmeCourant = new Algorithme();
	/** Pile d'exploration conditionnelle (ie instructions conditionnelles en cours de construction) */
	private Stack<ExplorationConditionnelle> pile = new Stack<>();
	/** Valeurs actuellement connues pour les variables */
	private Map<Integer, Integer> valeursCourantes;

	/**
	 * Construit un constructeur d'algorithme valué en utilisant l'état initial présent dans un fichier de
	 * onfiguration défini par la classe EtatInitial.
	 */
	public ConstructeurValue() {
		EtatInitial etatInitial = EtatInitial.getEtatInitial();
		valeursCourantes = new HashMap<>();
		etatInitial.forEach(valeursCourantes::put);
	}

	/**
	 * Constuit un constructeur d'algorithme valué en utilisant une table d'association id de variable - constante de
	 * base.
	 * @param valeursInitiales Une table associant id de variable - valeur initiale. La table de hashage est recopiée,
	 *                         cette classe ne modifie donc pas le contenu de valeursInitiales.
	 */
	public ConstructeurValue(Map<Integer, Integer> valeursInitiales) {
		this.valeursCourantes = new HashMap<>(valeursInitiales);
	}

	/**
	 * Renvoie l'algorithme en cours de construction. Lance une exception sur une condition est en cours de
	 * construction.
	 * <br>Si le constructeur valué continue à être utilisé suite à une utilisation de get(), l'algorithme retourné sera
	 * modifié. En d'autres termes, l'algorithme renvoyé par get() n'est pas une copie mais l'objet utilisé pour la
	 * construction.
	 * @return L'algorithme construit
	 */
	public Algorithme get() {
		if (!pile.isEmpty()) {
			throw new RuntimeException("Exécution non terminée " + pile.size());
		}
		
		return algorithmeCourant;
	}

	/**
	 * Ajoute l'instruction d'affectation donnée à l'algorithme en cours de construction. L'instruction peut être
	 * reecrite selon les connaissances actuelles sur l'état des variables.
	 * @param affectation L'instruction d'affectation à ajouter.
	 */
	public void ajouter(InstructionAffectation affectation) {
		affectation = reecrire(affectation);
		algorithmeCourant.ajouterInstruction(affectation);
	}

	/**
	 * Reecrit l'instruction en prenant en compte des valeurs connues pour les variables. Rempli éventuellement la table
	 * des valeurs courantes si l'évaluation de l'expression est possible.
	 */
	private InstructionAffectation reecrire(InstructionAffectation affectation) {
		Expression nouvelleExpression = explorer(affectation.expression);
		
		if (nouvelleExpression != affectation.expression) {
			affectation = new InstructionAffectation(affectation.variableAssignee, nouvelleExpression);
		}
		
		Integer evaluation = nouvelleExpression.evaluer();
		valeursCourantes.put(affectation.variableAssignee.idVariable, evaluation);
		
		return affectation;
	}

	/**
	 * Ajoute l'instruction d'affichage. Aucun traîtement n'est fait.
	 * @param affichage L'instruction d'affichage.
	 */
	public void ajouter(InstructionAffichage affichage) {
		algorithmeCourant.ajouterInstruction(affichage);
	}

	/**
	 * Permet de déclarer le début d'une condition. On donne en paramètre la condition. La fonction renvoie un résultat
	 * selon le désir de la classe de recevoir ou non les instructions contenues dans certaines branches.
	 * <br>Si la fonction déclare ne vouloir recevoir qu'une des deux branches, l'objet s'attend à ne recevoir que
	 * des ajout d'instructions, avec un appel à conditionFinie() lorsque le branchement conditionnel s'arrête.
	 * <br>Si la fonction déclare vouloir recevoir les deux branches, on s'attend à recevoir la suite d'instructions
	 * dans la branche si, puis un appel à conditionSinon, la suite d'instructions dans la branche sinon puis un
	 * appel à conditionFin. Les appels à conditionSinon et la suite d'instructions dans la branche sinon peuvent être
	 * ignorés si il n'y a pas d'instructions dans le sinon.
	 * <br><br>Le code de retour de cette fonction est identique à ce qu'attend la structure d'Exécuteur d'Instructions
	 * dans RMDechiffreur, d'où le choix de ne pas utiliser d'énumération (le code peut être simplement renvoyé par la
	 * fonction de traitement).
	 * @param condition La condition déclenchant le branchement conditionnel.
	 * @return 0 = explorer tout, 1 = explorer vrai, 2 = explorer faux
	 */
	public int commencerCondition(Condition condition) {
		if (condition instanceof ConditionVariable) {
			ConditionVariable cv = (ConditionVariable) condition;

			Expression gauche = explorer(cv.gauche);
			Expression droite = explorer(cv.droite);
			
			if (gauche != cv.gauche || droite != cv.droite) {
				cv = new ConditionVariable(gauche, cv.comparateur, droite);
				condition = cv;
			}
			
			Boolean test = cv.evaluer();
			
			if (test != null) {
				pile.push(new ExplorationPartielle());
				pile.peek().utiliser();
				return test ? 1 : 2;
			}
		}
		
		ExplorationTotale explorationTotale = new ExplorationTotale(condition);
		pile.push(explorationTotale);
		explorationTotale.utiliser();
		
		return 0;
	}

	/**
	 * Met fin à l'exploration de la branche si du branchement conditionnel pour commencer à explorer la branche sinon.
	 * Peut lever une exception de temps de course si une exploration partielle du branchement conditionnel a été
	 * demandé par l'objet.
	 */
	public void conditionElse() {
		pile.peek().recevoirSinon();
	}

	/**
	 * Met fin à l'exploration du branchement conditionnel actuel.
	 */
	public void conditionFinie() {
		pile.pop().recevoirFin();
	}

	/**
	 * Si a = b, renvoie a. Sinon renvoie null.
	 * @param a Le premier nombre
	 * @param b Le second nombre
	 * @return Objects.equals(a, b) ? a : null
	 */
	private static Integer combiner(Integer a, Integer b) {
		return Objects.equals(a, b) ? a : null;
	}


	/**
	 * Objet permettant de gérer les imbrications de conditions. Les objets implémentant cette interface sont appelés
	 * pour mettre en mémoire l'état de la mémoire avant une condition, et d'unifier les valeurs de la branche si
	 * et de la branche sinon.
	 */
	public static interface ExplorationConditionnelle {
		/** Commence l'exploration du branchement conditionnel */
		public void utiliser();
		/** Commence l'exploration de la branche sinon */
		public void recevoirSinon();
		/** Met fin à l'exploration du branchement conditionnel */
		public void recevoirFin();
	}

	/**
	 * Un explorateur qui ne fait rien, à part provoquer une erreur si il reçoit un sinon.
	 * <br>Son but est de continuer à compléter l'algorithme si seule une branche est explorée (on enlève le
	 * branchement conditionnel).
	 */
	public class ExplorationPartielle implements ExplorationConditionnelle {
		@Override
		public void recevoirFin() {
		}
		
		@Override
		public void recevoirSinon() {
			throw new UnsupportedOperationException("Partiel a reçu un sinon");
		}
		
		@Override
		public void utiliser() {
		}
	}

	/**
	 * Un explorateur qui traite les branches si et sinon et construit l'instruction conditionnelle.
	 */
	public class ExplorationTotale implements ExplorationConditionnelle {
		// L'idée étant que chaque branche a sa propre version de la valeur actuelle de chaque variable.
		// A la fin du branchement, on ne garde que les affectations dont les deux branches ont la même valeur.
		// Cela permet de garder les informations déjà connues (si une variable n'est pas modifiée, ce procédéne perd
		// pas la connaissance sur l'état de cette variable) entre autres.

		/** Algorithme possédant l'instruction conditionnelle en cours de construction */
		private final Algorithme pere;
		/** La condition générant l'instruction conditionnelle */
		private final Condition condition;
		/** Algorithme dans la branche si */
		private final Algorithme vrai;
		/** Valeurs courantes de la branche si */
		private final Map<Integer, Integer> valeursCourantesVrai;
		/** Algorithme dans la branche sinon */
		private final Algorithme faux;
		/** Valeurs courantes de la branche sinon */
		private final Map<Integer, Integer> valeursCourantesFaux;

		/**
		 * Construit un objet permettant de construire une instruction conditionnelle pouvant posséder une branche si
		 * et une branche sinon.
		 * @param condition La condition générant le branchement
		 */
		public ExplorationTotale(Condition condition) {
			this.pere = algorithmeCourant;
			this.condition = condition;
			this.vrai = new Algorithme();
			this.valeursCourantesVrai = new HashMap<>(ConstructeurValue.this.valeursCourantes);
			this.faux = new Algorithme();
			this.valeursCourantesFaux = new HashMap<>(ConstructeurValue.this.valeursCourantes);
		}

		@Override
		public void utiliser() {
			// Remplace l'état de la mémoire par l'état de la branche si
			ConstructeurValue.this.algorithmeCourant = vrai;
			ConstructeurValue.this.valeursCourantes = valeursCourantesVrai;
		}

		@Override
		public void recevoirSinon() {
			// Remplace l'état de la mémoire par l'état de la branche sinon
			ConstructeurValue.this.algorithmeCourant = faux;
			ConstructeurValue.this.valeursCourantes = valeursCourantesFaux;
		}

		@Override
		public void recevoirFin() {
			// Combine les branches si et sinon
			if (!(vrai.estVide() && faux.estVide())) {
				pere.ajouterInstruction(new BlocConditionnel(condition, vrai, faux));
			}

			ConstructeurValue.this.algorithmeCourant = pere;
			// Crée le nouvel état des variables à partir de l'état à la fin des branches si et sinon. Concrètement, si
			// la sortie, une variable a le même état dans les deux branches, on garde sa valeur. Sinon on déclare
			// qu'elle * n'a plus de valeur connue.
			ConstructeurValue.this.valeursCourantes = MapsUtils.combiner(new HashMap<>(),
					valeursCourantesVrai, valeursCourantesFaux, ConstructeurValue::combiner);
		}
	}

	/* ============================================
	 * SUBSTITUTION DE VALEURS DANS LES EXPRESSIONS
	 * ============================================ */

	@Override
	public Expression explorer(ExprVariable composant) {
		Integer valeur = valeursCourantes.get(composant.idVariable);
		return valeur == null ? composant : new Constante(valeur);
	}
}
