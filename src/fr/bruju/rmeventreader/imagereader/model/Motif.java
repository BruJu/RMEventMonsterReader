package fr.bruju.rmeventreader.imagereader.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

/**
 * Motif preexistant
 */
public class Motif {
	/** Chemin vers la liste des motifs connus */
	private static final String CHEMIN_MOTIFS_CONNUS = "metaressources/ocr/motifsconnus.txt";

	/**
	 * Renvoie la liste des motifs déjà connus, qui sont dans metaressources/ocr/motifsconnus.txt
	 */
	public static List<Motif> listerLesMotifs() {
		return LecteurDeFichiersLigneParLigne.listerRessources(CHEMIN_MOTIFS_CONNUS, Motif::new);
	}
	
	
	/** Chaîne représentée par le motif */
	private String lettre;

	/** Représentation numérique du motif */
	private int[] composition;

	
	public Motif(String serialisation) {
		Scanner scanner = new Scanner(serialisation);
		lettre = scanner.next();
		composition = lireTousLesNombres(scanner);
		scanner.close();
	}

	private int[] lireTousLesNombres(Scanner scanner) {
		List<Integer> valeursMotif = new ArrayList<>();

		while (scanner.hasNextInt()) {
			valeursMotif.add(scanner.nextInt());
		}
		
		int[] valeurs = valeursMotif.stream().mapToInt(valeur -> valeur).toArray();
		
		return valeurs;
	}

	/**
	 * Crée un motif préconnu
	 * 
	 * @param lettre Lettre représentée par le motif
	 * @param composition Représentation numérique du motif
	 */
	public Motif(char lettre, int[] composition) {
		this.lettre = Character.toString(lettre);
		this.composition = composition;
	}

	/**
	 * Permet de savoir si ce motif et le motif donné sont identiques
	 * 
	 * @param candidat Le motif dont on souhaite savoir si il est égal à ce motif
	 * @return Vrai si candidat et le motif représenté sont identiques
	 */
	public boolean comparer(int[] candidat) {
		return Arrays.equals(composition, candidat);
	}

	/**
	 * Renvoie la chaîne représentant le motif
	 * 
	 * @return La chaîne représentant le motif
	 */
	public String getSymboleDesigne() {
		return lettre;
	}

	/**
	 * Dessine le motif donné sous forme numérique
	 * 
	 * @param tab Motif sous forme numérique
	 */
	public static void dessinerUnMotif(int[] tab) {
		for (int valeur : tab) {
			dessinerUneLigneDeMotif(valeur);
			System.out.println();
		}
	}

	/**
	 * Dessine une ligne de motif à partir du nombre donné
	 * 
	 * @param valeur Le nombre représentant la ligne
	 */
	private static void dessinerUneLigneDeMotif(int valeur) {
		while (valeur != 0) {
			if (valeur % 2 == 1) {
				System.out.print("x");
			} else {
				System.out.print(" ");
			}

			valeur = valeur / 2;
		}
	}
}