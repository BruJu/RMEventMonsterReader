package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import java.util.function.Function;

public class Serialiseur implements Function<Monstre, String> {

	private List<String> champs = new ArrayList<>();
	private List<Function<Monstre, String>> lecteursDeChamp = new ArrayList<>();

	public Serialiseur(Serialiseur serialiseur) {
		champs.addAll(serialiseur.champs);
		lecteursDeChamp.addAll(serialiseur.lecteursDeChamp);
	}

	public Serialiseur() {

	}

	public void ajouterChampALire(String nom, Function<Monstre, String> serialisation) {
		champs.add(nom);
		lecteursDeChamp.add(serialisation);
	}

	public String getHeader() {
		StringJoiner sj = new StringJoiner(";");

		for (String champ : champs) {
			sj.add(champ);
		}

		return sj.toString();
	}


	@Override
	public String apply(Monstre monstre) {
		StringJoiner sj = new StringJoiner(";");

		for (Function<Monstre, String> serialisationPartielle : lecteursDeChamp) {
			sj.add(serialisationPartielle.apply(monstre));
		}

		return sj.toString();
	}

	public void supprimer(String champ) {
		int index = champs.indexOf(champ);
		champs.remove(index);
		lecteursDeChamp.remove(index);
	}
}
