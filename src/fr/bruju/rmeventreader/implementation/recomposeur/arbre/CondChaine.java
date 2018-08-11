package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.ConditionAffichable;

/**
 * Condition affichable constitué d'une simple chaîne
 * @author Bruju
 *
 */
public class CondChaine implements ConditionAffichable {
	/** Chaîne àa fficher */
	private String chaine;

	/**
	 * Crée une condition affichable affichant la chaîne donnée
	 * @param chaine La chaîne
	 */
	public CondChaine(String chaine) {
		this.chaine = chaine;
	}

	@Override
	public String getString() {
		return chaine;
	}
}
