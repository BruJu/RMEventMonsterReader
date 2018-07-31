package fr.bruju.rmeventreader.implementation.monsterlist.elements;

import static fr.bruju.rmeventreader.implementation.monsterlist.elements.ContexteElementaire.ELEMENTS;
import static fr.bruju.rmeventreader.implementation.monsterlist.elements.ContexteElementaire.PARTIES;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;

import fr.bruju.rmeventreader.actionmakers.actionner.ActionMakerDefalse;
import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.StackedActionMaker;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMonsterId;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Donnees;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

public class ScriptGlobal extends StackedActionMaker<Monstre> {
	private MonsterDatabase bdd;
	private ContexteElementaire contexte;

	public ScriptGlobal(MonsterDatabase bdd, ContexteElementaire contexte) {
		this.bdd = bdd;
		this.contexte = contexte;
	}

	@Override
	protected Collection<Monstre> getAllElements() {
		return bdd.extractMonsters();
	}

	@Override
	public void changeSwitch(Variable interrupteur, boolean value) {
		String nom = contexte.getPartie(interrupteur.idVariable);

		this.getElementsFiltres().forEach(monstre -> monstre.accessBool(PARTIES).set(nom, value));
	}

	@Override
	public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
		String nom = contexte.getElement(variable.idVariable);

		this.getElementsFiltres().forEach(monstre -> monstre.accessInt(ELEMENTS).compute(nom,
				(n, ex) -> operator.compute(ex, returnValue.valeur)));
	}

	@Override
	public void callCommonEvent(int eventNumber) {
		ScriptGeneral p = new ScriptGeneral(bdd, contexte);

		Interpreter interpreter = new Interpreter(p);

		try {
			interpreter.inputFile(new File(ContexteElementaire.RESSOURCES_PREFIXE + "Second.txt"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void getComment(String str) {
		if (str.equals("Mettre à 0 les résistances élémentaires")) {
			// Initialisation
			bdd.extractMonsters().forEach(this::initialiserMonstre);

		}

		if (str.equals("REHAUSSER LES RESISTANCES")) {
			// Fin - Application manuelle du calcul de réhaussement des résistances
			bdd.extractMonsters().forEach(this::modifierMonstre);
		}
	}

	private void initialiserMonstre(Monstre monstre) {
		monstre.donnees.put(ContexteElementaire.ELEMENTS,
				new Donnees<Integer>(monstre, contexte.getElements(), 0, v -> v.toString()));
		monstre.donnees.put(ContexteElementaire.PARTIES,
				new Donnees<Boolean>(monstre, contexte.getParties(), false, v -> v ? "x" : "_"));

	}

	private void modifierMonstre(Monstre monstre) {
		Collection<String> elements = contexte.getElements();

		int bonusCalc = monstre.accessInt(Monstre.STATS).get("Niveau");
		bonusCalc = (bonusCalc / 7) * 5;

		int bonus = bonusCalc; // Java refuse de prendre bonusCalc directement

		monstre.accessInt(ContexteElementaire.ELEMENTS).compute("Physique", (n, v) -> v - bonus / 2);

		elements.stream().filter(element -> !element.equals("Physique")).forEach(
				element -> monstre.accessInt(ContexteElementaire.ELEMENTS).compute(element, (n, v) -> v - bonus));
	}

	public class ScriptGeneral extends StackedActionMaker<Monstre> {
		private MonsterDatabase bdd;
		private ContexteElementaire contexte;
		private ArrayList<ActionPage> actionsPage = new ArrayList<>();
		private int ID_VARIABLE_MONSTRE_CIBLE = 552;
		private int EVENT_SOUS_FONCTIONS = 99;

		public ScriptGeneral(MonsterDatabase bdd, ContexteElementaire contexte) {
			this.bdd = bdd;
			this.contexte = contexte;
		}

		@Override
		protected Collection<Monstre> getAllElements() {
			return bdd.extractMonsters();
		}

		@Override
		public void changeSwitch(Variable interrupteur, boolean value) {
			String nom = contexte.getPartie(interrupteur.idVariable);

			this.getElementsFiltres().forEach(monstre -> monstre.accessBool(PARTIES).set(nom, value));
		}

		@Override
		public void changeVariable(Variable variable, Operator operator, ValeurFixe returnValue) {
			String nom = contexte.getElement(variable.idVariable);

			this.getElementsFiltres().forEach(monstre -> monstre.accessInt(ELEMENTS).compute(nom,
					(n, ex) -> operator.compute(ex, returnValue.valeur)));
		}

		@Override
		public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
			if (leftOperandValue != ID_VARIABLE_MONSTRE_CIBLE) {
				throw new LigneNonReconnueException("Script général : condition sur " + leftOperandValue);
			}

			this.conditions.push(new ConditionOnMonsterId(true, operatorValue, returnValue.valeur));
			return true;
		}

		@Override
		public void callMapEvent(int eventNumber, int eventPage) {
			if (eventNumber != EVENT_SOUS_FONCTIONS) {
				throw new LigneNonReconnueException(
						"Script général : appel d'évènement sur la carte sur " + eventNumber);
			}

			assurerExistance(eventPage);

			this.getElementsFiltres().forEach(actionsPage.get(eventPage - 1)::appliquer);
		}

		private void assurerExistance(int eventPage) {
			while (actionsPage.size() <= eventPage) {
				actionsPage.add(null);
			}

			if (actionsPage.get(eventPage - 1) == null) {
				Page p = new Page(contexte);
				Interpreter interpreter = new Interpreter(p);

				try {
					interpreter.inputFile(new File(ContexteElementaire.RESSOURCES_PREFIXE + eventPage + ".txt"));
				} catch (IOException e) {
					e.printStackTrace();
				}
				actionsPage.set(eventPage - 1, p.getResult());
			}
		}

	}

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

	}

	public static interface ActionPage {
		public void appliquer(Monstre monstre);
	}
}
