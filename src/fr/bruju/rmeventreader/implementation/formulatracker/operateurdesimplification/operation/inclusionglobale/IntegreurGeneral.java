package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.CreateurDeGestionnaire;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class IntegreurGeneral extends ConstructeurDeComposantR implements Integreur {
	private CreateurDeGestionnaire createur = new CreateurDeGestionnaire();
	
	private List<Condition> conditionsGeree = new ArrayList<>();
	private List<GestionnaireDeCondition> gestionnairesAssocies = new ArrayList<>();
	
	
	public void ajouterCondition(Condition condition) {
		
		
		
	}

	public Valeur integrer(Valeur formule) {
		return null;
	}

	public List<Condition> recupererConditions() {
		return conditionsGeree;
	}
	
	
	
	/* ============================================================================
	 * Utilisation de sous gestionnaires pour déléguer le traîtement des conditions
	 * ============================================================================ */

	@Override
	public void gestionnairePush(Condition condition, boolean reponse) {
	}

	@Override
	public void refuse(CArme cArme) {
	}

	@Override
	public void refuse(CSwitch cSwitch) {
	}

	@Override
	public void refuse(CVariable cVariable) {
	}

}
