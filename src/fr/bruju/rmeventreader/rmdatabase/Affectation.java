package fr.bruju.rmeventreader.rmdatabase;


/**
 * Représente un état de la partie en cours.
 * @author Bruju
 *
 */
public interface Affectation {

	/**
	 * Permet de savoir si un objet est possédé
	 * @param idObjet L'ID de l'objet
	 * @return Vrai si l'objet est possédé
	 */
	default boolean hasItem(int idObjet) {
		Integer quantitePossedee = getQuantitePossedee(idObjet);
		
		return quantitePossedee != null && quantitePossedee != 0;
	}
	
	/**
	 * Donne le nombre d'objets possédés
	 * @param idObjet L'objet dont on veut connaître la quantité possédée
	 * @return La quantité d'idObjet possédés
	 */
	Integer getQuantitePossedee(int idObjet);

	/**
	 * Renvoie la valeur de la variable idVariable
	 * @param idVariable La variable
	 * @return La valeur de idVariable
	 */
	Integer getVariable(int idVariable);

	/**
	 * Renvoie l'état de l'interrupteur donné
	 * @param idInterrupteur L'interrupteur dont on veut connaître l'état
	 * @return L'état de l'interrupteur
	 */
	Boolean getInterrupteur(int idInterrupteur);

	/**
	 * Permet de savoir si un héros possède un objet donné
	 * @param idHeros Le numréo du héros
	 * @param idObjet Le numéro de l'objet
	 * @return Vrai si le héros a l'objet équipé
	 */
	Boolean herosPossedeEquipement(int idHeros, int idObjet);

}