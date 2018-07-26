package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

/**
 * Résout les conditions dans les formules en utilisant les connaissances apportées par les préconditions. Ce procédé
 * est désigné comme étant l'intégration.
 * 
 * @author Bruju
 *
 */
public class MaillonIntegration implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerFormules(this::inclusionGenerale);
	}
	
	/**
	 * Converti la formule pour intégrer les conditions
	 * @param formuleBase La formule de départ
	 * @return La formule avec les conditions intégrées
	 */
	private FormuleDeDegats inclusionGenerale(FormuleDeDegats formuleBase) {
		// Récupération des valeurs
		List<Condition> conditions = formuleBase.conditions.stream().collect(Collectors.toList());
		Valeur formule = formuleBase.formule;
		IntegreurGeneral integreur = new IntegreurGeneral();
		
		// Integration des conditions
		conditions.sort(new ComparateurCondVar());
		for (Condition condition : conditions) {
			integreur.ajouterCondition(condition);
		}

		// Integration de la valeur
		formule = integreur.integrer(formule);
		conditions = integreur.recupererConditions();
		
		// Formule de dégâts jamais explorée
		if (formule == null) {
			return null;
		}
		
		// Retour
		return new FormuleDeDegats(conditions, formule);
	}
}
