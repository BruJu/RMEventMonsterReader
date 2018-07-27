package fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques;

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

/**
 * Stocke la liste des formules liées à une attaque
 * 
 * @author Bruju
 *
 */
public class Attaque {
	/** Nom de l'attaque */
	public final String nom;

	/** Association statistique modifiée - liste des formules*/
	private Map<ModifStat, List<FormuleDeDegats>> resultat;
	
	/** Chaîne à afficher lorsque SystemOut ou EcritureFichier est appelé*/
	private String chaineAAfficher;

	/**
	 * Initialise une attaque
	 * @param nom Nom de l'attaque
	 * @param resultat Association entre statistiques modifiées et liste des formules
	 */
	public Attaque(String nom, Map<ModifStat, List<FormuleDeDegats>> resultat) {
		this.nom = nom;
		this.resultat = resultat;
	}

	/**
	 * Donne la chaîne à afficher
	 * @return La chaîne à afficher
	 */
	public String getChaineAAfficher() {
		return this.chaineAAfficher;
	}

	
	/**
	 * Modifie les formules en appliquant la fonction fournie
	 * @param transformation La fonction de transformation de formule
	 */
	void modifierFormule(UnaryOperator<FormuleDeDegats> transformation) {
		modifierFormule((cle, liste) -> transformation.apply(liste));
	}

	/**
	 * Modifie les formules de dégâts en utilisant la fonction de transformation fournie
	 * @param transformation La fonction de transformation de formule
	 */
	void modifierFormule(BiFunction<ModifStat, FormuleDeDegats, FormuleDeDegats> transformation) {
		resultat.replaceAll((cle, liste) -> 
		 liste.stream()
				.map(formule -> transformation.apply(cle, formule))
				.filter(formule -> formule != null)
				.collect(Collectors.toList())
			);
	}

	/**
	 * Enlève les formules qui ne respectent pas le prédicat donné portant sur les modifications de statistiques.
	 * @param fonctionDeFiltre Fonction permettant de déterminer si une modification de statistique nous interesse
	 */
	void filtrer(Predicate<ModifStat> fonctionDeFiltre) {
		resultat.entrySet().removeIf(entry -> !fonctionDeFiltre.test(entry.getKey()));
	}
	
	/**
	 * Détermine l'affichage à faire selon la fonction d'affichage du nom de l'attaque et la fonction d'affichaque
	 * d'une formule
	 * @param detHeader La fonction donnant l'affichage à produire pour le nom de l'attaque
	 * @param detChaine La fonction donnant l'affichage à produire pour une formule de dégâts
	 */
	void detChaine(Function<Attaque, String> detHeader, Function<FormuleDeDegats, String> detChaine) {
		// TODO : prendre une BiFunction<ModifStat, FormuleDeDegats, String>
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
	
	/**
	 * Donne l'affichage du nom d'une statistique
	 */
	private static String getStatAffichage(Statistique stat) {
		return stat.possesseur.getNom() + "." + stat.nom;
	}

	
	/**
	 * Fusionne les formules de dégâts selon une fonction de fusion
	 * @param fonctionFusion La fonction de fusion
	 */
	void fusionner(BinaryOperator<Pair<ModifStat, FormuleDeDegats>> fonctionFusion) {		
		Map<Pair<String, Operator>, List<Pair<ModifStat, FormuleDeDegats>>> fusionnables = resultat.entrySet().stream()
				.flatMap(this::applatir).collect(Collectors.groupingBy(
						// Grouper par nom de stat x opérateur de modification
						// TODO : la demander à l'utilisateur
						paire -> new Pair<>(paire.getLeft().stat.nom, paire.getLeft().operateur)));
		
		fusionnables.replaceAll((cle, liste) -> Utilitaire.fusionnerJusquaStabilite(liste, fonctionFusion));

		Map<ModifStat, List<FormuleDeDegats>> groupes = new HashMap<>();

		fusionnables.values().stream().flatMap(liste -> liste.stream())
				.forEach(paire -> Utilitaire.mapAjouterElementAListe(groupes, paire.getLeft(), paire.getRight()));

		this.resultat = groupes;
	}


	/**
	 * Transforme des entrées dans une table de hashage en paires
	 */
	private <K, V> Stream<Pair<K, V>> applatir(Map.Entry<K, List<V>> e) {
		return e.getValue().stream().map(formule -> new Pair<>(e.getKey(), formule));
	}
}
