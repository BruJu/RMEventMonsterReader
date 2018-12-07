package fr.bruju.rmeventreader.interfaceutilisateur;


import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Un invite de commande gère la mise en relation des options disponibles dans le menu et des entrées de l'utilisateur.
 */
public class InviteDeCommande implements Consumer<Scanner> {
	/** Le menu géré */
	private final Menu menu;

	/**
	 * Crée un invite de commande pour le menu donné
	 * @param menu Le menu
	 */
	public InviteDeCommande(Menu menu) {
		this.menu = menu;
	}

	@Override
	public void accept(Scanner scanner) {
		boolean optionsDejaMontrees = false;
		boolean nePasRepeterNomDuMenu = false;
		int choix;

		while (true) {
			if (!nePasRepeterNomDuMenu) {
				System.out.println("== " + menu.nomMenu + " ==");
			} else {
				nePasRepeterNomDuMenu = false;
			}

			if (!optionsDejaMontrees) {
				System.out.println(menu.getListeDesOptions());
				optionsDejaMontrees = true;
			}

			System.out.print("Votre choix : ");
			choix = Integer.parseInt(scanner.nextLine());

			if (choix == 0) { // Répéter le menu en affichant les options
				System.out.println(menu.getListeDesOptions());
				nePasRepeterNomDuMenu = true;
			} else if (choix == -1) { // Quitter ce menu
				break;
			} else if (choix < 0 || choix > menu.options.size()) { // Commande invalide
				System.out.println("Choix invalide");
			} else { // Donner la main à l'option suivante
				menu.options.get(choix - 1).runnable.accept(scanner);
			}
		}
	}
}
