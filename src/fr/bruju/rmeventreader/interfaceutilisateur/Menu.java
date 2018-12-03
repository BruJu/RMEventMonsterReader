package fr.bruju.rmeventreader.interfaceutilisateur;

import fr.bruju.rmeventreader.Principal;
import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

public class Menu {
	final String nomMenu;
	final List<Option> options;

	public Menu(String nomMenu) {
		this.nomMenu = nomMenu;
		this.options = new ArrayList<>();
	}

	public void ajouterOption(Option option) {
		options.add(option);
	}

	public void ajouterOption(String nom, Consumer<Scanner> action) {
		options.add(new Option(nom, action));
	}

	public void ajouterOption(String nom, Runnable action) {
		options.add(new Option(nom, action));
	}


	public String getListeDesOptions() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i != options.size(); i++) {
			sb.append(i + 1).append(" : ").append(options.get(i).nom).append("\n");
		}

		return sb.toString();
	}

}
