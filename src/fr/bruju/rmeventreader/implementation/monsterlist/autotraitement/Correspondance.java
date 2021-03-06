package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.monsterlist.metier.Combat;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Action consistant à faire remplacer des valeurs par d'autres à travers un dictionnaire.
 * 
 * @author Bruju
 *
 */
public class Correspondance {
	/**
	 * Classe permettant de remplacer des données dans les monstres
	 */
	public static abstract class Remplaceur<T> {
		public final void appliquer(MonsterDatabase bdd) {
			fonctionDExtraction(bdd).forEach(this::remplacer);
		}
		
		protected abstract void remplacer(T monstre);
		protected abstract Collection<T> fonctionDExtraction(MonsterDatabase bdd);
	}

	private static abstract class RemplacementABaseDeFichier<T> extends Remplaceur<T> {
		/** Correspondance chaîne trouvée - chaîne à remplacer */
		private Map<String, String> map = new HashMap<>();

		protected final String extraire(String nom) {
			return map.get(nom);
		}
		
		public RemplacementABaseDeFichier(String fichier) {
			LecteurDeFichiersLigneParLigne.lectureFichierRessources(fichier, line -> {
				String[] chaines = LecteurDeFichiersLigneParLigne.diviser(line, 2);

				map.put(chaines[0], chaines[1]);
			});
		}
	}
	
	


	/**
	 * Chercheur et remplaceur de nom
	 */
	public static Remplaceur<Monstre> nom(String filename) {
		return new RemplacementABaseDeFichier<Monstre>(filename) {
			@Override
			public void remplacer(Monstre monstre) {
				String nomPresent = monstre.nom;
				String remplacement = extraire(nomPresent);

				if (remplacement != null) {
					monstre.nom = remplacement;
				}
			}

			@Override
			public Collection<Monstre> fonctionDExtraction(MonsterDatabase bdd) {
				return bdd.extractMonsters();
			}
		};
	}

	/**
	 * Chercheur et remplaceur de drop
	 */
	public static Remplaceur<Monstre> drop() {
		return new Remplaceur<Monstre>() {
			@Override
			public void remplacer(Monstre monstre) {
				String nomPresent = monstre.nomDrop;

				if (!nomPresent.equals("")) {
					int idObjet = Integer.parseInt(nomPresent);

					if (idObjet == 0) {
						monstre.nomDrop = "";
					} else {
						monstre.nomDrop = PROJET.extraireObjet(idObjet);
					}
				}
			}

			@Override
			public Collection<Monstre> fonctionDExtraction(MonsterDatabase bdd) {
				return bdd.extractMonsters();
			}
		};
	}

	/**
	 * Chercheur et remplaceur de zone
	 */
	public static Remplaceur<Combat> fond(String filename) {
		return new RemplacementABaseDeFichier<Combat>(filename) {
			@Override
			public void remplacer(Combat combat) {
				int i = 0;
				while (i != combat.fonds.size()) {
					String nomPresent = combat.fonds.get(i);
					String remplacement = extraire(nomPresent);

					if (remplacement != null) {
						if (combat.fonds.contains(remplacement)) {
							combat.fonds.remove(i);
							continue;
						} else {
							combat.fonds.set(i, remplacement);
						}
					}

					i++;
				}
			}

			@Override
			public Collection<Combat> fonctionDExtraction(MonsterDatabase bdd) {
				return bdd.extractBattles();
			}
		};
	}
}
