package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Donnees;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Classe exécutée avant la première classe d'analyser élémentaire pour créer les données
 * 
 * @author Bruju
 *
 */
public class ElementsInit implements Runnable {
	/** La base de données */
	private MonsterDatabase bdd;

	/** Contexte élémentaire (contient les associations nom - id variable par exemple */
	private ContexteElementaire contexte;
	
	/**
	 * Crée une instance de la classe
	 * @param db La base de données
	 */
	public ElementsInit(MonsterDatabase bdd, ContexteElementaire contexte) {
		this.bdd = bdd;
		this.contexte = contexte;
	}

	
	@Override
	public void run() {
		bdd.extractMonsters().forEach(this::initialiserMonstre);
	}
	
	/**
	 * Initialise les données qui seront enregistrées
	 * @param monstre Le monstre à initialiser
	 */
	private void initialiserMonstre(Monstre monstre) {
		monstre.donnees.put(ContexteElementaire.ELEMENTS,
				new Donnees<>(monstre, contexte.getElements(), 0, Object::toString));
		monstre.donnees.put(ContexteElementaire.PARTIES,
				new Donnees<>(monstre, contexte.getParties(), false, v -> v ? "x" : " "));
	}
}
