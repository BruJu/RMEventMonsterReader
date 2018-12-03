package fr.bruju.rmeventreader.interfaceutilisateur;


import java.util.Scanner;
import java.util.function.Consumer;

public class InviteDeCommande implements Consumer<Scanner> {
	private final Menu menu;

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

			System.out.println("Votre choix : ");
			choix = Integer.parseInt(scanner.next());

			if (choix == 0) {
				System.out.println(menu.getListeDesOptions());
				nePasRepeterNomDuMenu = true;
			} else if (choix == -1) {
				break;
			} else {
				if (choix < 0 || choix > menu.options.size()) {
					System.out.println("Choix invalide");
				} else {
					menu.options.get(choix-1).runnable.accept(scanner);
				}
			}
		}
	}
}
