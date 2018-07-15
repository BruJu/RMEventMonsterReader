package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.donnees.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.VariablePlage;

/**
 * Cette classe est appelée lorsqu'un évènement est lu.
 * 
 * Cette classe est appellée de manière séquentielle pour chaque instruction.
 * L'implémentation doit donc prendre en compte l'ordre d'écriture des instructions.
 */
public interface ActionMaker {

	/* ==============
	 * Non implémenté
     * ============== */
	/**
	 * Action déclenchée lorsque l'analyseur lit une ligne dont il n'a pas implémenté la fonctionnalité
	 * @param str Une chaîne avec un nom de la fonctionnalité non implémentée
	 */
	public void notImplementedFeature(String str);
	
	/* ====================
	 * Changement de valeur
	 * ==================== */
	
	/**
	 * Action déclenchée lorsqu'un switch doit êre modifié
	 * @param interrupteur L'interrupteur à modifier
	 * @param value La nouvelle valeur
	 */
	public void changeSwitch(Variable interrupteur, boolean value);

	public default void changeSwitch(VariablePlage interrupteur, boolean value) {
		interrupteur.getList().forEach(unique -> changeSwitch(unique, value));
	}
	
	public void changeSwitch(Pointeur interrupteur, boolean value);
	

	/**
	 * Action déclenchée quand un interrupteur est inversé
	 * @param interrupteur L'interrupteur à inverser
	 */
	public void revertSwitch(Variable interrupteur);
	
	public default void revertSwitch(VariablePlage interrupteurs) {
		interrupteurs.getList().forEach(unique -> revertSwitch(unique));
	}

	public void revertSwitch(Pointeur interrupteur);
	
	/**
	 * Action déclenchée lors d'un changement de valeur d'une variable
	 * @param variable La variable
	 * @param operator L'opérateur appliqué
	 * @param returnValue La valeur mise
	 */
	public void changeVariable(Variable variable, Operator operator, ReturnValue returnValue);

	public default void changeVariable(VariablePlage variables, Operator operator, ReturnValue returnValue) {
		variables.getList().forEach(unique -> changeVariable(unique, operator, returnValue));
	}

	public void changeVariable(Pointeur pointeur, Operator operator, ReturnValue returnValue);
	
	
	/**
	 * Action déclenchée lorsque les objets possédés sont modifiés
	 * @param idItem L'objet à modifier
	 * @param add Vrai si il faut en ajouter, faux si il faut en retirer
	 * @param quantity La quantité d'objets
	 */
	public void modifyItems(ReturnValue idItem, boolean add, ReturnValue quantity);
	
	
	/* ==========
	 * Conditions
	 * ========== */
	
	/**
	 * Condition sur un interrupteur
	 * @param number Le numéro de l'interrupteur
	 * @param value La valeur que doit avoir l'interrupteur
	 */
	public void condOnSwitch(int number, boolean value);
	
	/**
	 * Condition sur la possession d'un objet par un héros
	 * @param heroId LE numéro du héros
	 * @param itemId L'objet voulu
	 */
	public void condOnEquippedItem(int heroId, int itemId);
	
	/**
	 * Condition sur la valeur d'une variable
	 * @param leftOperandValue Le numéro de la variable à comparer
	 * @param operatorValue L'opérateur de comparaison
	 * @param returnValue La valeur avec laquelle comparer (une variable ou un nombre)
	 */
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue);
	
	/**
	 * Condition sur un objet possédé
	 * @param itemId L'objet possédé
	 */
	public void condOnOwnedItem(int itemId);
	
	/**
	 * Condition sur la présence d'un membre du groupe
	 * @param memberId Le numéro du membre du groupe
	 */
	public void condTeamMember(int memberId);


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
	 * @param id L'id de l'image
	 * @param pictureName Le nom de l'image
	 */
	public void showPicture(int id, String pictureName);

	
	/**
	 * Mise en place d'un label
	 * @param labelNumber Le numéro posé
	 */
	public void label(int labelNumber);
	
	/**
	 * Saut vers un label
	 * @param labelNumber Le label vers lequel aller
	 */
	public void jumpToLabel(int labelNumber);
	
	
	/**
	 * Exécution du code d'un autre évènement sur la carte
	 * @param eventNumber Numéro de l'évènement
	 * @param eventPage Page de l'évènement
	 */
	public void callMapEvent(int eventNumber, int eventPage);
}
