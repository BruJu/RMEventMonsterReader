package fr.bruju.rmeventreader.interfaceutilisateur;

import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;

public class Menus {

	public static Menu creerMenuGeneral() {
		Menu menu = new Menu("RMEventReader");

		menu.ajouterOption(new Option("Liste des monstres", new InviteDeCommande(creerMenuListeurDeMonstres())));

		return menu;
	}


	public static Menu creerMenuListeurDeMonstres() {
		Menu menu = new Menu("Liste des monstres");

		menu.ajouterOption(new Option("Liste des combats", new ListeurDeMonstres(0)));
		menu.ajouterOption(new Option("CSV des combats", new ListeurDeMonstres(1)));
		menu.ajouterOption(new Option("CSV des monstres par combat", new ListeurDeMonstres(2)));
		menu.ajouterOption(new Option("CSV des monstres (regroupés)", new ListeurDeMonstres(3)));
		menu.ajouterOption(new Option("Afficher les combats qui n'ont pas de fond connu", new ListeurDeMonstres(4)));
		menu.ajouterOption(new Option("Afficher les lieux de drop des objets", new ListeurDeMonstres(5)));
		menu.ajouterOption(new Option("Sauvegarder dans des fichiers toutes les données", new ListeurDeMonstres(6)));

		return menu;
	}
}
