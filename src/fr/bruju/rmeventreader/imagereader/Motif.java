package fr.bruju.rmeventreader.imagereader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

/**
 * Motif preexistant
 */
public class Motif {
	/* =================================
	 * IDENTIFICATION DE TOUS LES MOTIFS
	 * ================================= */
	
	/** Chemin vers la liste des motifs connus */
	private static final String CHEMIN_MOTIFS_CONNUS = "metaressources/ocr/motifsconnus.txt";

	/**
	 * Renvoie la liste des motifs déjà connus, qui sont dans metaressources/ocr/motifsconnus.txt
	 */
	static List<Motif> listerLesMotifs() {
		List<Motif> motifs = LecteurDeFichiersLigneParLigne.listerRessources(CHEMIN_MOTIFS_CONNUS, Motif::new);
		return motifs == null ? new ArrayList<>() : motifs;
	}
	

	/* ===========
	 * OBJET MOTIF
	 * =========== */
	
	/* --------------------------
	 * Attributs et constructeurs
	 * -------------------------- */
	
	/** Chaîne représentée par le motif */
	private String lettre;

	/**
	 * Représentation numérique du motif
	 * <br>Chaque nombre correspond à une ligne. Une ligne est encodé en utilisant la représentation binaire de la
	 * suite de symboles de droite à gauche.
	 * <br>Exemples :
	 * <ul>
	 *  <li>"x" = 1</li>
	 *  <li>"" = 0</li>
	 *  <li>"xx" = 3 (1 + 2)</li>
	 *  <li>" x" = 2</li>
	 *  <li>"x " = 1 (équivaut à "x"</li>
	 *  <li>"xx x" = 13 (1 + 2 + 0 + 8)</li>
	 * </ul>
	 * Autrement dit, la présence du ieme symbole signifie que sa représentation se voit ajoutée du nombre 2^i
	 */
	private int[] composition;

	/**
	 * Crée un motif à partir de sa sérialisation (Symbole Nombres_Constituant_Sa_Représentation_Numériqueà
	 * @param serialisation La sérialisation du motif
	 */
	private Motif(String serialisation) {
		Scanner scanner = new Scanner(serialisation);
		lettre = scanner.next();
		composition = lireTousLesNombres(scanner);
		scanner.close();
	}

	/**
	 * Lis tous les nombres possibles via le scanner
	 * @param scanner Le scanner
	 * @return Un tableau avec tous les nombres lus via scanenr.nextInt();
	 */
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
	public Motif(int[] composition) {
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

	public String serialiser() {
		StringBuilder sb = new StringBuilder();
		
		String lettre = this.lettre;
		if (lettre == null) {
			lettre = "?";
		}
		
		sb.append(lettre);
		for (int valeur : composition) {
			sb.append(" ").append(valeur);
		}
		
		return sb.toString();
	}
	
	/**
	 * Dessine le motif donné sous forme numérique
	 * 
	 * @param tab Motif sous forme numérique
	 */
	public String dessinerMotif() {
		StringBuilder stringBuilder = new StringBuilder();
		for (int valeur : composition) {
			dessinerUneLigneDeMotif(stringBuilder, valeur);
		}
		
		return stringBuilder.toString();
	}

	/**
	 * Dessine une ligne de motif à partir du nombre donné
	 * 
	 * @param valeur Le nombre représentant la ligne
	 */
	private static void dessinerUneLigneDeMotif(StringBuilder stringBuilder, int valeur) {
		while (valeur != 0) {
			if (valeur % 2 == 1) {
				stringBuilder.append("x");
			} else {
				stringBuilder.append(" ");
			}

			valeur = valeur / 2;
		}
		
		stringBuilder.append("\n");
	}

	public String getChaineDeNonReconnaissance() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("Motif non reconnu :\n");
		sb.append(dessinerMotif()).append("\n");
		sb.append(serialiser()).append("\n");
		
		return sb.toString();
	}
}