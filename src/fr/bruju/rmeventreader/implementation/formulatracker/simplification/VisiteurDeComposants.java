package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Composant;
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

public interface VisiteurDeComposants {

	/* ========
	 * VISITEUR
	 * ======== */

	public default void visit(Composant composant) {
		composant.accept(this);
	}
	
	/* ========
	 * FEUILLES
	 * ======== */

	void visit(BBase composant);

	void visit(BConstant composant);

	void visit(VAleatoire composant);

	void visit(VBase composant);

	void visit(VConstante composant);
	
	void visit(VStatistique composant);
	
	
	/* ======
	 * CALCUL
	 * ====== */

	void visit(VCalcul vCalcul);

	
	/* =========
	 * TERNAIRES
	 * ========= */

	void visit(VTernaire vTernaire);

	void visit(BTernaire bTernaire);
	
	
	/* ==========
	 * CONDITIONS
	 * ========== */
	
	void visit(CArme cArme);

	void visit(CSwitch cSwitch);

	void visit(CVariable cVariable);

}