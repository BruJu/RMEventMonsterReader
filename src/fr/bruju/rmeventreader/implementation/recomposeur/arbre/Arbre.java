package fr.bruju.rmeventreader.implementation.recomposeur.arbre;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.utilitaire.Triplet;

public class Arbre {
	private List<String> nomDesEtages;
	private Contenant racine;
	private BaseDeVariables base;
	
	public Arbre(List<String> nomDesEtages, Contenant racine, BaseDeVariables base) {
		this.nomDesEtages = nomDesEtages;
		this.racine = racine;
		this.base = base;
	}
	
	
	public List<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> getFruits() {
		return racine.recupererAlgo().collect(Collectors.toList());
	}
	
	
}
