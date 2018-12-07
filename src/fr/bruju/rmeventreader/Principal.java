package fr.bruju.rmeventreader;

import fr.bruju.rmeventreader.interfaceutilisateur.InviteDeCommande;
import fr.bruju.rmeventreader.interfaceutilisateur.Menu;
import fr.bruju.rmeventreader.interfaceutilisateur.Menus;

import java.util.Scanner;

/** Classe principale */
public class Principal {
	/** Fonction principale */
	public static void main(String[] args) {
		System.out.println("#### Début ####");
		System.out.println("• Projet ouvert : " + Parametre.get("DOSSIER"));
		System.out.println("• Pour reafficher les choix disponibles, tapez 0");
		System.out.println("• Pour quitter un menu, tapez -1");

		Menu menu = Menus.creerMenuGeneral();
		InviteDeCommande inviteDeCommande = new InviteDeCommande(menu);

		Scanner scanner = new Scanner(System.in);
		inviteDeCommande.accept(scanner);

		System.out.println("#### Fin ####");
	}
}
