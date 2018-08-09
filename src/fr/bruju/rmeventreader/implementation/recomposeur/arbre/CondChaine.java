package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.ConditionAffichable;

public class CondChaine implements ConditionAffichable {
	private String chaine;

	public CondChaine(String chaine) {
		this.chaine = chaine;
	}

	@Override
	public String getString() {
		return chaine;
	}
}
