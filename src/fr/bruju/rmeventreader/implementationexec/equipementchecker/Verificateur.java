package fr.bruju.rmeventreader.implementationexec.equipementchecker;

import java.util.TreeMap;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Explorateur;

/**
 * Ce module a pour but de vérifier si les équipements des personnages donnent autant de statistiques qu'ils n'en
 * reprennent quand on les enlève.
 * 
 * @author Bruju
 *
 */
public class Verificateur implements Runnable {
	@Override
	public void run() {
		verifierPersonnage(1, 67, 47, 141);
		verifierPersonnage(2, 70, 46, 162);
		verifierPersonnage(3, 52, 46, 158);
		verifierPersonnage(4, 71, 46, 257);
		verifierPersonnage(5, 69, 52, 133);
		verifierPersonnage(6, 68, 46, 159);
		verifierPersonnage(7, 73, 46, 182);
	}

	/**
	 * Vérifie si les équipements du personnage sont cohérents entre l'équipement et le déséquipement
	 * @param idPerso Numéro du personnage
	 * @param idMap Numéro de la map où les objets sont équipés
	 * @param idEvent Numéro de l'event où les objets sont équipés
	 * @param idEventCommun Numéro de l'event commun où les objets sont désequipés
	 */
	private void verifierPersonnage(int idPerso, int idMap, int idEvent, int idEventCommun) {
		System.out.println("------------- PERSONNAGE " + idPerso);

		EquipementChecker onEquip = new EquipementChecker(idPerso);
		Explorateur.lireEvenementMap(onEquip, idMap, idEvent, 1);
	

		EquipementChecker onDesequip = new EquipementChecker(idPerso);
		Explorateur.lireEvenementCommun(onDesequip, idEventCommun);
		 

		TreeMap<Integer, EquipementData> somme = EquipementData.combiner(onEquip.getEquipements(),
				onDesequip.getEquipements());

		somme = EquipementData.simplifier(somme);

		somme.forEach((idObjet, data) -> System.out.println("== Objet " + idObjet + "==\n" + data.getString()));
	}

}
