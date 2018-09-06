package fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.division.Diviseur;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;
import fr.bruju.util.similaire.CollectorBySimilarity;

/**
 * Stocke la liste des formules liées à une attaque
 * 
 * @author Bruju
 *
 */
public class Attaque {
	/** Nom de l'attaque */
	public final String nom;

	/** Association statistique modifiée - liste des formules */
	private Map<ModifStat, List<FormuleDeDegats>> resultat;

	/**
	 * Initialise une attaque
	 * 
	 * @param nom Nom de l'attaque
	 * @param resultat Association entre statistiques modifiées et liste des formules
	 */
	public Attaque(String nom, Map<ModifStat, List<FormuleDeDegats>> resultat) {
		this.nom = nom;
		this.resultat = resultat;
	}

	/**
	 * Modifie les formules en appliquant la fonction fournie
	 * 
	 * @param transformation La fonction de transformation de formule
	 */
	void modifierFormule(UnaryOperator<FormuleDeDegats> transformation) {
		resultat.replaceAll((cle, liste) -> liste.stream().map(transformation).filter(formule -> formule != null)
				.collect(Collectors.toList()));
	}

	/**
	 * Modifie les formules de dégâts en utilisant la fonction de transformation fournie
	 * 
	 * @param transformation La fonction de transformation de formule
	 */
	void modifierFormule(BiFunction<String, FormuleDeDegats, FormuleDeDegats> transformation) {
		resultat.replaceAll((cle, liste) -> liste.stream()
				.map(formule -> transformation.apply(cle.stat.possesseur.getNom(), formule))
				.filter(formule -> formule != null).collect(Collectors.toList()));
	}

	/**
	 * Enlève les formules qui ne respectent pas le prédicat donné portant sur les modifications de statistiques.
	 * 
	 * @param fonctionDeFiltre Fonction permettant de déterminer si une modification de statistique nous interesse
	 */
	void filtrer(Predicate<ModifStat> fonctionDeFiltre) {
		resultat.entrySet().removeIf(entry -> !fonctionDeFiltre.test(entry.getKey()));
	}

	/**
	 * Fusionne les formules de dégâts selon une fonction de fusion
	 * 
	 * @param fonctionFusion La fonction de fusion
	 */
	void fusionner(BinaryOperator<Pair<ModifStat, FormuleDeDegats>> fonctionFusion) {

		CollectorBySimilarity<Pair<ModifStat, FormuleDeDegats>> collector = new CollectorBySimilarity<>(
				p -> p.getLeft().hashUnifiable(), (a, b) -> a.getLeft().estUnifiable(b.getLeft()));

		Collection<List<Pair<ModifStat, FormuleDeDegats>>> fusionnables = resultat.entrySet().stream()
				.flatMap(this::applatir).collect(collector).getMap().values();

		Map<ModifStat, List<FormuleDeDegats>> groupes = new HashMap<>();

		fusionnables.stream().map(liste -> Utilitaire.fusionnerJusquaStabilite(liste, fonctionFusion))
				.forEach(liste -> liste.forEach(
						paire -> Utilitaire.Maps.ajouterElementDansListe(groupes, paire.getLeft(), paire.getRight())));

		this.resultat = groupes;
	}

	/**
	 * Transforme des entrées dans une table de hashage en paires
	 */
	private <K, V> Stream<Pair<K, V>> applatir(Map.Entry<K, List<V>> e) {
		return e.getValue().stream().map(formule -> new Pair<>(e.getKey(), formule));
	}

	/**
	 * Applique la fonction donnée aux formules de dégâts, en réduisant avec la fonction de réduction si il y en a
	 * plusieurs et renvoie le résultat
	 * 
	 * @param fonctionFormule La fonction à appliquer à chaque formule
	 * @param reduction La fonction pour réduire le résultat de deux formules
	 * @return Le résultat de la fonction appliquée à toutes les formules
	 */
	public String returnForEach(TriFunction<String, ModifStat, FormuleDeDegats, String> fonctionFormule) {
		if (resultat.isEmpty()) {
			return "";
		}

		try {
			return resultat
						.entrySet()
					    .stream()
					    .flatMap(entry -> entry.getValue()
									   .stream()
									   .map(formule -> fonctionFormule.apply(nom, entry.getKey(), formule)))
					    .reduce((s1, s2) -> s1 + s2)
					    .get();
		} catch (NoSuchElementException e) {
			return "";
		}
	}

	/* ========
	 * DIVISION 
	 * ======== */

	public void diviser(Diviseur[] diviseurs) {
		List<Pair<ModifStat, FormuleDeDegats>> donnees = resultat.entrySet().stream().flatMap(this::applatir)
				.collect(Collectors.toList());

		resultat = reconstituerResultats(donnees, diviseurs);

	}

	private static Map<ModifStat, List<FormuleDeDegats>> reconstituerResultats(
			List<Pair<ModifStat, FormuleDeDegats>> donnees, Diviseur[] diviseurs) {
		Map<ModifStat, List<FormuleDeDegats>> resultat = new HashMap<>();

		donnees.stream().map(formule -> integrerDiviseurs(diviseurs, formule))
				.forEach(listeResultat -> listeResultat.forEach(nouvellePaire -> Utilitaire
						.Maps.ajouterElementDansListe(resultat, nouvellePaire.getLeft(), nouvellePaire.getRight())));

		return resultat;
	}

	private static List<Pair<ModifStat, FormuleDeDegats>> integrerDiviseurs(Diviseur[] diviseurs,
			Pair<ModifStat, FormuleDeDegats> groupementMSFDD) {

		ModifStat mS = groupementMSFDD.getLeft();
		FormuleDeDegats fdd = groupementMSFDD.getRight();

		List<Pair<GroupeDeConditions, FormuleDeDegats>> resultat = new ArrayList<>();
		resultat.add(new Pair<>(new GroupeDeConditions(), fdd));

		for (Diviseur diviseur : diviseurs) {
			resultat = resultat.stream()
					.map(existant -> new Pair<>(existant.getLeft(), diviseur.diviser(existant.getRight())))
					.flatMap(paireGrp_ListCondFormule -> paireGrp_ListCondFormule.getRight().stream()
							.map(condFormule -> new Pair<>(
									new GroupeDeConditions(paireGrp_ListCondFormule.getLeft(), 
											new ConditionAffichable(condFormule.getLeft(), diviseur.getFonction())),
									condFormule.getRight())))
					.filter(paireGrpFDD -> paireGrpFDD.getRight() != null)
					.collect(Collectors.toList());
		}

		return resultat.stream().map(r -> new Pair<>(new ModifStat(mS, r.getLeft()), r.getRight()))
				.collect(Collectors.toList());
	}

}
