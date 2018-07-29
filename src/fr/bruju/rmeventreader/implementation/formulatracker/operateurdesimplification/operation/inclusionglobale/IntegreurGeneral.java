package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.CreateurDeGestionnaire;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.inclusionglobale.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

public class IntegreurGeneral extends ConstructeurDeComposantsRecursif {
	private boolean estVivant = true;
	private CreateurDeGestionnaire createur = new CreateurDeGestionnaire();
	
	private List<Condition> conditionsGeree = new ArrayList<>();
	private List<GestionnaireDeCondition> gestionnairesAssocies = new ArrayList<>();

	
	public FormuleDeDegats integrer(FormuleDeDegats formuleBase) {
		int tailleCondition = conditionsGeree.size();
		
		formuleBase.conditions.stream().forEach(this::ajouterCondition);
		
		
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
		return traiterCondition(cArme, (g, c) -> g.conditionArme(c));
	}

	@Override
	protected Composant modifier(CSwitch cSwitch) {
		
		Bouton nouveauBouton = (Bouton) traiter(cSwitch.interrupteur);
		
		if (nouveauBouton != cSwitch.interrupteur) {
			cSwitch = new CSwitch(nouveauBouton, cSwitch.valeur);
		}
		
		return traiterCondition(cSwitch, (g, c) -> g.conditionSwitch(c));
	}

	@Override
	protected Composant modifier(CVariable cVariable) {
		
		Valeur gauche = (Valeur) traiter(cVariable.gauche);
		Valeur droite = (Valeur) traiter(cVariable.droite);
		
		if (gauche != cVariable.gauche || droite != cVariable.droite) {
			cVariable = new CVariable(gauche, cVariable.operateur, droite);
		}
		
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
	
	
	
	
	@Override
	protected Composant modifier(BTernaire boutonTernaire) {
		return ternaire(boutonTernaire, (c, v, v2) -> new BTernaire(c,v,v2));
	}

	@Override
	protected Composant modifier(VTernaire variableTernaire) {
		return ternaire(variableTernaire, (c, v, v2) -> new VTernaire(c,v,v2));
	}

	@SuppressWarnings("unchecked")
	private <T extends Composant> Composant ternaire(ComposantTernaire<T> ternaire,
			TriFunction<Condition, T, T, T> creation) {
		Condition cond = (Condition) traiter(ternaire.condition);
		
		if (cond instanceof CFixe) {
			new RuntimeException().printStackTrace();
			throw new RuntimeException();
		}

		gestionnairesAssocies.add(createur.getGestionnaire(cond));
		
		T vrai = (T) traiter(ternaire.siVrai);
		
		gestionnairesAssocies.set(gestionnairesAssocies.size() - 1, createur.getGestionnaire(cond.revert()));
		
		T faux = (T) traiter(ternaire.siFaux);
		
		gestionnairesAssocies.remove(gestionnairesAssocies.size() - 1);

		if (cond == ternaire.condition && faux == ternaire.siFaux && vrai == ternaire.siVrai) {
			return ternaire;
		} else {
			return creation.apply(cond, vrai, faux);
		}
	}


}
