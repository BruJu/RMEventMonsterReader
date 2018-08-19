package fr.bruju.rmeventreader.implementation.equipementchecker;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class EquipementChecker implements ActionMakerDefalse {
	private List<Equipement> equipements = new ArrayList<>();
	
	Equipement equipementEnCours = null;

	private int idHeros;
	
	public void afficherEquipements() {
		equipements.stream()
		.sorted()
		.map(Equipement::getString).forEach(System.out::println);
	}
	
	public EquipementChecker(int idHeros) {
		this.idHeros = idHeros;
	}
	
	@Override
	public void changeSwitch(Variable interrupteur, boolean value) {
		if (equipementEnCours == null)
			return;
		
		equipementEnCours.interrupteursModifies.add(new Pair<>(interrupteur.idVariable, value));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		if (equipementEnCours == null)
			return;
		
		if (operator == Operator.PLUS) {
			equipementEnCours.variablesModifiees.add(new Pair<>(variable.idVariable, returnValue.valeur));
		} else if (operator == Operator.MINUS) {
			equipementEnCours.variablesModifiees.add(new Pair<>(variable.idVariable, -returnValue.valeur));
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
		
		if ((var == 828
				|| var == 483 || var == 484
				
				) && operatorValue.equals(Operator.IDENTIQUE)) {
			equipementEnCours = new Equipement(returnValue.valeur);
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
			equipementEnCours = new Equipement(itemId);
			return true;
		}
	}


	@Override
	public void condElse() {
		tropComplique();
	}

	private void tropComplique() {
		if (equipementEnCours == null)
			return;
		
		equipementEnCours.setComplexe();
		decharger();
	}

	private void decharger() {
		if (equipementEnCours == null)
			return;
		
		equipements.add(equipementEnCours);
		equipementEnCours = null;
	}

	@Override
	public void condEnd() {
		decharger();
	}

}
