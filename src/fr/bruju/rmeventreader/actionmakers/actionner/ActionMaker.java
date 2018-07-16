package fr.bruju.rmeventreader.actionmakers.actionner;

import fr.bruju.rmeventreader.actionmakers.donnees.Pointeur;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.actionmakers.donnees.VariablePlage;

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

	/**
	 * Modifie une s�rie d'interrupteurs
	 * @param interrupteur La liste des interrupteurs � modifier
	 * @param value La nouvelle valeur
	 */
	public default void changeSwitch(VariablePlage interrupteur, boolean value) {
		interrupteur.getList().forEach(unique -> changeSwitch(unique, value));
	}

	/**
	 * Modifie l'interrupteur point� par la variable donn�e
	 * @param interrupteur La variable qui pointe vers l'interrupteur
	 * @param value La valeur
	 */
	public void changeSwitch(Pointeur interrupteur, boolean value);	
	

	/**
	 * Action d�clench�e quand un interrupteur est invers�
	 * @param interrupteur L'interrupteur � inverser
	 */
	public void revertSwitch(Variable interrupteur);
	
	/**
	 * Action d�clench�e pour inverser une s�rie d'interrupteurs
	 * @param interrupteurs La liste des interrupteurs � inverser
	 */
	public default void revertSwitch(VariablePlage interrupteurs) {
		interrupteurs.getList().forEach(unique -> revertSwitch(unique));
	}

	/**
	 * Action d�lench�e pour inverser un interrupteur point� par une variable
	 * @param interrupteur La variable qui pointe vers l'interrupteur
	 */
	public void revertSwitch(Pointeur interrupteur);
	
	/**
	 * Modifie une variable avec une valeur donn�e
	 */
	public void changeVariable(Variable variable, Operator operator, ValeurFixe rightValue);
	/**
	 * Modifie une variable avec une valeur al�atoire entre deux bornes
	 */
	public void changeVariable(Variable variable, Operator operator, ValeurAleatoire rightValue);
	/**
	 * Modifie une variable avec une autre variable
	 */
	public void changeVariable(Variable variable, Operator operator, Variable rightValue);
	/**
	 * Modifie une variable avec une valeur se trouvant dans une variable point�e
	 */
	public void changeVariable(Variable variable, Operator operator, Pointeur rightValue);

	/**
	 * Modifie une liste de variables avec une valeur donn�e
	 */
	public default void changeVariable(VariablePlage variables, Operator operator, ValeurFixe returnValue) {
		variables.getList().forEach(unique -> changeVariable(unique, operator, returnValue));
	}

	/**
	 * Modifie une liste de variables avec une valeur al�atoire entre deux bornes
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
	 * Modifie une liste de variables avec une valeur se trouvant dans une variable point�e
	 */
	public default void changeVariable(VariablePlage variables, Operator operator, Pointeur returnValue) {
		variables.getList().forEach(unique -> changeVariable(unique, operator, returnValue));
	}

	/**
	 * Modifie une variable point�e avec une valeur donn�e
	 */
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurFixe returnValue);
	/**
	 * Modifie une variable point�e avec une valeur al�atoire entre deux bornes
	 */
	public void changeVariable(Pointeur pointeur, Operator operator, ValeurAleatoire returnValue);
	/**
	 * Modifie une variable point�e avec une autre variable
	 */
	public void changeVariable(Pointeur pointeur, Operator operator, Variable returnValue);
	/**
	 * Modifie une variable point�e avec une valeur se trouvant dans une variable point�e
	 */
	public void changeVariable(Pointeur pointeur, Operator operator, Pointeur returnValue);
	
	
	/**
	 * Action d�clench�e lorsque les objets poss�d�s sont modifi�s
	 */
	public void modifyItems(ValeurFixe idItem, boolean add, ValeurFixe quantity);
	/**
	 * Action d�clench�e lorsque les objets poss�d�s sont modifi�s
	 */
	public void modifyItems(ValeurFixe idItem, boolean add, Variable quantity);
	/**
	 * Action d�clench�e lorsque les objets poss�d�s sont modifi�s
	 */
	public void modifyItems(Variable idItem, boolean add, ValeurFixe quantity);
	/**
	 * Action d�clench�e lorsque les objets poss�d�s sont modifi�s
	 */
	public void modifyItems(Variable idItem, boolean add, Variable quantity);
	
	
	/* ==========
	 * Conditions
	 * ========== */
	
	/**
	 * Condition sur un interrupteur
	 * @param number Le num�ro de l'interrupteur
	 * @param value La valeur que doit avoir l'interrupteur
	 * @return Vrai si la condition a �t� utile � l'interpr�tation
	 */
	public boolean condOnSwitch(int number, boolean value);
	
	/**
	 * Condition sur la possession d'un objet par un h�ros
	 * @param heroId LE num�ro du h�ros
	 * @param itemId L'objet voulu
	 * @return Vrai si la condition a �t� utile � l'interpr�tation
	 */
	public boolean condOnEquippedItem(int heroId, int itemId);
	
	/**
	 * Condition sur la valeur d'une variable
	 * @param leftOperandValue Le num�ro de la variable � comparer
	 * @param operatorValue L'op�rateur de comparaison
	 * @param returnValue La valeur avec laquelle comparer
	 * @return Vrai si la condition a �t� utile � l'interpr�tation
	 */
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, Variable returnValue);
	/**
	 * Condition sur la valeur d'une variable
	 * @param leftOperandValue Le num�ro de la variable � comparer
	 * @param operatorValue L'op�rateur de comparaison
	 * @param returnValue La variable avec laquelle comparer
	 * @return Vrai si la condition a �t� utile � l'interpr�tation
	 */
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue);
	
	/**
	 * Condition sur un objet poss�d�
	 * @param itemId L'objet poss�d�
	 * @return Vrai si la condition a �t� utile � l'interpr�tation
	 */
	public boolean condOnOwnedItem(int itemId);
	
	/**
	 * Condition sur la pr�sence d'un membre du groupe
	 * @param memberId Le num�ro du membre du groupe
	 * @return Vrai si la condition a �t� utile � l'interpr�tation
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
