package fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Statistique;

/**
 * Classe permettant de stocker la statistique modifiée et l'opérateur qui lui est appliqué
 * 
 * @author Bruju
 *
 */
public class ModifStat {
	/** Stat modifiée */
	public final Statistique stat;
	/** Opérateur appliqué */
	public final Operator operateur;

	/**
	 * Crée un modificateur de statistique
	 * 
	 * @param stat Statistique affectée
	 * @param operator Opérateur utilisé
	 */
	public ModifStat(Statistique stat, Operator operator) {
		this.stat = stat;
		this.operateur = operator;
	}

	@Override
	public String toString() {
		return "<" + stat.possesseur.getNom() + "." + stat.nom + ";" + operateur + ">";
	}
}
