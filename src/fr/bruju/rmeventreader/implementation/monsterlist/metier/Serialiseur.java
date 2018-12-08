package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

public class Serialiseur {
	private List<String> champs;
	private List<Function<Monstre, String>> lecteursDeChamp;

	public Serialiseur() {
		champs = new ArrayList<>();
		lecteursDeChamp = new ArrayList<>();
	}

	public Serialiseur(Serialiseur serialiseur) {
		champs = new ArrayList<>(serialiseur.champs);
		lecteursDeChamp = new ArrayList<>(serialiseur.lecteursDeChamp);
	}

	/* =======================
	 * MODIFICATION DES CHAMPS
	 * ======================= */

	public void ajouterChampALire(String nom, Function<Monstre, String> serialisation) {
		champs.add(nom);
		lecteursDeChamp.add(serialisation);
	}

	public void supprimer(String champ) {
		int index = champs.indexOf(champ);
		champs.remove(index);
		lecteursDeChamp.remove(index);
	}


	/* =========
	 * AFFICHAGE
	 * ========= */

	/**
	 * Donne la liste des noms des champs
	 * @return La liste des champs séparés par un ;
	 */
	public String getEnTete() {
		StringJoiner sj = new StringJoiner(";");

		for (String champ : champs) {
			sj.add(champ);
		}

		return sj.toString();
	}

	/**
	 * Transforme un monstre en une chaîne le représentant
	 * @param monstre Le monstre
	 * @return Une représentation du monstre
	 */
	public String serialiserMonstre(Monstre monstre) {
		StringJoiner sj = new StringJoiner(";");

		for (Function<Monstre, String> serialisationPartielle : lecteursDeChamp) {
			sj.add(serialisationPartielle.apply(monstre));
		}

		return sj.toString();
	}
}
