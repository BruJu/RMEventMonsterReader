package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.donnees.LeftValue;
import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.RightValue;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.VariablePlage;

/**
 * Cette classe est appelée lorsqu'un évènement est lu.
 * <p>
 * Cette classe est appellée de manière séquentielle pour chaque instruction. L'implémentation doit donc prendre en
 * compte l'ordre d'écriture des instructions.
 * <p>
 * Les méthodes commencant par un _ ne devraient pas être redéfinies
 */
public interface ActionMaker {	
	/* ==============
	 * Non implémenté
	 * ============== */
	/**
	 * Action déclenchée lorsque l'analyseur lit une ligne dont il n'a pas implémenté la fonctionnalité
	 * 
	 * @param str Une chaîne avec un nom de la fonctionnalité non implémentée
	 */
	public void notImplementedFeature(String str);

	/* ====================
	 * Changement de valeur
	 * ==================== */

	/**
	 * Action déclenchée lorsqu'un switch doit êre modifié
	 * 
	 * @param interrupteur L'interrupteur à modifier
	 * @param value La nouvelle valeur
	 */
	public void changeSwitch(Variable interrupteur, boolean value);

	/**
	 * Modifie une série d'interrupteurs
	 * 
	 * @param interrupteur La liste des interrupteurs à modifier
	 * @param value La nouvelle valeur
	 */
	public default void changeSwitch(VariablePlage interrupteur, boolean value) {
		interrupteur.getList().forEach(unique -> changeSwitch(unique, value));
	}

	/**
	 * Modifie l'interrupteur pointé par la variable donnée
	 * 
	 * @param interrupteur La variable qui pointe vers l'interrupteur
	 * @param value La valeur
	 */
	public void changeSwitch(Pointeur interrupteur, boolean value);

	/**
	 * Action déclenchée quand un interrupteur est inversé
	 * 
	 * @param interrupteur L'interrupteur à inverser
	 */
	public void revertSwitch(Variable interrupteur);

	/**
	 * Action déclenchée pour inverser une série d'interrupteurs
	 * 
	 * @param interrupteurs La liste des interrupteurs à inverser
	 */
	public default void revertSwitch(VariablePlage interrupteurs) {
		interrupteurs.getList().forEach(unique -> revertSwitch(unique));
	}

	/**
	 * Action délenchée pour inverser un interrupteur pointé par une variable
	 * 
	 * @param interrupteur La variable qui pointe vers l'interrupteur
	 */
	public void revertSwitch(Pointeur interrupteur);

	/**
	 * Modifie une variable avec une valeur donnée
	 */
	public void changeVariable(Variable variable, Operator operator, ValeurFixe rightValue);

	/**
	 * Modifie une variable avec une valeur aléatoire entre deux bornes
	 */
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire rightValue);

	/**
	 * Modifie une variable avec une autre variable
	 */
	public void changeVariable(Variable variable, Operator operator, Variable rightValue);

	/**
	 * Modifie une variable avec une valeur se trouvant dans une variable pointée
	 */
	public void changeVariable(Variable variable, Operator operator, Pointeur rightValue);

	/**
	 * Modifie une liste de variables avec une valeur donnée
	 */
	public default void changeVariable(VariablePlage variables, Operator operator, ValeurFixe returnValue) {
		variables.getList().forEach(unique -> changeVariable(unique, operator, returnValue));
	}

	/**
	 * Modifie une liste de variables avec une valeur aléatoire entre deux bornes
	 */
	public default void changeVariable(VariablePlage variables, Operator operator, ValeurAleatoire returnValue) {
		variables.getList().forEach(unique -> changeVariable(unique, operator, returnValue));
	}

	/**
	 * Modifie une liste de variables avec une autre variable
	 */
	public default void changeVariable(VariablePlage variables, Operator operator, Variable returnValue) {
		variables.getList().forEach(unique -> changeVariable(unique, operator, returnValue));
	}

	/**
	 * Modifie une liste de variables avec une valeur se trouvant dans une variable pointée
	 */
	public default void changeVariable(VariablePlage variables, Operator operator, Pointeur returnValue) {
		variables.getList().forEach(unique -> changeVariable(unique, operator, returnValue));
	}

	/**
	 * Modifie une variable pointée avec une valeur donnée
	 */
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurFixe returnValue);

	/**
	 * Modifie une variable pointée avec une valeur aléatoire entre deux bornes
	 */
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurAleatoire returnValue);

	/**
	 * Modifie une variable pointée avec une autre variable
	 */
	public void changeVariable(Pointeur pointeur, Operator operator, Variable returnValue);

	/**
	 * Modifie une variable pointée avec une valeur se trouvant dans une variable pointée
	 */
	public void changeVariable(Pointeur pointeur, Operator operator, Pointeur returnValue);

	/**
	 * Action déclenchée lorsque les objets possédés sont modifiés
	 */
	public void modifyItems(ValeurFixe idItem, boolean add, ValeurFixe quantity);

	/**
	 * Action déclenchée lorsque les objets possédés sont modifiés
	 */
	public void modifyItems(ValeurFixe idItem, boolean add, Variable quantity);

	/**
	 * Action déclenchée lorsque les objets possédés sont modifiés
	 */
	public void modifyItems(Variable idItem, boolean add, ValeurFixe quantity);

	/**
	 * Action déclenchée lorsque les objets possédés sont modifiés
	 */
	public void modifyItems(Variable idItem, boolean add, Variable quantity);

	/* ==========
	 * Conditions
	 * ========== */

	/**
	 * Condition sur un interrupteur
	 * 
	 * @param number Le numéro de l'interrupteur
	 * @param value La valeur que doit avoir l'interrupteur
	 * @return Vrai si la condition a été utile à l'interprétation
	 */
	public boolean condOnSwitch(int number, boolean value);

	/**
	 * Condition sur la possession d'un objet par un héros
	 * 
	 * @param heroId LE numéro du héros
	 * @param itemId L'objet voulu
	 * @return Vrai si la condition a été utile à l'interprétation
	 */
	public boolean condOnEquippedItem(int heroId, int itemId);

	/**
	 * Condition sur la valeur d'une variable
	 * 
	 * @param leftOperandValue Le numéro de la variable à comparer
	 * @param operatorValue L'opérateur de comparaison
	 * @param returnValue La valeur avec laquelle comparer
	 * @return Vrai si la condition a été utile à l'interprétation
	 */
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue);

	/**
	 * Condition sur la valeur d'une variable
	 * 
	 * @param leftOperandValue Le numéro de la variable à comparer
	 * @param operatorValue L'opérateur de comparaison
	 * @param returnValue La variable avec laquelle comparer
	 * @return Vrai si la condition a été utile à l'interprétation
	 */
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue);

	/**
	 * Condition sur un objet possédé
	 * 
	 * @param itemId L'objet possédé
	 * @return Vrai si la condition a été utile à l'interprétation
	 */
	public boolean condOnOwnedItem(int itemId);

	/**
	 * Condition sur la présence d'un membre du groupe
	 * 
	 * @param memberId Le numéro du membre du groupe
	 * @return Vrai si la condition a été utile à l'interprétation
	 */
	public boolean condTeamMember(int memberId);

	public boolean condOnOwnedSpell(int heroId, int spellId);

	/**
	 * Passage au sinon de la condition
	 */
	public void condElse();

	/**
	 * Fin d'une condition
	 */
	public void condEnd();

	/* ======
	 * Divers
	 * ====== */

	/**
	 * Affiche une image avec l'id donné et dont le nom est pictureName
	 * 
	 * @param id L'id de l'image
	 * @param pictureName Le nom de l'image
	 */
	public void showPicture(int id, String pictureName);

	/**
	 * Mise en place d'un label
	 * 
	 * @param labelNumber Le numéro posé
	 */
	public void label(int labelNumber);

	/**
	 * Saut vers un label
	 * 
	 * @param labelNumber Le label vers lequel aller
	 */
	public void jumpToLabel(int labelNumber);

	/**
	 * Exécution du code d'un autre évènement sur la carte.
	 * <p>
	 * Si le numéro de l'evènement est 0, l'évènement se rappelle lui-même
	 * 
	 * @param eventNumber Numéro de l'évènement
	 * @param eventPage Page de l'évènement
	 */
	public void callMapEvent(int eventNumber, int eventPage);
	
	
	/**
	 * Exécution du code d'un évènement commun
	 * 
	 * @param eventNumber Numéro de l'évènement commun
	 */
	public void callCommonEvent(int eventNumber);

	/**
	 * Commentaire lu
	 * 
	 * @param str Le commentaire
	 */
	public void getComment(String str);

	

	/* =================================================================================================================
	 *  - Pseudo visiteur - Pseudo visiteur - Pseudo visiteur - Pseudo visiteur - Pseudo visiteur - Pseudo visiteur -
	 * ============================================================================================================== */
	
	public static class AMException extends RuntimeException {
		private static final long serialVersionUID = 7917269665749561827L;

		public AMException(String message) {
			super("AMException classe inconnue : " + message);
		}
	}
	
	public default void _changeSwitch(LeftValue interrupteur, boolean value) {
		if (interrupteur instanceof Variable) {
			changeSwitch((Variable) interrupteur, value);
		} else if (interrupteur instanceof VariablePlage) {
			changeSwitch((VariablePlage) interrupteur, value);
		} else if (interrupteur instanceof Pointeur) {
			changeSwitch((Pointeur) interrupteur, value);
		} else {
			throw new AMException(interrupteur.getClass().toString());
		}
	}

	public default void _revertSwitch(LeftValue interrupteur) {
		if (interrupteur instanceof Variable) {
			revertSwitch((Variable) interrupteur);
		} else if (interrupteur instanceof VariablePlage) {
			revertSwitch((VariablePlage) interrupteur);
		} else if (interrupteur instanceof Pointeur) {
			revertSwitch((Pointeur) interrupteur);
		} else {
			throw new AMException(interrupteur.getClass().toString());
		}
	}
	
	public default void _changeVariable(LeftValue variable, Operator operator, RightValue rightValue) {
		/* 
		 * Il s'agit d'un choix de design de ne pas avoir de fonctions prenant comme argument LeftValue ou RightValue
		 * afin de simplifier l'implémentation de ActionMaker (éviter de tester le type d'argument reçu en début
		 * de fonction).
		 */
		
		if (variable instanceof Variable) {
			if (rightValue instanceof ValeurFixe) {
				changeVariable((Variable) variable, operator, (ValeurFixe) rightValue);
			} else if (rightValue instanceof ValeurAleatoire) {
				changeVariable((Variable) variable, operator, (ValeurAleatoire) rightValue);
			} else if (rightValue instanceof Variable) {
				changeVariable((Variable) variable, operator, (Variable) rightValue);
			} else if (rightValue instanceof Pointeur) {
				changeVariable((Variable) variable, operator, (Pointeur) rightValue);
			} else {
				throw new AMException(rightValue.getClass().toString());
			}
		} else if (variable instanceof VariablePlage) {
			if (rightValue instanceof ValeurFixe) {
				changeVariable((VariablePlage) variable, operator, (ValeurFixe) rightValue);
			} else if (rightValue instanceof ValeurAleatoire) {
				changeVariable((VariablePlage) variable, operator, (ValeurAleatoire) rightValue);
			} else if (rightValue instanceof Variable) {
				changeVariable((VariablePlage) variable, operator, (Variable) rightValue);
			} else if (rightValue instanceof Pointeur) {
				changeVariable((VariablePlage) variable, operator, (Pointeur) rightValue);
			} else {
				throw new AMException(rightValue.getClass().toString());
			}
		} else if (variable instanceof Pointeur) {
			if (rightValue instanceof ValeurFixe) {
				changeVariable((Pointeur) variable, operator, (ValeurFixe) rightValue);
			} else if (rightValue instanceof ValeurAleatoire) {
				changeVariable((Pointeur) variable, operator, (ValeurAleatoire) rightValue);
			} else if (rightValue instanceof Variable) {
				changeVariable((Pointeur) variable, operator, (Variable) rightValue);
			} else if (rightValue instanceof Pointeur) {
				changeVariable((Pointeur) variable, operator, (Pointeur) rightValue);
			} else {
				throw new AMException(rightValue.getClass().toString());
			}
		} else {
			throw new AMException(variable.getClass().toString());
		}
	}
}
