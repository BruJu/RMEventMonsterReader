package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.detectiondeformules.AttaqueALire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.division.Diviseur;
import fr.bruju.util.Pair;
import fr.bruju.util.function.TriFunction;

/**
 * Base de données des attaques connues
 * 
 * @author Bruju
 *
 */
public class Attaques {
	/** Liste des attaques */
	private List<Attaque> liste = new ArrayList<>();
	/** Affichage à produire */
	private String affichage;
	/** Liste des informations complémentaires dans ModifStat */
	private List<String> groupesSupplementaires = new ArrayList<>();

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
	 * Transforme une formule de dégâts en connaissant le personnage qui est modifié
	 */
	public void modifierFormules(BiFunction<String, FormuleDeDegats, FormuleDeDegats> transformation) {
		forEach(attaque -> attaque.modifierFormule(transformation));
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



	public void appliquerDiviseur(String titre, Diviseur[] diviseurs) {
		forEach(attaque -> attaque.diviser(diviseurs));
		
		groupesSupplementaires.add(titre);
	}
	

	/* =========
	 * AFFICHAGE
	 * ========= */
	public String getAffichage() {
		return this.affichage;
	}
	
	/**
	 * Détermine l'affichage à faire en fonction des fonctions données
	 * @ param affichage affichageHeader Fonction donant le header à produire en fonction de la liste des titres des
	 * sous groupes dans ModifStat
	 * @param affichageHeaderAttaque Fonction donnant le header à produire pour chaque attaque en fonction du nom
	 * @param affichageFormule Fonction donnant la liste à produire pour chaque formules de dégâts en fonction du nom
	 * de l'attaque, de la statistique modifiée et de la formule de dégâts
	 * @param affichageFooterAttaque Fonction donnant le footer à produire à la fin de chaque attaque en fonction du nom
	 */
	public void determinerAffichageAttaques(
			Function<List<String>, String> affichageHeader,
			Function<AttaqueALire, String> affichageHeaderAttaque,
			TriFunction<AttaqueALire, ModifStat, FormuleDeDegats, String> affichageFormule,
			Function<AttaqueALire, String> affichageFooterAttaque) {
		StringBuilder sb = new StringBuilder();
		
		sb.append(affichageHeader.apply(groupesSupplementaires));
		
		forEach(attaque -> {
			sb.append(affichageHeaderAttaque.apply(attaque.noms));
			sb.append(attaque.returnForEach(affichageFormule));
			sb.append(affichageFooterAttaque.apply(attaque.noms));
		});
		
		this.affichage = sb.toString();
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
										.map(composant -> (Condition) composant.evaluationRapide())
										.filter(composant -> composant != CFixe.get(true))
										.collect(Collectors.toList());

		if (conditions.contains(CFixe.get(false))) {
			return null;
		}

		Valeur v = (Valeur) transformation.apply(formule.formule);

		return new FormuleDeDegats(conditions, v);
	}

}
