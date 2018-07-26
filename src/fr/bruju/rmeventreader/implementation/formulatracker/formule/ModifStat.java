package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Statistique;

public class ModifStat {
	public final Statistique stat;
	public final Operator operateur;

	public ModifStat(Statistique stat, Operator operator) {
		this.stat = stat;
		this.operateur = operator;
	}
}
