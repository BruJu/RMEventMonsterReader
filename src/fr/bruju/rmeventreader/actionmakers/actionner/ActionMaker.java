package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.donnees.ReturnValue;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.rework.VariablePlage;

/**
 * Cette classe est appel�e lorsqu'un �v�nement est lu.
 * 
 * Cette classe est appell�e de mani�re s�quentielle pour chaque instruction.
 * L'impl�mentation doit donc prendre en compte l'ordre d'�criture des instructions.
 */
public interface ActionMaker {

	/* ==============
	 * Non impl�ment�
     * ============== */
	/**
	 * Action d�clench�e lorsque l'analyseur lit une ligne dont il n'a pas impl�ment� la fonctionnalit�
	 * @param str Une cha�ne avec un nom de la fonctionnalit� non impl�ment�e
	 */
	public void notImplementedFeature(String str);
	
	/* ====================
	 * Changement de valeur
	 * ==================== */
	
	/**
	 * Action d�clench�e lorsqu'un switch doit �re modifi�
	 * @param interrupteur L'interrupteur � modifier
	 * @param value La nouvelle valeur
	 */
	public void changeSwitch(Variable interrupteur, boolean value);

	public default void changeSwitch(VariablePlage interrupteur, boolean value) {
		interrupteur.getList().forEach(unique -> changeSwitch(unique, value));
	}
	
	public void changeSwitch(Pointeur interrupteur, boolean value);
	

	/**
	 * Action d�clench�e quand un interrupteur est invers�
	 * @param interrupteur L'interrupteur � inverser
	 */
	public void revertSwitch(Variable interrupteur);
	
	public default void revertSwitch(VariablePlage interrupteurs) {
		interrupteurs.getList().forEach(unique -> revertSwitch(unique));
	}

	public void revertSwitch(Pointeur interrupteur);
	
	/**
	 * Action d�clench�e lors d'un changement de valeur d'une variable
	 * @param variable La variable
	 * @param operator L'op�rateur appliqu�
	 * @param returnValue La valeur mise
	 */
	public void changeVariable(Variable variable, Operator operator, ReturnValue returnValue);

	public default void changeVariable(VariablePlage variables, Operator operator, ReturnValue returnValue) {
		variables.getList().forEach(unique -> changeVariable(unique, operator, returnValue));
	}

	public void changeVariable(Pointeur pointeur, Operator operator, ReturnValue returnValue);
	
	
	/**
	 * Action d�clench�e lorsque les objets poss�d�s sont modifi�s
	 * @param idItem L'objet � modifier
	 * @param add Vrai si il faut en ajouter, faux si il faut en retirer
	 * @param quantity La quantit� d'objets
	 */
	public void modifyItems(ReturnValue idItem, boolean add, ReturnValue quantity);
	
	
	/* ==========
	 * Conditions
	 * ========== */
	
	/**
	 * Condition sur un interrupteur
	 * @param number Le num�ro de l'interrupteur
	 * @param value La valeur que doit avoir l'interrupteur
	 */
	public void condOnSwitch(int number, boolean value);
	
	/**
	 * Condition sur la possession d'un objet par un h�ros
	 * @param heroId LE num�ro du h�ros
	 * @param itemId L'objet voulu
	 */
	public void condOnEquippedItem(int heroId, int itemId);
	
	/**
	 * Condition sur la valeur d'une variable
	 * @param leftOperandValue Le num�ro de la variable � comparer
	 * @param operatorValue L'op�rateur de comparaison
	 * @param returnValue La valeur avec laquelle comparer (une variable ou un nombre)
	 */
	public void condOnVariable(int leftOperandValue, Operator operatorValue, ReturnValue returnValue);
	
	/**
	 * Condition sur un objet poss�d�
	 * @param itemId L'objet poss�d�
	 */
	public void condOnOwnedItem(int itemId);
	
	/**
	 * Condition sur la pr�sence d'un membre du groupe
	 * @param memberId Le num�ro du membre du groupe
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
	 * Affiche une image avec l'id donn� et dont le nom est pictureName
	 * @param id L'id de l'image
	 * @param pictureName Le nom de l'image
	 */
	public void showPicture(int id, String pictureName);

	
	/**
	 * Mise en place d'un label
	 * @param labelNumber Le num�ro pos�
	 */
	public void label(int labelNumber);
	
	/**
	 * Saut vers un label
	 * @param labelNumber Le label vers lequel aller
	 */
	public void jumpToLabel(int labelNumber);
	
	
	/**
	 * Ex�cution du code d'un autre �v�nement sur la carte
	 * @param eventNumber Num�ro de l'�v�nement
	 * @param eventPage Page de l'�v�nement
	 */
	public void callMapEvent(int eventNumber, int eventPage);
}
