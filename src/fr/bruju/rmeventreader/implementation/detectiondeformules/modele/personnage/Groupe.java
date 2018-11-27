package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Statistique;

import java.util.Set;
import java.util.StringJoiner;

/**
 * Un groupe est un rassemblement de personnages
 */
public class Groupe extends Personnage {
	/** Liste des personnages représentés par le groupe */
	private Set<Individu> personnage;
	/** Le nom du groupe */
	private String nom;

	/**
	 * Crée un groupe
	 * @param nom Le nom du groupe
	 * @param personnage La liste des personnages le composant
	 */
	public Groupe(String nom, Set<Individu> personnage) {
		this.nom = nom;
		this.personnage = personnage;
	}

	@Override
	public String getNom() {
		return nom;
	}

	@Override
	public void ajouterPersonnage(Set<Individu> set) {
		set.addAll(personnage);
	}

	/**
	 * Ajoute toutes les statistiques du personnage donné à ce groupe de personnage
	 * @param personnage Le personnage dont on souhaite exploiter les statistiques
	 * @param baseDePersonnages La base de personnages qui permet de connaître les numéros de variable utiliables
	 */
	public void ajouterNouvellesStatistiquesIssuesDe(Personnage personnage, BaseDePersonnages baseDePersonnages) {
		personnage.forEachStatistique(statistique -> {
			if (this.getStatistique(statistique.nom) == null) {
				int idVariable;

				if (statistique.idVariable < 0) {
					idVariable = baseDePersonnages.getNouvelInterrupteur();
				} else {
					idVariable = baseDePersonnages.getNouvelleVariable();
				}

				ajouterStatistique(new Statistique(idVariable, this, statistique.nom));
			}
		});
	}

	/**
	 * Donne le nom composé pour un ensemble d'individu
	 * @param personnage L'ensemble des individus
	 * @return En général si le set contient les personnages nommés a, b, ..., z, renvoie "a/b/.../z". Si tous les
	 * personnages ont un nom commencant par Monstre, renvoie Monstre suivi de tous les suffixes de Monstre trouvé.
	 * Remarque : Renvoie Monstre si le set est vide (ce comportement n'étant ni désiré ni génant).
	 */
	public static String definirNom(Set<Individu> personnage) {
		String versionMonstre = "Monstre";

		StringJoiner sj = new StringJoiner("/");

		for (Individu individu : personnage) {
			String nom = individu.getNom();

			sj.add(nom);

			if (nom.startsWith("Monstre") && versionMonstre != null) {
				versionMonstre += nom.substring(7);
			} else {
				versionMonstre = null;
			}
		}

		return versionMonstre != null ? versionMonstre : sj.toString();
	}
}
