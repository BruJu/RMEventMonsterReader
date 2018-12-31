package fr.bruju.rmeventreader.implementation.magasin.objet;

import fr.bruju.rmeventreader.implementation.magasin.caracteristique.LivresMenus;
import fr.bruju.rmeventreader.implementation.magasin.caracteristique.ListeDArmes;
import fr.bruju.rmeventreader.implementation.magasin.caracteristique.ListeEquipabilite;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Classe regroupant les connaissance sur les objets, permettant d'instancier des objets avec des connaissances
 * supplémentaires extraites de divers évènements
 */
public class ObjetsCrees {
	private Map<Integer, Objet> objets = new HashMap<>();

	private Map<Integer, Integer> armes = ListeDArmes.lireBonusAttaque();
	private Map<Integer, Set<Integer>> equipables = ListeEquipabilite.chercherObjetsEquipables();
	private Map<Integer, Livre.StatistiqueDeLivre> livres = LivresMenus.lireLesStatistiques();


	public Objet getObjet(int id) {
		Objet objet = objets.get(id);

		if (objet == null) {
			String nom = PROJET.extraireObjet(id);

			if (equipables.containsKey(id)) {
				// Equipement
				Set<Integer> equipablePar = equipables.get(id);
				StringJoiner personnages = new StringJoiner(" / ");

				for (Integer idHeros : equipablePar) {
					personnages.add(PROJET.extraireHeros(idHeros));
				}

				Integer bonusAttaque = armes.get(id);

				if (bonusAttaque == null || bonusAttaque == 0) {
					objet = new ObjetAvecDescription(id, nom, personnages.toString());
				} else {
					String bonusAttaqueStr = " ; Attaque +" + bonusAttaque;
					objet = new ObjetAvecDescription(id, nom, personnages.toString() + bonusAttaqueStr);
				}
			} else if (livres.containsKey(id)) {
				Livre.StatistiqueDeLivre statistiqueAugmentee = livres.get(id);
				objet = new Livre(id, nom, statistiqueAugmentee);
			} else {
				// Objet sans effet à afficher
				objet = new Objet(id, nom);
			}

			objets.put(id, objet);
		}

		return objet;
	}


	public static class ObjetAvecDescription extends Objet {
		private final String complement;

		public ObjetAvecDescription(int id, String nom, String complement) {
			super(id, nom);
			this.complement = complement;
		}

		@Override
		public String getString() {
			return super.getString() + " (" + complement + ")";
		}
	}
}
