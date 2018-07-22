package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VTernaire;

public interface VisiteurDeComposantsADefaut extends VisiteurDeComposants {

	void comportementParDefaut();
	
	@Override
	default void visit(BBase composant) {
		comportementParDefaut();
	}

	@Override
	default void visit(BConstant composant) {
		comportementParDefaut();
	}

	@Override
	default void visit(VAleatoire composant) {
		comportementParDefaut();
	}

	@Override
	default void visit(VBase composant) {
		comportementParDefaut();
	}

	@Override
	default void visit(VConstante composant) {
		comportementParDefaut();
	}

	@Override
	default void visit(VStatistique composant) {
		comportementParDefaut();
	}

	@Override
	default void visit(VCalcul vCalcul) {
		comportementParDefaut();
	}

	@Override
	default void visit(VTernaire vTernaire) {
		comportementParDefaut();
	}

	@Override
	default void visit(BTernaire bTernaire) {
		comportementParDefaut();
	}

	@Override
	default void visit(CArme cArme) {
		comportementParDefaut();
	}

	@Override
	default void visit(CSwitch cSwitch) {
		comportementParDefaut();
	}

	@Override
	default void visit(CVariable cVariable) {
		comportementParDefaut();
	}

}
