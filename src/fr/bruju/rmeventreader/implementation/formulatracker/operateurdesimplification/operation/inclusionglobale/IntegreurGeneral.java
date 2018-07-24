package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.CreateurDeGestionnaire;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

public class IntegreurGeneral extends ConstructeurDeComposantR implements Integreur {
	private boolean estVivant = true;
	private CreateurDeGestionnaire createur = new CreateurDeGestionnaire();
	
	private List<Condition> conditionsGeree = new ArrayList<>();
	private List<GestionnaireDeCondition> gestionnairesAssocies = new ArrayList<>();
	
	


	public void ajouterCondition(Condition condition) {
		if (!estVivant)
			return;
		
		condition = (Condition) traiter(condition);
		Boolean fixe = CFixe.identifier(condition);
		
		if (fixe != null) {
			if (fixe) {
				// Cette condition est plus générale qu'une autre. On ne l'ajoute pas à la liste
			} else {
				// Deux conditions contradictoires sont présentes
				estVivant = false;
			}
		} else {
			conditionsGeree.add(condition);
			gestionnairesAssocies.add(createur.getGestionnaire(this, condition));
		}
	}

	public Valeur integrer(Valeur formule) {
		if (!estVivant)
			return null;
		
		return (Valeur) get(formule);
	}

	public List<Condition> recupererConditions() {
		if (!estVivant)
			return null;
		
		return conditionsGeree;
	}
	
	/* ====================
	 * Composants condition
	 * ==================== */
	

	@Override
	protected Composant traiter(CArme cArme) {
		return traiterCondition(cArme, (g, c) -> g.conditionArme(c));
	}

	@Override
	protected Composant traiter(CSwitch cSwitch) {
		return traiterCondition(cSwitch, (g, c) -> g.conditionSwitch(c));
	}

	@Override
	protected Composant traiter(CVariable cVariable) {
		return traiterCondition(cVariable, (g, c) -> g.conditionVariable(c));
	}
	
	@SuppressWarnings("unchecked")
	private <T extends Condition> Composant traiterCondition(T condition,
			BiFunction<GestionnaireDeCondition, T, Condition> funcTraitement) {
		T condActuelle = condition;

		for (GestionnaireDeCondition gestionnaire : gestionnairesAssocies) {			
			Condition condRecue = funcTraitement.apply(gestionnaire, condActuelle);
			if (condRecue instanceof CFixe) {
				return condRecue;
			}
			
			condActuelle = (T) condRecue;
		}
		
		return condActuelle;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	protected <T extends Composant> Composant visitePar(ComposantTernaire<T> ternaire,
			TriFunction<Condition, T, T, T> creation) {
		Condition cond = (Condition) traiter(ternaire.condition);

		Boolean fixe = CFixe.identifier(cond);
		
		if (fixe != null) {
			return fixe ? traiter(ternaire.siVrai) : traiter(ternaire.siFaux);
		} else {
			gestionnairesAssocies.add(createur.getGestionnaire(this, cond));
			
			T vrai = (T) traiter(ternaire.siVrai);
			
			gestionnairesAssocies.set(gestionnairesAssocies.size() - 1, createur.getGestionnaire(this, cond.revert()));
			
			T faux = (ternaire.siFaux != null) ? (T) traiter(ternaire.siFaux) : null;
			
			gestionnairesAssocies.remove(gestionnairesAssocies.size() - 1);

			if (cond == ternaire.condition && faux == ternaire.siFaux && vrai == ternaire.siVrai) {
				return ternaire;
			} else {
				return creation.apply(cond, vrai, faux);
			}
		}
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
