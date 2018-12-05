package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Finaliser l'extraction des faiblesses élémentaires en appliquant un calcul pour réhausser les faiblesses par rapport
 * au niveau.
 * 
 * @author Bruju
 *
 */
public class ElementsFinalisation implements Runnable {
	/** La base de données */
	private MonsterDatabase bdd;

	/** Contexte élémentaire (contient les associations nom - id variable par exemple */
	private ContexteElementaire contexte;

	/**
	 * Crée une instance de la classe
	 * @param db La base de données
	 */
	public ElementsFinalisation(MonsterDatabase bdd, ContexteElementaire contexte) {
		this.bdd = bdd;
		this.contexte = contexte;
	}

	@Override
	public void run() {
		bdd.extractMonsters().forEach(this::finaliserMonstre);
	}

	/**
	 * Finalise le monstre en appliquant les modifications faites à la fin pour réhausser les résistances selon le
	 * niveau.
	 * @param monstre Le monstre à modifier
	 */
	private void finaliserMonstre(Monstre monstre) {
		int bonus = monstre.accessInt("Niveau") / 7 * 5;

		monstre.modifier("Physique", v -> v + bonus / 2);

		for (String element : contexte.getElements()) {
			if (!element.equals("Physique")) {
				monstre.modifier(element, v -> v + bonus);
			}
		}
	}
}
