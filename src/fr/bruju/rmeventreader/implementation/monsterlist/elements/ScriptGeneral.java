package fr.bruju.rmeventreader.implementation.monsterlist.elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import fr.bruju.rmeventreader.actionmakers.actionner.Interpreter;
import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.donnees.Variable;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.StackedActionMaker;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMonsterId;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

import static fr.bruju.rmeventreader.implementation.monsterlist.elements.ContexteElementaire.ELEMENTS;
import static fr.bruju.rmeventreader.implementation.monsterlist.elements.ContexteElementaire.PARTIES;

public class ScriptGeneral extends StackedActionMaker<Monstre> {
	private MonsterDatabase bdd;
	private ContexteElementaire contexte;
	private ArrayList<Page.ActionPage> actionsPage = new ArrayList<>();
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
		if (leftOperandValue != ID_VARIABLE_MONSTRE_CIBLE ) {
			throw new LigneNonReconnueException("Script général : condition sur " + leftOperandValue);
		}
		
		this.conditions.push(new ConditionOnMonsterId(true, operatorValue, returnValue.valeur));
		return true;
	}

	@Override
	public void callMapEvent(int eventNumber, int eventPage) {
		if (eventNumber != EVENT_SOUS_FONCTIONS ) {
			throw new LigneNonReconnueException("Script général : appel d'évènement sur la carte sur " + eventNumber);
		}
		
		assurerExistance(eventPage);

		this.getElementsFiltres().forEach(actionsPage.get(eventPage - 1)::appliquer);
	}

	private void assurerExistance(int eventPage) {
		while (actionsPage.size() <= eventPage) {
			actionsPage.add(null);
		}
		
		if (actionsPage.get(eventPage-1) == null) {
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
