package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.ComposantFeuille;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.visiteur.VisiteurDeComposants;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage.Statistique;

/**
 * Variable contenant une statistique
 * @author Bruju
 *
 */
public class VStatistique implements Valeur, ComposantFeuille {
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
		if (!(other instanceof VStatistique)) {
			return false;
		}
		VStatistique castOther = (VStatistique) other;
		return Objects.equals(statistique, castOther.statistique);
	}

	@Override
	public int hashCode() {
		return Objects.hash(statistique.position) * 10133;
	}
}