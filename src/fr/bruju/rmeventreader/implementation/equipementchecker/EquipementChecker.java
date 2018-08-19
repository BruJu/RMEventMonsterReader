package fr.bruju.rmeventreader.implementation.equipementchecker;

import java.util.TreeMap;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;

public class EquipementChecker implements ActionMakerDefalse {
	private int idHeros;
	private TreeMap<Integer, EquipementData> equipements = new TreeMap<>();
	
	int idEquipementEnCours = -1;
	EquipementData equipementEnCours = null;
	
	public EquipementChecker(int idHeros) {
		this.idHeros = idHeros;
	}

	public TreeMap<Integer, EquipementData> getEquipements() {
		return equipements;
	}

	/* ================================
	 * MONTEUR D'ENSEMBLE D'EQUIPEMENTS
	 * ================================ */
	
	private void tropComplique() {
		if (equipementEnCours == null)
			return;
		
		equipementEnCours.setComplexe();
		decharger();
	}

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
