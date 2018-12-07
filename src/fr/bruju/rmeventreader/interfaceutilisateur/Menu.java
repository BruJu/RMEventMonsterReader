package fr.bruju.rmeventreader.interfaceutilisateur;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Un menu repertorie la liste des fonctionnalités qu'il propose
 */
public class Menu {
	/** Le nom du menu */
	final String nomMenu;
	/** La liste des options proposées */
	final List<Option> options;

	public Menu(String nomMenu) {
		this.nomMenu = nomMenu;
		this.options = new ArrayList<>();
	}

	public void ajouterOption(String nom, Consumer<Scanner> action) {
		options.add(new Option(nom, action));
	}

	public void ajouterOption(String nom, Runnable action) {
		options.add(new Option(nom, action));
	}

	/**
	 * Donne la liste des options disponibles
	 * @return La liste des options disponibles
	 */
	public String getListeDesOptions() {
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i != options.size(); i++) {
			sb.append(i + 1).append(" : ").append(options.get(i).nom).append("\n");
		}

		return sb.toString();
	}
}
