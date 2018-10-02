package fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur;

import fr.bruju.rmeventreader.actionmakers.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;
import java.util.Objects;

/**
 * Valeur qui est le calcul entre deux valeurs
 * 
 * @author Bruju
 */
public class VCalcul implements Valeur {
	/* =========
	 * COMPOSANT
	 * ========= */
	
	/** Opérande de gauche */
	public final Valeur gauche;
	/** Opérateur */
	public final OpMathematique operateur;
	/** Opérande de droite */
	public final Valeur droite;

	/**
	 * Construit un calcul à partir de deux valeurs et un opérateur
	 * 
	 * @param gauche Valeur de gauche
	 * @param operator Un opérateur dans +, -, *, /, %
	 * @param droite Valeur de droite
	 */
	public VCalcul(Valeur vGauche, OpMathematique operator, Valeur vDroite) {
		gauche = vGauche;
		this.operateur = operator;
		droite = vDroite;
	}

	/* ================
	 * IMPLEMENTATIONS
	 * ================ */

	@Override
	public String getString() {
		return getValeurParenthesee(this.getPriorite(), gauche.getPriorite(), gauche.getString())
				+ " " + operateur.symbole + " "
				+ getValeurParenthesee(this.getPriorite(), droite.getPriorite(), droite.getString());
	}

	/**
	 * Donne une représentation du fils en considérant qu'il a le père donné pour le parenthésage
	 * @param pere Le père
	 * @param fils Le fils
	 * @return La représentation du fils, entourée de parenthèse si le père est moins prioritaire
	 */
	public static String getValeurParenthesee(int prioritePere, int prioriteFils, String chaineFils) {
		if (prioriteFils < prioritePere) {
			return "(" + chaineFils + ")";
		} else {
			return chaineFils;
		}
	}
	
	/**
	 * Donne la priorité de l'opération de ce calcul
	 * @return La priorité de l'opérateur de ce calcul
	 */
	@Override
	public int getPriorite() {
		return operateur.priorite;
	}
	
	@Override
	public Composant evaluationRapide() {
		if (gauche instanceof VConstante && droite instanceof VConstante) {
			VConstante cstg = (VConstante) gauche;
			VConstante cstd = (VConstante) droite;
			
			if (operateur == OpMathematique.DIVISE) {
				int modulo = cstg.valeur % cstd.valeur;
				
				if (modulo != 0) {
					return this;
				}
			}
				
			return new VConstante(operateur.calculer(cstg.valeur, cstd.valeur));
		}
		
		if (droite instanceof VConstante) {
			VConstante cstd = (VConstante) droite;
			
			if (operateur.getNeutre() == cstd.valeur) {
				return gauche;
			}
		}
		
		
		return this;
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
		if (!(other instanceof VCalcul)) {
			return false;
		}
		VCalcul castOther = (VCalcul) other;
		return Objects.equals(gauche, castOther.gauche) && Objects.equals(operateur, castOther.operateur)
				&& Objects.equals(droite, castOther.droite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gauche, operateur, droite);
	}

}
