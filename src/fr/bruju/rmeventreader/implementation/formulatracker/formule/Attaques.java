package fr.bruju.rmeventreader.implementation.formulatracker.formule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Pair;

/*
 * TODO : Reorganiser la structure pour ne plus avoir besoin de faire des lambda dans des lambda qui bouclent sur
 * des map dans des listes dans la blockchain dans un datacenter situé au Pérou
 */
public class Attaques {
	private List<Attaque> liste = new ArrayList<>();

	public void transformerComposants(UnaryOperator<Composant> transformation) {
		transformerFormules(f -> generalisationDeLaTransformationDeComposants(f, transformation));
	}

	public void transformerFormules(UnaryOperator<FormuleDeDegats> modificateurDeFormule) {
		liste.stream().forEach(attaque -> attaque.modifierFormule(modificateurDeFormule));
	}

	public void modifierFormules(BiFunction<ModifStat, FormuleDeDegats, FormuleDeDegats> transformation) {
		liste.stream().forEach(attaque -> attaque.modifierFormule(transformation));
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
	public static FormuleDeDegats generalisationDeLaTransformationDeComposants(FormuleDeDegats formule,
			UnaryOperator<Composant> transformation) {
		List<Condition> conditions = formule.conditions
										.stream()
										.map(transformation)
										.map(composant -> (Condition) composant)
										.filter(composant -> composant != CFixe.get(true))
										.collect(Collectors.toList());

		if (conditions.contains(CFixe.get(false))) {
			return null;
		}

		Valeur v = (Valeur) transformation.apply(formule.formule);

		return new FormuleDeDegats(conditions, v);
	}

	
	
	
	
	
	
	
	

	public void determinerAffichage(Function<FormuleDeDegats, String> detChaine, Function<Attaque, String> detHeader) {
		liste.stream().forEach(attaque -> attaque.detChaine(detHeader, detChaine));
	}

	public void filterKeys(Predicate<ModifStat> fonctionDeFiltre) {
		liste.stream().forEach(attaque -> attaque.filtrer(fonctionDeFiltre));
	}

	public void fusionner(BinaryOperator<Pair<ModifStat, FormuleDeDegats>> fonctionFusion) {
		liste.stream().forEach(attaque -> attaque.fusionner(fonctionFusion));
	}

	public void filterAttaques(Predicate<Attaque> fonctionDeFiltre) {
		liste.removeIf(attaque -> !fonctionDeFiltre.test(attaque));
	}

	public void ajouterAttaque(Attaque attaque) {
		liste.add(attaque);
	}

	public void forEach(Consumer<Attaque> action) {
		liste.forEach(action);
	}
	

}
