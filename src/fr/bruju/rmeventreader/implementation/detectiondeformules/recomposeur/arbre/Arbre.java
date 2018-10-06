package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.arbre;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.visiteur.template.VisiteurConstructeur;
import fr.bruju.rmeventreader.utilitaire.Triplet;

/**
 * Représentation d'un arbre de formules. Chaque branche possède la même hauteur.
 * 
 * @author Bruju
 *
 */
public class Arbre {
	/** Nom des différents étages */
	private List<String> nomDesEtages;
	/** Racine */
	public final Contenant racine;
	/** Base de variables */
	public final BaseDeVariables base;
	
	/**
	 * Crée un arbre de formules
	 * @param nomDesEtages Liste des étages initiaux
	 * @raram racine Conteneur de la racine
	 * @param base Base de variables
	 */
	public Arbre(List<String> nomDesEtages, Contenant racine, BaseDeVariables base) {
		this.nomDesEtages = nomDesEtages;
		this.racine = racine;
		this.base = base;
	}
	
	/**
	 * Donne la liste des algorithmes
	 * @return Des triplets Groupes de Prérequis, Statistique touchée, Algorithme
	 */
	public List<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> getFruits() {
		return racine.recupererAlgo().collect(Collectors.toList());
	}

	/**
	 * Transforme les algorithmes en appliquant le constructeur donné
	 * @param constructeur Le constructeur d'algorithmes
	 * @return this
	 */
	public Arbre transformerAlgorithmes(VisiteurConstructeur constructeur) {
		racine.transformerAlgorithmes(algo -> (Algorithme) constructeur.traiter(algo));
		return this;
	}
	
	/**
	 * Ajout un niveau à l'arbre par rapport aux algorithmes présents
	 * @param transformation
	 * @return this
	 */
	public Arbre pimp(StructureDInjectionDeHeader transformation) {
		nomDesEtages.add(transformation.getNom());
		racine.ajouterUnNiveau(transformation);
		return this;
	}
	
	/**
	 * Transforme les listes avec la fonction donnée
	 * @param classifier Le classifier qui permet de séparer les objets pour accélérer le traitement
	 * @param fonctionFusion La fonction de fusion de deux Resultats, retourne null si la fusion est impossible 
	 * @return this
	 */
	private Arbre transformerListes(Function<Resultat, Object> classifier, BinaryOperator<Resultat> fonctionFusion) {
		racine.transformerListes(classifier, fonctionFusion);
		return this;
	}

	/**
	 * Transforme les listes avec la fonction donnée
	 * @param fonctionFusion La fonction de fusion de deux Resultats, retourne null si la fusion est impossible 
	 * @return this
	 */
	public Arbre unifier(BinaryOperator<Resultat> fonctionFusion) {
		return transformerListes(r -> Boolean.TRUE, fonctionFusion);
	}
}
