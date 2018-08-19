package fr.bruju.rmeventreader.implementation.equipementchecker;

import java.util.TreeMap;

import fr.bruju.rmeventreader.actionmakers.xml.AutoLibLcfXMLCache;

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

	private void verifierPersonnage(int idPerso, int idMap, int idEvent, int idEventCommun) {
		System.out.println("------------- PERSONNAGE " + idPerso);

		EquipementChecker onEquip = new EquipementChecker(idPerso);
		new AutoLibLcfXMLCache(onEquip, idMap, idEvent, 1).run();

		EquipementChecker onDesequip = new EquipementChecker(idPerso);
		new AutoLibLcfXMLCache(onDesequip, -1, idEventCommun, -1).run();

		TreeMap<Integer, EquipementData> somme = EquipementData.combiner(onEquip.getEquipements(),
				onDesequip.getEquipements());

		somme = EquipementData.simplifier(somme);

		somme.forEach((idObjet, data) -> System.out.println("== Objet " + idObjet + "==\n" + data.getString()));
	}

}
