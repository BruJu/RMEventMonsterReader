package fr.bruju.rmeventreader.implementation.monsterlist.elements;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

public class Page implements ActionMakerDefalse {
	private ContexteElementaire contexte;

	private List<Consumer<Monstre>> actionsARealiser = new ArrayList<>();

	public Page(ContexteElementaire contexte) {
		this.contexte = contexte;
	}
	
	public ActionPage getResult() {
		return monstre -> actionsARealiser.forEach(action -> action.accept(monstre));
	}

	@Override
	public void changeSwitch(Variable interrupteur, boolean value) {
		String nomPartie = contexte.getPartie(interrupteur.get());

		actionsARealiser.add(monstre -> monstre.accessBool(ContexteElementaire.PARTIES).set(nomPartie, value));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		String nomElement = contexte.getElement(variable.idVariable);

		actionsARealiser.add(monstre -> monstre.accessInt(ContexteElementaire.ELEMENTS).compute(nomElement,
				(nom, valeur) -> operator.compute(valeur, returnValue.valeur)));
	}
	
	@Override
	public void condElse() {
		return;
	}

	@Override
	public void condEnd() {
		return;
	}
	
	public static interface ActionPage {
		public void appliquer(Monstre monstre);
	}
}
