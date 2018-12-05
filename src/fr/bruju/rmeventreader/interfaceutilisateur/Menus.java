package fr.bruju.rmeventreader.interfaceutilisateur;

import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.ChercheurDeReferences;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.module.*;
import fr.bruju.rmeventreader.implementation.detectiondeformules.Simplifieur;
import fr.bruju.rmeventreader.implementation.equipementchecker.Verificateur;
import fr.bruju.rmeventreader.implementation.magasin.ChercheurDeMagasins;
import fr.bruju.rmeventreader.implementation.monsterlist.ListeurDeMonstres;
import fr.bruju.rmeventreader.implementation.obtentionobjet.ObteneurDObjets;
import fr.bruju.rmeventreader.implementation.random.AppelsDEvenements;
import fr.bruju.rmeventreader.implementation.random.ChercheurDImages;
import fr.bruju.rmeventreader.implementation.random.DetecteurDeColissionsDInterrupteurs;
import fr.bruju.rmeventreader.implementation.recherchecombat.ChercheCombat;
import fr.bruju.rmeventreader.implementation.recherchecombat.ListeurDeMonstresDansUneZone;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Supplier;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class Menus {

	public static Menu creerMenuGeneral() {
		Menu menu = new Menu("RMEventReader");

		menu.ajouterOption(new Option("Liste des monstres", new InviteDeCommande(creerMenuListeurDeMonstres())));
		menu.ajouterOption(new Option("Sauver les ressources", () -> PROJET.ecrireRessource("ressources_gen\\")));
		menu.ajouterOption(new Option("Verificateur d'équipements", new Verificateur()));
		menu.ajouterOption(new Option("Compteur d'appels à des évènements communs", new AppelsDEvenements()));
		menu.ajouterOption(new Option("Chercheur d'images", new Intercepteur("Numéro de la map", ChercheurDImages::new)));
		menu.ajouterOption(new Option("Chercheur de magasins", new ChercheurDeMagasins()));
		menu.ajouterOption(new Option("Formules des attaques", new Simplifieur()));
		menu.ajouterOption(new Option("Collision des interrupteurs", new DetecteurDeColissionsDInterrupteurs()));

		menu.ajouterOption(new Option("Recherche", new InviteDeCommande(baseDeRecherche())));
		menu.ajouterOption(new Option("Obtention d'objets", scanner -> {
			System.out.print("Objet recherché : ");
			new ObteneurDObjets(scanner.nextLine()).run();
		}));

		menu.ajouterOption(new Option("Recherche de monstres", scanner -> {
			System.out.print("Rechercher dans les cartes contenant : ");
			new ListeurDeMonstresDansUneZone().run(scanner.nextLine());
		}));


		return menu;
	}

	private static Menu baseDeRecherche() {
		Menu menu = new Menu("Recherche");

		menu.ajouterOption("Apparition d'une variable", nombres("Variables à chercher", ApparitionDeVariables::new));
		menu.ajouterOption("Modifications d'une variable", nombre("Variable", ActivationDInterrupteur::new));
		menu.ajouterOption("Obtention d'un objet", nombre("Numéro de l'objet", ObjetObtenu::new));
		menu.ajouterOption("Texte", texte("Chaîne cherchée", Texte::new));
		menu.ajouterOption("Activation d'interrupteur", nombre("Interrupteur", ActivationDInterrupteur::new));
		menu.ajouterOption("Musique", bdr(Musique::new));
		menu.ajouterOption("Appel à un évènement commun", nombre("EvenementCherché", AppelAUnEvenement::new));
		menu.ajouterOption("Apprentissage d'un sort", nombre("Personnage", "Sort", ApprentissageSort::new));
		menu.ajouterOption("Augmentation de valeur de variables", nombres("Variables à chercher", DoubleVariableAjoute::new));

		return menu;
	}

	private static Consumer<Scanner> bdr(Supplier<BaseDeRecherche> supplier) {
		return scanner -> new ChercheurDeReferences(supplier.get()).run();
	}

	private static Consumer<Scanner> nombres(String chaine, Function<List<Integer>, BaseDeRecherche> instanciation) {
		return scanner -> {
			System.out.println(chaine + " (0 pour finir)");
			List<Integer> nombres = new ArrayList<>();

			while (true) {
				int nombreSaisi = Integer.parseInt(scanner.nextLine());

				if (nombreSaisi == 0) {
					break;
				} else {
					nombres.add(nombreSaisi);
				}
			}

			BaseDeRecherche base = instanciation.apply(nombres);
			new ChercheurDeReferences(base).run();
		};
	}

	private static Consumer<Scanner> nombre(String chaine, IntFunction<BaseDeRecherche> instanciation) {
		return scanner -> {
			System.out.print(chaine + " : ");
			int nombreSaisi = Integer.parseInt(scanner.nextLine());
			BaseDeRecherche base = instanciation.apply(nombreSaisi);
			new ChercheurDeReferences(base).run();
		};
	}


	private interface BiIntFunction<T> {
		public T apply(int a, int b);
	}

	private static Consumer<Scanner> nombre(String chaine1, String chaine2,
											BiIntFunction<BaseDeRecherche> instanciation) {
		return scanner -> {
			System.out.print(chaine1 + " : ");
			int nombreSaisi1 = Integer.parseInt(scanner.nextLine());
			System.out.print(chaine2 + " : ");
			int nombreSaisi2 = Integer.parseInt(scanner.nextLine());
			BaseDeRecherche base = instanciation.apply(nombreSaisi1, nombreSaisi2);
			new ChercheurDeReferences(base).run();
		};
	}

	private static Consumer<Scanner> texte(String chaine, Function<String, BaseDeRecherche> instanciation) {
		return scanner -> {
			System.out.print(chaine + " : ");
			String texteSaisi = scanner.nextLine();
			BaseDeRecherche base = instanciation.apply(texteSaisi);
			new ChercheurDeReferences(base).run();
		};
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

	public static class Intercepteur implements Consumer<Scanner> {
		private final String message;
		private final IntFunction<Runnable> createur;

		public Intercepteur(String message, IntFunction<Runnable> createur) {
			this.message = message;
			this.createur = createur;
		}

		@Override
		public void accept(Scanner scanner) {
			System.out.println(message + " : ");
			int numero = Integer.parseInt(scanner.nextLine());
			createur.apply(numero).run();
		}
	}
}