package fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantEtendu;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composants;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;

/**
 * Interface permettant de visiter les composants
 * 
 * @author Bruju
 *
 */
public interface VisiteurDeComposants {
	/* ========
	 * VISITEUR
	 * ======== */

	/**
	 * Visite de composant
	 */
	public default void visit(Composants composant) {
		composant.accept(this);
	}

	/* ========
	 * FEUILLES
	 * ======== */

	/**
	 * Visite de composant
	 */
	void visit(BBase composant);

	/**
	 * Visite de composant
	 */
	void visit(BConstant composant);

	/**
	 * Visite de composant
	 */
	void visit(VAleatoire composant);

	/**
	 * Visite de composant
	 */
	void visit(VBase composant);

	/**
	 * Visite de composant
	 */
	void visit(VConstante composant);

	/**
	 * Visite de composant
	 */
	void visit(VStatistique composant);

	/* ======
	 * CALCUL
	 * ====== */

	/**
	 * Visite de composant
	 */
	void visit(VCalcul composant);

	/* =========
	 * TERNAIRES
	 * ========= */

	/**
	 * Visite de composant
	 */
	void visit(VTernaire composant);

	/**
	 * Visite de composant
	 */
	void visit(BTernaire composant);

	/* ==========
	 * CONDITIONS
	 * ========== */

	/**
	 * Visite de composant
	 * @return 
	 */
	default void visit(ComposantEtendu composant) {
		throw new VisiteIllegale();
	}

	/**
	 * Visite de composant
	 * @return 
	 */
	default void visit(CFixe composant) {
		throw new VisiteIllegale();
	}

	/**
	 * Visite de composant
	 */
	void visit(CArme composant);

	/**
	 * Visite de composant
	 */
	void visit(CSwitch composant);

	/**
	 * Visite de composant
	 */
	void visit(CVariable composant);

}