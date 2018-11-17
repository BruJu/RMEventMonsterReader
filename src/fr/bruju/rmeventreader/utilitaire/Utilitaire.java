package fr.bruju.rmeventreader.utilitaire;

import fr.bruju.util.ListAsStack;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.function.*;

/**
 * Fourni un ensemble de classes static utilitaires
 */
public class Utilitaire {

	/**
	 * Ecrit dans le fichier dont le chemin est spécifié la chaîne à écrire
	 * @param chemin Le fichier
	 * @param chaineAEcrire La chaîne
	 */
	public static void Fichier_Ecrire(String chemin, String chaineAEcrire) {
		File f = new File(chemin);

		try {
			f.createNewFile();
			FileWriter ff = new FileWriter(f);
			ff.write(chaineAEcrire);
			ff.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Supprime le dossier donné en supprimant tous les fichiers à l'intérieur
	 * @param dossier Le dossier à supprimer
	 */
	public static void Fichier_supprimerDossier(File dossier) {
		for (File fichierPresent : dossier.listFiles()) {
			if (fichierPresent.isDirectory())
				Fichier_supprimerDossier(fichierPresent);
			else
				fichierPresent.delete();
		}

		dossier.delete();
	}

	/**
	 * Transforme la liste en un tableau de int
	 * @param liste La liste
	 * @return Un tableau de int
	 */
	public static int[] toArrayInt(List<Integer> liste) {
		int[] tableau = new int[liste.size()];

		for (int i = 0 ; i != tableau.length ; i++) {
			tableau[i] = liste.get(i);
		}

		return tableau;
	}
	
	/**
	 * Donne la position de l'élément dans le tableau, ou -1 si il est absent
	 * @param element L'élément à chercher
	 * @param elements Le tableau de nombres
	 * @return La position du nombre
	 */
	public static int getPosition(int element, int[] elements) {
		for (int i = 0 ; i != elements.length ; i++) {
			if (elements[i] == element) {
				return i;
			}
		}
		
		return -1;
	}

	public static <T> T[] Arrays_aggrandir(T[] tableau, int tailleVoulue, Supplier<T> provider) {
		if (tableau.length == tailleVoulue) {
			return tableau;
		}
		
		T[] nouveauTableau = Arrays.copyOf(tableau, tailleVoulue);
		
		for (int i = tableau.length ; i != tailleVoulue ; i++) {
			nouveauTableau[i] = provider.get();
		}
		
		return nouveauTableau;
	}


	public static <T> int comparerIterateurs(Supplier<T> source1, Supplier<T> source2,
											 ToIntBiFunction<T, T> fonctionDeComparaison) {
		while (true) {
			T objet1 = source1.get();
			T objet2 = source2.get();

			if (objet1 == null) {
				if (objet2 == null) {
					return 0;
				} else {
					return -1;
				}
			} else {
				if (objet2 == null) {
					return 1;
				} else {
					int comparaison = fonctionDeComparaison.applyAsInt(objet1, objet2);

					if (comparaison != 0) {
						return comparaison;
					}
				}
			}
		}
	}

	public static <T> boolean comparerIterateursBoolean(Supplier<T> source1, Supplier<T> source2,
											 BiPredicate<T, T> fonctionDeComparaison) {
		while (true) {
			T objet1 = source1.get();
			T objet2 = source2.get();

			if (objet1 == null) {
				return objet2 == null;
			} else {
				if (objet2 == null || !fonctionDeComparaison.test(objet1, objet2)) {
					return false;
				}
			}
		}
	}

}
