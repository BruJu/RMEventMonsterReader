package fr.bruju.rmeventreader.implementation.formulatracker.composant.condition;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

import java.util.Objects;

/**
 * Condition portant sur une variable
 * 
 * @author Bruju
 *
 */
public class CVariable implements Condition {
	/* =========
	 * COMPOSANT
	 * ========= */

	/** Opérande de gauche */
	public final Valeur gauche;
	/** Opérateur de comparaison */
	public final Comparateur operateur;
	/** Opérande de droite */
	public final Valeur droite;

	/**
	 * Construit une condition portant sur des valeurs
	 * 
	 * @param gauche Opérande de gauche
	 * @param operateur2 Opérateur de test
	 * @param vDroite Opérande de droite
	 */
	public CVariable(Valeur gauche, Comparateur operateur2, Valeur vDroite) {
		this.gauche = gauche;
		this.operateur = operateur2;
		this.droite = vDroite;
	}

	@Override
	public Condition revert() {
		return new CVariable(gauche, operateur.revert(), droite);
	}

	/* ===============
	 * IMPLEMENTATIONS
	 * =============== */

	@Override
	public String getString() {
		return gauche.getString() + " " + operateur.symbole + " " + droite.getString();
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public Composant evaluationRapide() {
		if (gauche instanceof VConstante && droite instanceof VConstante) {
			VConstante cstg = (VConstante) gauche;
			VConstante cstd = (VConstante) droite;
			
			return CFixe.get(operateur.test(cstg.valeur, cstd.valeur));
		}
		
		return this;
	}
	
	
	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof CVariable)) {
			return false;
		}
		CVariable castOther = (CVariable) other;
		return Objects.equals(gauche, castOther.gauche) && Objects.equals(operateur, castOther.operateur)
				&& Objects.equals(droite, castOther.droite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gauche, operateur, droite);
	}

}
