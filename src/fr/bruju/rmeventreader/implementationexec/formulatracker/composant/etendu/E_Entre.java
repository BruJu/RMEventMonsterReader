package fr.bruju.rmeventreader.implementationexec.formulatracker.composant.etendu;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Valeur bornée par deux autres valeurs
 * 
 * @author Bruju
 *
 */
public class E_Entre implements ComposantEtendu, Valeur {
	/* =========
	 * COMPOSANT
	 * ========= */
	/** Borne inférieure */
	public final Valeur borneInf;
	/** Valeur bornée */
	public final Valeur valeur;
	/** Borne supérieure */
	public final Valeur borneSup;
	
	/**
	 * Crée une valuer entre deux autres valeurs
	 * @param borneInf Borne minimale
	 * @param valeur Valeur
	 * @param borneSup Borne maximale
	 */
	public E_Entre(Valeur borneInf, Valeur valeur, Valeur borneSup) {
		this.borneInf = borneInf;
		this.valeur = valeur;
		this.borneSup = borneSup;
	}
	
	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		return "entre(" + borneInf.getString() + " ; " + valeur.getString() + " ; " + borneSup.getString() +")";
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposants) {
		visiteurDeComposants.visit(this);
	}

	@Override
	public Composant evaluationRapide() {
		return this;
	}
	
	@Override
	public Composant getComposantNormal() {
		return new E_Borne(new E_Borne(valeur, borneSup, true), borneInf, false).getComposantNormal();
	}
}
