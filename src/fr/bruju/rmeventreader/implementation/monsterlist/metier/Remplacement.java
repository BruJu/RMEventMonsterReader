package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.function.BiConsumer;
import java.util.function.Function;

/**
 * Classe permettant de remplacer des données dans les monstres
 * 
 * @author Bruju
 *
 */
public class Remplacement {
	/** Fonction de recherche de valeur dans un monstre */
	private final Function<Monstre, String> search;
	/** Fonction d'application de la chaîne au monstre */
	private final BiConsumer<Monstre, String> replace;

	/**
	 * Crée un remplaceur
	 */
	public Remplacement(Function<Monstre, String> search, BiConsumer<Monstre, String> replace) {
		this.search = search;
		this.replace = replace;
	}
	
	/**
	 * Donne la fonction de recherche
	 */
	public Function<Monstre, String> getSearch() {
		return search;
	}

	/**
	 * Donne la fonction de remplacement
	 */
	public BiConsumer<Monstre, String> getReplace() {
		return replace;
	}
	
	/**
	 * Chercheur et remplaceur de nom
	 */
	public static Remplacement nom() {
		return new Remplacement(m -> m.nom, (m, s) -> m.nom = s);
	}
	
	/**
	 * Chercheur et remplaceur de drop
	 */
	public static Remplacement drop() {
		return new Remplacement(m -> m.nomDrop, (m, s) -> m.nomDrop = s);
	}
}
