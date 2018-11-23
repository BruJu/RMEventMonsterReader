package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.division;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.IntegreurGeneral;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.util.Pair;

/**
 * Objet permettant de projeter une formule de dégâts en une liste de paires condition - formule
 * 
 * @author Bruju
 *
 */
public class Diviseur {
	/** Stratégie de division */
	private StrategieDeDivision strategie;

	/**
	 * Crée un diviseur appliquant la stratégie donnée
	 * 
	 * @param strategie La stratégie de division
	 */
	public Diviseur(StrategieDeDivision strategie) {
		this.strategie = strategie;
	}

	/**
	 * Divise la formule en ne liste de paires conditions - formules en utilisant ce diviseur
	 * @param formule La formule à projeter
	 * @return La liste des formules issues de la division de la formule donnée avec la condition utilisée. Peut
	 * renvoyer <null, formule> si aucune division n'est nécessaire.
	 */
	public List<Pair<Condition, FormuleDeDegats>> diviser(FormuleDeDegats formule) {
		Set<Condition> conditions = new HashSet<>();

		formule.conditions.forEach(condition -> strategie.getExtracteur().extraire(condition, conditions));
		strategie.getExtracteur().extraire(formule.formule, conditions);

		if (conditions.isEmpty()) {
			ArrayList<Pair<Condition, FormuleDeDegats>> listeReponse = new ArrayList<>();
			listeReponse.add(new Pair<>(null, formule));
			return listeReponse;
		} else {
			return conditions.stream().map(condition -> integrer(conditions, condition, formule))
					.collect(Collectors.toList());
		}
	}
	
	/**
	 * Intègre la condition dans la formule
	 * @param conditions L'ensemble des conditions extraites
	 * @param condition La condition utilisée pour cette intégration
	 * @param formule La formule de base
	 * @return Une paire <Condition, Formule intégrée par rapport à la condition donnée>
	 */
	private Pair<Condition, FormuleDeDegats> integrer(Set<Condition> conditions, Condition condition,
			FormuleDeDegats formule) {
		List<GestionnaireDeCondition> gestionnaires = strategie.getGestionnaires(condition, conditions);

		IntegreurGeneral integreur = new IntegreurGeneral();

		integreur.ajouterGestionnaires(gestionnaires);

		return new Pair<>(condition, integreur.integrer(formule));
	}

	public Function<Condition, String> getFonction() {
		return strategie.getFonctionDAffichage();
	}
}
