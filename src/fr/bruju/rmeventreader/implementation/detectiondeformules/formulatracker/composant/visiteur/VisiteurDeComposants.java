package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.visiteur;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.etendu.ComposantEtendu;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.etendu.E_Borne;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.etendu.E_Entre;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VTernaire;

/**
 * Interface permettant de visiter les composants.
 * <p>
 * Par défaut, toutes les méthodes de visite des composants "normaux" doivent être implémentés. Il est possible
 * d'implémenter également des traitements spécifiques pour les composants dit étendus (préfixés par E_). En cas de non
 * implémentation, la méthode getComposantNormal sera appelée pour faire le traitement.
 * <p>
 * Le plus souvent, utiliser VisiteurRetourneur et ConstructeurDeComposantsRecursifs est plus simple.
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
	public default void visit(Composant composant) {
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
	void visit(BStatistique composant);

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

	/* ==================
	 * COMPOSANTS ETENDUS
	 * ================== */
	
	default void visiterComposantNormal(ComposantEtendu composant) {
		visit(composant.getComposantNormal());
	}
	
	/**
	 * Visite de composant
	 * 
	 * @return
	 */
	default void visit(E_Borne composant) {
		visiterComposantNormal(composant);
	}

	/**
	 * Visite de composant
	 * 
	 * @return
	 */
	default void visit(E_Entre composant) {
		visiterComposantNormal(composant);
	}
	
	/**
	 * Visite de composant
	 * 
	 * @return
	 */
	default void visit(CFixe composant) {
		throw new VisiteIllegale();
	}

}