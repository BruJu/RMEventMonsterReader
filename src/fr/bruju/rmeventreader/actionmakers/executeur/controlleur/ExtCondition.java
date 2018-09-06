package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondArgent;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondChrono;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondDirection;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondEventDemarreParAppui;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondHerosAAuMoinsHp;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondHerosAPourNom;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondHerosAStatut;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondHerosDansLEquipe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondHerosNiveauMin;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondHerosPossedeObjet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondHerosPossedeSort;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondMusiqueJoueePlusDUneFois;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondObjet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondVehiculeUtilise;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Variable;

public interface ExtCondition {
	public interface $ extends ExtCondition, ModuleExecFlot {
		@Override
		default boolean Flot_si(Condition condition) {
			return $(condition);
		}
	}
	
	public interface $$ extends ExecuteurInstructions, $ {
		@Override
		default ModuleExecFlot getExecFlot() {
			return this;
		}
	}


	public default boolean $(Condition condition) {
		return condition.accept(this);
	}

	public default boolean herosStatut(CondHerosAStatut condHerosAStatut) {
		return false;
	}

	public default boolean herosObjet(CondHerosPossedeObjet condHerosPossedeObjet) {
		return false;
	}

	public default boolean herosSort(CondHerosPossedeSort condHerosPossedeSort) {
		return false;
	}

	public default boolean herosVivant(CondHerosAAuMoinsHp condHerosAAuMoinsHp) {
		return false;
	}

	public default boolean herosNiveau(CondHerosNiveauMin condHerosNiveauMin) {
		return false;
	}

	public default boolean herosNomme(CondHerosAPourNom condHerosAPourNom) {
		return false;
	}

	public default boolean herosPresent(CondHerosDansLEquipe condHerosDansLEquipe) {
		return false;
	}

	public default boolean musiqueABoucle(CondMusiqueJoueePlusDUneFois condMusiqueJoueePlusDUneFois) {
		return false;
	}

	public default boolean eventDemarreParAppui(CondEventDemarreParAppui condEventDemarreParAppui) {
		return false;
	}

	public default boolean vehicule(CondVehiculeUtilise condVehiculeUtilise) {
		return false;
	}

	public default boolean direction(CondDirection condDirection) {
		return false;
	}

	public default boolean objet(CondObjet condObjet) {
		return false;
	}

	public default boolean argent(CondArgent condArgent) {
		return false;
	}

	public default boolean chrono(CondChrono condChrono) {
		return false;
	}

	public default boolean interrupteur(CondInterrupteur condInterrupteur) {
		return false;
	}
	
	public default boolean variable(CondVariable condVariable) {
		if (condVariable.valeurDroite instanceof ValeurFixe) {
			return variableFixe(condVariable.variable, condVariable.comparateur,
					(ValeurFixe) condVariable.valeurDroite);
		} else {
			return variableVariable(condVariable.variable, condVariable.comparateur,
					(Variable) condVariable.valeurDroite);
		}
	}

	public default boolean variableVariable(int variable, Comparateur comparateur, Variable droite) {
		return false;
	}

	public default boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		return false;
	}
}
