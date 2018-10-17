package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.CreateurDeGestionnaire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;

/**
 * Transforme des formules en intégrant des solutions dans leurs valeurs
 * 
 * @author Bruju
 *
 */
public class IntegreurGeneral extends ConstructeurDeComposantsRecursif {
	/** Vrai si aucune conditions contradictoires n'ont été rencontrées dans les préconditions */
	private boolean estVivant = true;
	
	/** Créateur de gestionaires de conditions */
	private CreateurDeGestionnaire createur = new CreateurDeGestionnaire();
	
	/** Liste des préconditions gérées */
	private List<Condition> conditionsGeree = new ArrayList<>();
	/** Liste des gestionnaires de conditions */
	private List<GestionnaireDeCondition> gestionnairesAssocies = new ArrayList<>();

	/**
	 * Intègre la formule donnée
	 */
	public FormuleDeDegats integrer(FormuleDeDegats formuleBase) {
		int tailleCondition = conditionsGeree.size();
		
		formuleBase.conditions.forEach(this::ajouterCondition);
		
		
		Valeur formule = formuleBase.formule;
		
		formule = integrer(formule);
		
		// Formule de dégâts jamais explorée
		if (formule == null) {
			return null;
		}
		
		conditionsGeree = conditionsGeree.subList(tailleCondition, conditionsGeree.size()); 
		
		// Retour
		return new FormuleDeDegats(conditionsGeree, formule);
	}
	
	/**
	 * Ajoute une précondition au traitement des formules traitées par cet intégreur
	 * @param condition La condition à ajouter
	 */
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
			gestionnairesAssocies.add(createur.getGestionnaire(condition));
		}
	}

	/**
	 * Intègre la valeur donnée et renvoie sa valeur intégrée
	 */
	public Valeur integrer(Valeur formule) {
		if (!estVivant)
			return null;
		
		return (Valeur) traiter(formule);
	}

	
	/* ====================
	 * Composants condition
	 * ==================== */
	

	@Override
	protected Composant modifier(CArme cArme) {
		return traiterCondition(cArme, GestionnaireDeCondition::conditionArme);
	}

	@Override
	protected Composant modifier(CSwitch cSwitch) {
		
		Bouton nouveauBouton = (Bouton) traiter(cSwitch.interrupteur);
		
		if (nouveauBouton != cSwitch.interrupteur) {
			cSwitch = new CSwitch(nouveauBouton, cSwitch.valeur);
		}
		
		return traiterCondition(cSwitch, GestionnaireDeCondition::conditionSwitch);
	}

	@Override
	protected Composant modifier(CVariable cVariable) {
		
		Valeur gauche = (Valeur) traiter(cVariable.gauche);
		Valeur droite = (Valeur) traiter(cVariable.droite);
		
		if (gauche != cVariable.gauche || droite != cVariable.droite) {
			cVariable = new CVariable(gauche, cVariable.operateur, droite);
		}
		
		if (!(droite instanceof VConstante)) {
			return CFixe.get(true);
		}
		
		return traiterCondition(cVariable, GestionnaireDeCondition::conditionVariable);
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
	
	@Override
	protected void ternaireAvantVrai(Condition condition) {
		gestionnairesAssocies.add(createur.getGestionnaire(condition));
	}

	@Override
	protected void ternaireAvantFaux(Condition condition) {
		gestionnairesAssocies.add(createur.getGestionnaire(condition.revert()));
	}

	@Override
	protected void ternaireApres(Condition condition) {
		gestionnairesAssocies.remove(gestionnairesAssocies.size() - 1);
	}

	public void ajouterGestionnaires(List<GestionnaireDeCondition> gestionnaires) {
		this.gestionnairesAssocies.addAll(gestionnaires);
		
		//throw new UnsupportedOperationException();
	}
}
