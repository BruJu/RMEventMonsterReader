package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Statistique;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Un personnage (pouvant être seul ou un groupe de personnages).
 */
public abstract class Personnage {
	/** Association nom de statistique - objet la représentant */
	private Map<String, Statistique> statistiquesPossedees = new HashMap<>();

	/**
	 * Donne le nom du personnage
	 * @return Le nom du personnage
	 */
	public abstract String getNom();

	/**
	 * Ajoute tous les individus composant ce personnage au set
	 * @param set Le set de personnage à remplir
	 */
	public abstract void ajouterPersonnage(Set<Individu> set);


	/* ============================
	 * MANIPULATION DE STATISTIQUES
	 * ============================ */

	/**
	 * Ajoute la statistique au personnage
	 * @param statistique La statistique à ajouter
	 */
	public final void ajouterStatistique(Statistique statistique) {
		statistiquesPossedees.put(statistique.nom, statistique);
	}

	/**
	 * Effectue un traitement pour chaque statistique
	 * @param consumer Le traitement à exécuter
	 */
	protected final void forEachStatistique(Consumer<Statistique> consumer) {
		statistiquesPossedees.values().forEach(consumer);
	}

	/**
	 * <code>return statistiquesPossedees.get(nom);</code>
	 */
	public Statistique getStatistique(String nom) {
		return statistiquesPossedees.get(nom);
	}
}
