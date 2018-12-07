package fr.bruju.rmeventreader.interfaceutilisateur;

import fr.bruju.util.Pair;

import java.util.ArrayList;
import java.util.List;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class RechercheDansDictionnaire {


	public static Pair<Integer, String> variableUnique(String chaine) {
		List<Pair<Integer, String>> liste = variable(chaine);

		if (liste == null) {
			System.out.println("Saisie non valide");
		} else if (liste.size() == 1) {
			return liste.get(0);
		} else if (liste.isEmpty()) {
			System.out.println("Aucune variable ne contient " + chaine);
		} else {
			System.out.println("Plusieurs variables contiennent " + chaine);

			for (Pair<Integer, String> variablesTrouvees : liste) {
				System.out.println(variablesTrouvees.getLeft() + " : " + variablesTrouvees.getRight());
			}
		}

		return null;
	}

	public static List<Pair<Integer, String>> variable(String chaine) {
		return recherche(chaine, PROJET.extraireVariables());
	}


	public static Pair<Integer, String> interrupteurUnique(String chaine) {
		List<Pair<Integer, String>> liste = interrupteur(chaine);

		if (liste == null) {
			System.out.println("Saisie non valide");
		} else if (liste.size() == 1) {
			return liste.get(0);
		} else if (liste.isEmpty()) {
			System.out.println("Aucun interrupteur ne contient " + chaine);
		} else {
			System.out.println("Plusieurs interrupteurs contiennent " + chaine);

			for (Pair<Integer, String> interrupteursTrouves : liste) {
				System.out.println(interrupteursTrouves.getLeft() + " : " + interrupteursTrouves.getRight());
			}
		}

		return null;
	}

	public static List<Pair<Integer, String>> interrupteur(String chaine) {
		return recherche(chaine, PROJET.extraireInterrupteurs());
	}


	public static Pair<Integer, String> objetUnique(String chaine) {
		List<Pair<Integer, String>> liste = objet(chaine);

		if (liste == null) {
			System.out.println("Saisie non valide");
		} else if (liste.size() == 1) {
			return liste.get(0);
		} else if (liste.isEmpty()) {
			System.out.println("Aucun objet ne contient " + chaine);
		} else {
			System.out.println("Plusieurs objets contiennent " + chaine);

			for (Pair<Integer, String> objetsTrouves : liste) {
				System.out.println(objetsTrouves.getLeft() + " : " + objetsTrouves.getRight());
			}
		}

		return null;
	}

	public static List<Pair<Integer, String>> objet(String chaine) {
		return recherche(chaine, PROJET.extraireObjets());
	}

	private static List<Pair<Integer, String>> recherche(String chaine, List<String> dictionnaire) {
		List<Pair<Integer, String>> liste = new ArrayList<>();

		try {
			int idObjet = Integer.parseInt(chaine);

			if (idObjet < 0 || idObjet >= dictionnaire.size()) {
				return null;
			}

			liste.add(new Pair<>(idObjet, dictionnaire.get(idObjet - 1)));
		} catch (NumberFormatException exception) {
			for (int i = 0 ; i != dictionnaire.size() ; i++) {
				String nom = dictionnaire.get(i);

				if (nom.contains(chaine)) {
					liste.add(new Pair<>(i + 1, nom));
				}
			}
		}

		return liste;
	}
}
