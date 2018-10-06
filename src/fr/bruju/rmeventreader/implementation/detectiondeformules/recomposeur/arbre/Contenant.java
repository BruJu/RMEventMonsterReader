package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.arbre;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.utilitaire.Triplet;

/**
 * Un contenant est un pointeur vers un contenu. Cette structure permet de redéfinir le contenu d'un noeud sans
 * connaître le père et le modifier.
 * 
 * @author Bruju
 *
 */
public class Contenant implements Contenu {
	/** Contenu */
	private Contenu contenu;
	
	/** Crée un contenant */
	public Contenant() {}
	
	/** Crée un contenant contenant la liste des résultats donnés */
	public Contenant(List<Resultat> contenu) {
		this.contenu = new ListAlgo(this, contenu);
	}

	/**
	 * Modifie le contenu du contenant
	 * @param contenu Le contenu
	 */
	public void transformerContenu(Contenu contenu) {
		this.contenu = contenu;
	}
	
	/*
	 * Le contenant délègue au contenu le traîtement des changements demandés
	 */
	
	@Override
	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation) {
		contenu.transformerAlgorithmes(transformation);
	}

	@Override
	public Stream<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> recupererAlgo() {
		return contenu.recupererAlgo();
	}

	@Override
	public void ajouterUnNiveau(StructureDInjectionDeHeader transformation) {
		contenu.ajouterUnNiveau(transformation);
	}

	@Override
	public void transformerListes(Function<Resultat, ?> classifier, BinaryOperator<Resultat> unifieur) {
		contenu.transformerListes(classifier, unifieur);
	}
}
