package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Statistique;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class Attaque {
	public final String nom;

	private Map<ModifStat, List<FormuleDeDegats>> resultat;
	private String chaineAAfficher;

	public Attaque(String nom, Map<ModifStat, List<FormuleDeDegats>> resultat) {
		this.nom = nom;
		this.resultat = resultat;
	}

	public String getChaineAAfficher() {
		return this.chaineAAfficher;
	}

	
	public void modifierFormule(UnaryOperator<FormuleDeDegats> transformation) {
		modifierFormule((cle, liste) -> transformation.apply(liste));
	}

	public void modifierFormule(BiFunction<ModifStat, FormuleDeDegats, FormuleDeDegats> transformation) {
		resultat.replaceAll((cle, liste) -> 
		 liste.stream()
				.map(formule -> transformation.apply(cle, formule))
				.collect(Collectors.toList())
			);
	}

	public void filtrer(Predicate<ModifStat> fonctionDeFiltre) {
		resultat.entrySet().removeIf(entry -> !fonctionDeFiltre.test(entry.getKey()));
	}
	
	
	
	
	
	
	
	public void detChaine(Function<Attaque, String> detHeader, Function<FormuleDeDegats, String> detChaine) {
		StringBuilder sb = new StringBuilder();
		sb.append(detHeader.apply(this))
		  .append("\n");
		
		resultat.forEach((modifStat, listeDeFormules) ->
			listeDeFormules.stream()
						   .map(formule -> detChaine.apply(formule))
						   .filter(chaine -> !chaine.isEmpty())
						   .forEach(chaine ->
						   			sb.append(getStatAffichage(modifStat.stat))
								      .append(" ")
								      .append(Utilitaire.getSymbole(modifStat.operateur))
								      .append(" ")
								      .append(chaine)
								      .append("\n"))
						); 
		
		this.chaineAAfficher = sb.toString();
	}
	

	private static String getStatAffichage(Statistique stat) {
		return stat.possesseur.getNom() + "." + stat.nom;
	}

	
	
	public void fusionner(BinaryOperator<Pair<ModifStat, FormuleDeDegats>> fonctionFusion) {		
		Map<Pair<String, Operator>, List<Pair<ModifStat, FormuleDeDegats>>> fusionnables = resultat.entrySet().stream()
				.flatMap(this::applatir).collect(Collectors.groupingBy( // Grouper par nom de stat x opérateur de modification
						paire -> new Pair<>(paire.getLeft().stat.nom, paire.getLeft().operateur)));
		
		fusionnables.replaceAll((cle, liste) -> Utilitaire.fusionnerJusquaStabilite(liste, fonctionFusion));

		Map<ModifStat, List<FormuleDeDegats>> groupes = new HashMap<>();

		fusionnables.values().stream().flatMap(liste -> liste.stream())
				.forEach(paire -> Utilitaire.mapAjouterElementAListe(groupes, paire.getLeft(), paire.getRight()));

		this.resultat = groupes;
	}


	private <K, V> Stream<Pair<K, V>> applatir(Map.Entry<K, List<V>> e) {
		return e.getValue().stream().map(formule -> new Pair<>(e.getKey(), formule));
	}
	



}
