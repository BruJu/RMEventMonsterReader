package fr.bruju.rmeventreader.implementation.equipementchecker;

import java.util.TreeMap;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

/**
 * Exécuteur d'actions qui lit les variables modifiées par des équipements
 * 
 * @author Bruju
 *
 */
public class EquipementChecker implements ActionMakerDefalse {
	/** Numéro du héros */
	private int idHeros;
	
	/** Associations numéro d'objet - variables modifiées */
	private TreeMap<Integer, EquipementData> equipements = new TreeMap<>();
	
	/** Equipement en cours de lecture */
	private int idEquipementEnCours = -1;
	/** Données en cours de construction */
	private EquipementData equipementEnCours = null;
	
	/**
	 * Construit un EquipementChecker pour le héros donné
	 * @param idHeros Le numéro du héros
	 */
	public EquipementChecker(int idHeros) {
		this.idHeros = idHeros;
	}

	/**
	 * Donne une liste d'association objet - variables modifiées 
	 */
	public TreeMap<Integer, EquipementData> getEquipements() {
		return equipements;
	}

	/* ================================
	 * MONTEUR D'ENSEMBLE D'EQUIPEMENTS
	 * ================================ */
	
	/**
	 * Détermine que l'objet en cours de lecture est trop complexe pour être stockée dans le système très simple mis en
	 * place.
	 */
	private void tropComplique() {
		if (equipementEnCours == null)
			return;
		
		equipementEnCours.setComplexe();
		decharger();
	}

	/**
	 * Enregisre pour l'objet en cours de lecture les statistiques modifiées
	 */
	private void decharger() {
		if (equipementEnCours == null)
			return;
		
		equipements.put(idEquipementEnCours, equipementEnCours);
		equipementEnCours = null;
	}
	
	/* ============
	 * ACTION MAKER
	 * ============ */
	
	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		if (equipementEnCours == null)
			return;
		
		if (operator == Operator.PLUS) {
			equipementEnCours.ajouterModification(variable.idVariable, returnValue.valeur);
		} else if (operator == Operator.MINUS) {
			equipementEnCours.ajouterModification(variable.idVariable, -returnValue.valeur);
		} else {
			tropComplique();
		}
	}

	@Override
	public boolean condOnSwitch(int number, boolean value) {
		if (equipementEnCours == null) {
			return true;
		}
		
		tropComplique();
		return false;
	}

	@Override
	public boolean condOnVariable(int var, Operator operatorValue, ValeurFixe returnValue) {
		if (equipementEnCours != null) {
			tropComplique();
		}
		
		if ((var == 828 || var == 483 || var == 484) && operatorValue.equals(Operator.IDENTIQUE)) {
			idEquipementEnCours = returnValue.valeur;
			equipementEnCours = new EquipementData();
			return true;
		} else {
			tropComplique();
			return false;
		}
	}
	
	@Override
	public boolean condOnEquippedItem(int heroId, int itemId) {
		if (equipementEnCours != null) {
			tropComplique();
		}
		
		if (heroId != this.idHeros) {
			System.out.println("heroId " + heroId + " rencontré");
			return false;
		} else {
			idEquipementEnCours = itemId;
			equipementEnCours = new EquipementData();
			return true;
		}
	}

	@Override
	public void condElse() {
		tropComplique();
	}

	@Override
	public void condEnd() {
		decharger();
	}
}
