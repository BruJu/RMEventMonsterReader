package fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;
import fr.bruju.rmeventreader.rmdechiffreur.modele.Comparateur;

import java.util.Objects;

/**
 * Valeur bornée par un autre valeur. Autrement dit, un minimum ou un maximum.
 * 
 * @author Bruju
 */
public final class E_Borne implements ComposantEtendu, Valeur {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Valeur bornée */
	public final Valeur valeur;
	/** Borne */
	public final Valeur borne;
	/** Si vrai, max. Sinon min entre les deux valeurs */
	public final boolean estBorneSup;

	/**
	 * Borne une valeur par une autre
	 * @param valeur Valeur à borner
	 * @param borne La borne
	 * @param estborneSup Si vrai, la borne est une borne supérieure. Sinon une borne inférieure
	 */
	public E_Borne(Valeur valeur, Valeur borne, boolean estborneSup) {
		this.valeur = valeur;
		this.borne = borne;
		this.estBorneSup = estborneSup;
	}
	
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return ((estBorneSup) ? "max" : "min") + "(" + valeur.getString() + ", " + borne.getString() + ")";
	}
	
	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Composant evaluationRapide() {
		return this;
	}
	
	@Override
	public Composant getComposantNormal() {
		return new VTernaire(new CVariable(valeur, estBorneSup ? Comparateur.SUP : Comparateur.INF, borne), valeur, borne);
	}
	
	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public int hashCode() {
		return Objects.hash(valeur, borne, estBorneSup);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof E_Borne) {
			E_Borne that = (E_Borne) object;
			return Objects.equals(this.valeur, that.valeur) && Objects.equals(this.borne, that.borne)
					&& this.estBorneSup == that.estBorneSup;
		}
		return false;
	}

	
	

}
