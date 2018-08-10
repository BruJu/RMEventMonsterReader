package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurConstructeur;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.utilitaire.Triplet;

public class Arbre {
	@SuppressWarnings("unused")
	private List<String> nomDesEtages;
	public final Contenant racine;
	public final BaseDeVariables base;
	
	public Arbre(List<String> nomDesEtages, Contenant racine, BaseDeVariables base) {
		this.nomDesEtages = nomDesEtages;
		this.racine = racine;
		this.base = base;
	}
	
	public List<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> getFruits() {
		return racine.recupererAlgo().collect(Collectors.toList());
	}

	public Arbre transformerAlgorithmes(VisiteurConstructeur constructeur) {
		racine.transformerAlgorithmes(algo -> (Algorithme) constructeur.traiter(algo));
		return this;
	}
	
	public Arbre pimp(StructureDInjectionDeHeader transformation) {
		racine.ajouterUnNiveau(transformation);
		return this;
	}
	
	public Arbre transformerListes(Function<Resultat, Object> classifier, BinaryOperator<Resultat> fonctionFusion) {
		racine.transformerListes(classifier, fonctionFusion);
		return this;
	}

	public Arbre unifier(BinaryOperator<Resultat> fonctionFusion) {
		return transformerListes(r -> Boolean.TRUE, fonctionFusion);
	}
}