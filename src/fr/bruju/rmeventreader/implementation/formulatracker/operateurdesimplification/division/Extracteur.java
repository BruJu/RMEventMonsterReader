package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division;

import java.util.Set;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Extrait des conditions présentes dans les composants
 * 
 * @author Bruju
 *
 */
public interface Extracteur extends VisiteurDeComposants {
	/**
	 * Extrait les conditions du composant donné et les ajoute dans l'ensemble de conditions
	 * @param composant Le composant dont il faut extraire des conditions
	 * @param conditions Les conditions extraites
	 */
	void extraire(Composant composant, Set<Condition> conditions);

	/* ========
	 * Feuilles
	 * ======== */
	@Override
	default void visit(BBase composant) {
		// Feuille
	}

	@Override
	default void visit(BConstant composant) {
		// Feuille
	}

	@Override
	default void visit(BStatistique composant) {
		// Feuille
	}

	@Override
	default void visit(VAleatoire composant) {
		// Feuille
	}

	@Override
	default void visit(VBase composant) {
		// Feuille
	}

	@Override
	default void visit(VConstante composant) {
		// Feuille
	}

	@Override
	default void visit(VStatistique composant) {
		// Feuille
	}

	/* ===================
	 * Composants composés
	 * =================== */
	@Override
	default void visit(VCalcul composant) {
		visit(composant.gauche);
		visit(composant.droite);
	}

	@Override
	default void visit(VTernaire composant) {
		visit(composant.condition);
		visit(composant.siVrai);
		visit(composant.siFaux);
	}

	@Override
	default void visit(BTernaire composant) {
		visit(composant.condition);
		visit(composant.siVrai);
		visit(composant.siFaux);
	}
}
