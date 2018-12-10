package fr.bruju.rmeventreader.implementation.magasin.livre;

import fr.bruju.rmeventreader.implementation.magasin.ChercheurDeMagasins;
import fr.bruju.rmeventreader.implementation.magasin.Magasin;
import fr.bruju.rmeventreader.implementation.magasin.Objet;
import fr.bruju.util.MapsUtils;
import fr.bruju.util.Pair;

import java.util.*;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Le jeu étudié propose un système consistant à lire des livres sur l'univers du jeu afin d'obtenir des statistiques
 * pour nos personnages. Ce module se donne pour but de lister pour chaque magasin quelles sont les statistiques que
 * l'on peut augmenter.
 */
public class MagasinDeStatistiques {
	public static void afficherMagasinsDeStatistiques() {
		Collection<Magasin> magasinsExistants = new ChercheurDeMagasins().chercher().getMagasins();
		List<Magasin> librairies = filtrerLibrairies(magasinsExistants);
		ChercheurDeMagasins.afficherMagasinsComplets(librairies);
	}


	public static List<Magasin> filtrerLibrairies(Collection<Magasin> magasinsLus) {
		List<Magasin> librairies = new ArrayList<>();

		Map<Integer, StatistiqueDeLivre> statistiquesAcquerables = lireLesStatistiques();

		for (Magasin existant : magasinsLus) {
			Magasin magasin = new Magasin(existant);

			for (Objet objet : existant.objetsVendus()) {
				StatistiqueDeLivre statistiqueAugmentee = statistiquesAcquerables.get(objet.id);

				if (statistiqueAugmentee != null) {
					magasin.ajouterObjet(new Livre(objet.id, objet.nom, statistiqueAugmentee));
				}
			}

			if (magasin.vendUnObjet()) {
				librairies.add(magasin);
			}
		}

		return librairies;
	}

	private static Map<Integer, StatistiqueDeLivre> lireLesStatistiques() {
		LivresMenus livresMenus = new LivresMenus();
		PROJET.lireEvenementCommun(livresMenus, 351);
		return livresMenus.getResultat();
	}

	public static void afficherLivresAccessibles(String[] nomsDeVilles) {
		Collection<Magasin> magasinsExistants = new ChercheurDeMagasins().chercher().getMagasins();
		List<Magasin> librairies = filtrerLibrairies(magasinsExistants);

		CategorieDeLivre[] categories = new CategorieDeLivre[StatistiqueDeLivre.values().length];

		for (int i = 0 ; i != categories.length ; i++) {
			categories[i] = new CategorieDeLivre(StatistiqueDeLivre.values()[i]);
		}

		for (Magasin librairie : librairies) {
			if (!contient(librairie.getLieu(), nomsDeVilles)) {
				continue;
			}

			for (Objet objet : librairie.objetsVendus()) {
				Livre livre = (Livre) objet;
				categories[livre.statistique.ordinal()].ajouterLivre(livre, librairie);
			}
		}

		for (CategorieDeLivre categorie : categories) {
			System.out.println(categorie.getAffichage());
		}
	}

	public static boolean contient(String mot, String[] chaines) {
		for (String chaine : chaines) {
			if (mot.contains(chaine)) {
				return true;
			}
		}

		return false;
	}
}
