package fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques;

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

/**
 * Base de données des attaques connues
 * 
 * @author Bruju
 *
 */
public class Attaques {
	/** Liste des attaques */
	private List<Attaque> liste = new ArrayList<>();

	/* =========================
	 * MANIPULATION DES ATTAQUES
	 * ========================= */
	
	/**
	 * Transforme tous les composants des attaques
	 */
	public void transformerComposants(UnaryOperator<Composant> transformation) {
		transformerFormules(f -> generalisationDeLaTransformationDeComposants(f, transformation));
	}
	
	/**
	 * Transforme une formule de dégâts
	 */
	public void transformerFormules(UnaryOperator<FormuleDeDegats> modificateurDeFormule) {
		forEach(attaque -> attaque.modifierFormule(modificateurDeFormule));
	}

	/**
	 * Transforme une formule de dégâts en connaissant les statistiques qu'elle modifie
	 */
	public void modifierFormules(BiFunction<ModifStat, FormuleDeDegats, FormuleDeDegats> transformation) {
		forEach(attaque -> attaque.modifierFormule(transformation));
	}
	
	/**
	 * Détermine l'affichage avec une fonction donnat le header et une fonction donnant la chaîne des formules
	 */
	public void determinerAffichage(Function<FormuleDeDegats, String> detChaine, Function<Attaque, String> detHeader) {
		forEach(attaque -> attaque.detChaine(detHeader, detChaine));
	}

	/**
	 * Filtre les ModifStat ne répondant pas à un prédicat
	 */
	public void filterKeys(Predicate<ModifStat> fonctionDeFiltre) {
		forEach(attaque -> attaque.filtrer(fonctionDeFiltre));
	}

	/**
	 * Fusionne les formules en utilisant une fonction de fusion
	 */
	public void fusionner(BinaryOperator<Pair<ModifStat, FormuleDeDegats>> fonctionFusion) {
		forEach(attaque -> attaque.fusionner(fonctionFusion));
	}

	/**
	 * Applique une action pour chaque attaque 
	 */
	public void forEach(Consumer<Attaque> action) {
		liste.forEach(action);
	}

	/**
	 * Filtre les attaques ne répondant pas au prédicat
	 */
	public void filterAttaques(Predicate<Attaque> fonctionDeFiltre) {
		liste.removeIf(attaque -> !fonctionDeFiltre.test(attaque));
	}

	/**
	 * Ajoute une attaque à la liste
	 */
	public void ajouterAttaque(Attaque attaque) {
		liste.add(attaque);
	}
	

	/* =====
	 * AUTRE
	 * ===== */
		
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
}