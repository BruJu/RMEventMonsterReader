package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.utilitaire.Triplet;

/**
 * Element d'un arbre. Chaque élément est constitué d'un contenant et d'un contenu réel.
 * 
 * @author Bruju
 *
 */
public interface Contenu {
	/**
	 * Transforme les algorithmes avec la fonction de transformation donnée
	 * @param transformation La fonction de transformation
	 */
	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation);

	/**
	 * Ajoute un niveau à l'arbre avec la structure donnée
	 * @param transformation La structure permettant l'ajout d'un niveau
	 */
	public void ajouterUnNiveau(StructureDInjectionDeHeader transformation);

	/**
	 * Renvoie un flux contenant tous les algorithmes de ce contenu
	 * @return Un flux contenant tous les algorithmes de ce contenu
	 */
	public Stream<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> recupererAlgo();

	/**
	 * Transforme les algorithmes d'une liste pour les compacter
	 * @param classifier Un classifieur pour accélérer le traîtement
	 * @param unifieur La fonction d'unification
	 */
	void transformerListes(Function<Resultat, ?> classifier, BinaryOperator<Resultat> unifieur);
}
