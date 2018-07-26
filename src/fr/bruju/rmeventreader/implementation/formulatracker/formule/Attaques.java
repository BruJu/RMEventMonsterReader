package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Statistique;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/*
 * TODO : Reorganiser la structure pour ne plus avoir besoin de faire des lambda dans des lambda qui bouclent sur
 * des map dans des listes dans la blockchain dans un datacenter situé au Pérou
 */
public class Attaques {
	public List<Attaque> liste = new ArrayList<>();





	public Collection<Attaque> getAttaques() {
		return liste;
	}

	public void apply(UnaryOperator<FormuleDeDegats> modificateurDeFormule) {
		modifierFormules((stat, formule) -> modificateurDeFormule.apply(formule));
	}

	public void transformerComposants(UnaryOperator<Composant> transformation) {
		apply(f -> transformationDeFormule(f, transformation));
	}

	/**
	 * Transforme la formule donnée en utilisant la fonction de transformation de composants fournie. Transforme toutes
	 * les conditions en enlevant les conditions devenues vraies. Si une devient fausse, renvoie null. Transforme la
	 * formule en utilisant la fonction de transformation.
	 * 
	 * @param formule Formule globale à transformer
	 * @param transformation Fonction de transformation d'un composant
	 * @return La formule sur laquelle on a appliqué la fonction de transformation. null si une des conditions était
	 *         devenue fausse.
	 */
	public static FormuleDeDegats transformationDeFormule(FormuleDeDegats formule,
			UnaryOperator<Composant> transformation) {
		Operator operateur = formule.operator;
		List<Condition> conditions = formule.conditions.stream().map(transformation)
				.map(composant -> (Condition) composant).filter(composant -> composant != CFixe.get(true))
				.collect(Collectors.toList());

		if (conditions.contains(CFixe.get(false))) {
			return null;
		}

		Valeur v = (Valeur) transformation.apply(formule.formule);

		return new FormuleDeDegats(operateur, conditions, v);
	}

	public void determinerAffichage(Function<Attaque, String> determineurDeChaine) {
		liste.stream().forEach(attaque -> attaque.chaineAAfficher = determineurDeChaine.apply(attaque));
	}

	public void determinerAffichage(Function<FormuleDeDegats, String> detChaine, Function<Attaque, String> detHeader) {
		determinerAffichage(attaque -> {
			List<String> sousChaines = new ArrayList<>();

			attaque.resultat.map.forEach((stat, listeDeFormules) -> {
				listeDeFormules.stream().map(formule -> detChaine.apply(formule)).filter(c -> !c.equals(""))

						.map(c -> getStatAffichage(stat) + " " + c).forEach(sousChaines::add);
			});

			if (sousChaines.isEmpty())
				return "";

			return detHeader.apply(attaque) + "\n" + sousChaines.stream().collect(Collectors.joining("\n"));
		});
	}

	private String getStatAffichage(Statistique stat) {
		return stat.possesseur.getNom() + "." + stat.nom;
	}

	public void modifierFormules(BiFunction<Statistique, FormuleDeDegats, FormuleDeDegats> transformation) {
		transformerListeDeformules((statistique, listeDeFormules) -> listeDeFormules.stream()
				.map(formule -> transformation.apply(statistique, formule)).filter(f -> f != null)
				.collect(Collectors.toList()));
	}

	public void transformerListeDeformules(
			BiFunction<Statistique, List<FormuleDeDegats>, List<FormuleDeDegats>> transformationListe) {
		liste.stream().map(attaque -> attaque.resultat).map(resultat -> resultat.map)
				.forEach(map -> map.forEach((statistique, listeDeFormules) -> map.put(statistique,
						transformationListe.apply(statistique, listeDeFormules))));

	}

	public void appliquerJusquaStabilite(BinaryOperator<FormuleDeDegats> unification) {
		transformerListeDeformules((stat, liste) -> Utilitaire.fusionnerJusquaStabilite(liste, unification));
	}

}
