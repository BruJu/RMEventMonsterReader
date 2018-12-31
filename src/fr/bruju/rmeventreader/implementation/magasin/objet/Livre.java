package fr.bruju.rmeventreader.implementation.magasin.objet;

/**
 * Représente un livre (objet donnant des points dans une statistique quand on l'utilise pour la première fois)
 */
public class Livre extends Objet {
	/** Statistique donnée */
	public final StatistiqueDeLivre statistique;

	/**
	 * Crée un livre
	 * @param id ID de l'objet
	 * @param nom Nom du livre
	 * @param statistique Statistiques données
	 */
	public Livre(int id, String nom, StatistiqueDeLivre statistique) {
		super(id, nom);
		this.statistique = statistique;
	}

	@Override
	public String getString() {
		return id + " " + nom + " (£ " + statistique + ")";
	}

	/**
	 * Liste des statistiques données
	 */
	public enum StatistiqueDeLivre {
		Erudition,
		Troc,
		Force,
		Magie,
		Esprit,
		Persuasion,
		Vitalite,
		Dexterite,
		Commandement;

		/**
		 * Donne la statistique correspondant au numéro de stat donné
		 * @param index Le numéro de stat
		 * @return La statistique
		 */
		public static StatistiqueDeLivre get(int index) {
			if (index == 15) {
				return Livre.StatistiqueDeLivre.Commandement;
			} else {
				return Livre.StatistiqueDeLivre.values()[index - 1];
			}
		}
	}

}
