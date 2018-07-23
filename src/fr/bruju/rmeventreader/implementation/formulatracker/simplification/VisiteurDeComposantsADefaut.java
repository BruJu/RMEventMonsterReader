package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;

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
