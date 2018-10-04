package fr.bruju.rmeventreader.implementation.monsterlist.manipulation;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.rmdechiffreur.modele.Comparateur;

/**
 * Condition sur l'id d'un combat
 * @author Bruju
 *
 */
public class ConditionOnBattleId implements Condition<Combat> {
	/**
	 * Opérateur de comparaison
	 */
	private Comparateur operateur;
	
	/**
	 * Valeur de droite de comparaison
	 */
	private int rightValue;

	/**
	 * Construit une condition sur un combat
	 * tel que id du combat operator rightValue
	 * @param operator L'opérateur de comparaison
	 * @param rightValue La valeur de comparaison
	 */
	public ConditionOnBattleId(Comparateur operateur, int valeur) {
		this.operateur = operateur;
		this.rightValue = valeur;
	}

	@Override
	public void revert() {
		operateur = operateur.oppose;
	}

	@Override
	public boolean filter(Combat combat) {
		return operateur.test(combat.id, rightValue);
	}
}
