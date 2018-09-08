package fr.bruju.rmeventreader.implementationexec.monsterlist.manipulation;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;
import fr.bruju.rmeventreader.implementationexec.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementationexec.monsterlist.metier.Monstre;

/**
 * Condition sur une statistique d'un monstre
 * 
 * @author Bruju
 *
 */
public class ConditionOnMembreStat implements Condition<Combat> {
	/**
	 * Statistique testée
	 */
	private String nomStatistique;
	
	/**
	 * Numéro du monstre testé
	 */
	private int numeroMonstre;
	
	/**
	 * Opérateur de comparaison
	 */
	private Comparateur operator;
	
	/**
	 * Valeur de référence
	 */
	private int compareTo;

	/**
	 * Construit une condition sur une statistique d'un monstre 
	 * @param posStat La statistique du monstre
	 * @param position La position du monstre
	 * @param operatorValue L'opérateur de comparaison
	 * @param value La valeur à laquelle la statistique sera comparée à droite de la comparaison
	 */
	public ConditionOnMembreStat(String nomStatistique, int position, Comparateur operatorValue, int value) {
		this.nomStatistique = nomStatistique;
		numeroMonstre = position;
		operator = operatorValue;
		compareTo = value;
	}

	@Override
	public void revert() {
		operator = operator.revert();
	}

	@Override
	public boolean filter(Combat combat) {
		int statMonstre;
		
		Monstre monstre = combat.getMonstre(numeroMonstre);
		
		if (monstre == null) {
			statMonstre = 0;
		} else {
			statMonstre = monstre.accessInt(Monstre.STATS).get(nomStatistique);
		}
		
		return operator.test(statMonstre, compareTo);
	}
}