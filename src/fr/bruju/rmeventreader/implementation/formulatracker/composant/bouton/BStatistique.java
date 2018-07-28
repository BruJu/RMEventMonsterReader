package fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantFeuille;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Statistique;

/**
 * Représente une propriété d'un personnage
 * 
 * @author Bruju
 *
 */
public final class BStatistique implements Bouton, ComposantFeuille {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Statistique visée */
	public final Statistique statistique; 

	/** Crée une variable pour la statistique donnée */
	public BStatistique(Statistique stat) {
		this.statistique = stat;
		
		if (statistique == null)
			throw new RuntimeException();
	}

	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String getString() {
		return statistique.possesseur.getNom() + "." + statistique.nom;
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof BStatistique)) {
			return false;
		}
		BStatistique castOther = (BStatistique) other;
		return Objects.equals(statistique, castOther.statistique);
	}

	@Override
	public int hashCode() {
		return Objects.hash("BStatistique", statistique.position);
	}
}
