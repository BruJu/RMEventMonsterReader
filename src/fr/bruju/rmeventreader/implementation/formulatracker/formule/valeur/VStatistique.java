package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.Statistique;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

/**
 * Variable contenant une statistique
 * @author Bruju
 *
 */
public class VStatistique implements Valeur {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Statistique visée */
	public final Statistique statistique; 

	/** Crée une variable pour la statistique donnée */
	public VStatistique(Statistique stat) {
		this.statistique = stat;
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return statistique.possesseur.getNom() + "." + statistique;
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
		if (!(other instanceof VStatistique)) {
			return false;
		}
		VStatistique castOther = (VStatistique) other;
		return Objects.equals(statistique.position, castOther.statistique.position);
	}

	@Override
	public int hashCode() {
		return Objects.hash(statistique.position) * 10133;
	}
}
