package fr.bruju.rmeventreader.implementation.magasin;

import fr.bruju.rmeventreader.implementation.random.ListeDArmes;
import fr.bruju.rmeventreader.implementation.random.ListeEquipabilite;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class ObjetsCrees {
	private Map<Integer, Objet> objets = new HashMap<>();

	private Map<Integer, Integer> armes = ListeDArmes.lireBonusAttaque();
	private Map<Integer, Set<Integer>> equipables = ListeEquipabilite.chercherObjetsEquipables();


	public Objet getObjet(int id) {
		Objet objet = objets.get(id);

		if (objet == null) {
			String nom = PROJET.extraireObjet(id);

			Set<Integer> equipablePar = equipables.get(id);

			if (equipablePar == null) {
				objet = new Objet(id, nom);
			} else {
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
			}
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
