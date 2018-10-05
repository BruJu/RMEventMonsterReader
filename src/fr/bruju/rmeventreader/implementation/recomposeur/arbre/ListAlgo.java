package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Triplet;

/**
 * Liste d'algorithmes
 * 
 * @author Bruju
 *
 */
public class ListAlgo implements Contenu {
	/** Contenant père */
	public final Contenant contenant;
	/** Liste des algorithmes connus */
	private List<Resultat> contenu;

	/**
	 * Crée une liste d'algorithme stockable dans un arbre
	 * @param contenant Le contenant père
	 * @param contenu La liste des algorithmes à stocker
	 */
	public ListAlgo(Contenant contenant, List<Resultat> contenu) {
		this.contenant = contenant;
		this.contenu = contenu;
	}

	@Override
	public void transformerAlgorithmes(UnaryOperator<Algorithme> transformation) {
		contenu.replaceAll(res -> new Resultat(res.stat, transformation.apply(res.algo)));
	}

	@Override
	public void ajouterUnNiveau(StructureDInjectionDeHeader transformation) {
		Builder builder = new Builder();
		
		contenu.stream().forEach(resultat -> builder.ajouter(resultat.stat,
				transformation.degrouper(resultat.stat, resultat.algo)));
		
		contenant.transformerContenu(builder.build(contenant));
	}

	@Override
	public Stream<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> recupererAlgo() {
		return contenu.stream().map(r -> new Triplet<>(new ArrayList<>(), r.stat, r.algo));
	}

	@Override
	public void transformerListes(Function<Resultat, ?> classifier, BinaryOperator<Resultat> unifieur) {
		contenu = contenu.stream()
						.collect(Collectors.groupingBy(classifier, Collectors.toList()))
						.values().stream()
						.map(liste -> Utilitaire.fusionnerJusquaStabilite(liste, unifieur))
						.flatMap(liste -> liste.stream())
						.collect(Collectors.toList());
	}
	
	/**
	 * Monte un étage à partir d'un incrémentateur de header
	 * 
	 * @author Bruju
	 *
	 */
	private static class Builder {
		/** Associations */
		private Map<GroupeDeConditions, List<Resultat>> map = new HashMap<>();

		/** Crée l'étage */
		public Etage build(Contenant contenant) {
			return new Etage(contenant, 
			map.entrySet().stream().map(entry -> new Pair<>(entry.getKey(), new Contenant(entry.getValue())))
				.collect(Pair.toMap()));
		}

		/** Ajoute un résultat issus d'un Incrementateur de Header */
		public Builder ajouter(Statistique stat, Iterable<Pair<GroupeDeConditions, Algorithme>> inc) {
			inc.forEach(paire -> ajouter(paire.getLeft(), new Resultat(stat, paire.getRight())));
			return this;
		}

		/** Ajoute un résultat avec le groupe donné et le résultat */
		private void ajouter(GroupeDeConditions groupe, Resultat resultat) {
			Utilitaire.Maps.getX(map, groupe, ArrayList::new).add(resultat);
		}
	}
}
