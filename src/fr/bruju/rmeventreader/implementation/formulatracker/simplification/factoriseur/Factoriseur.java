package fr.bruju.rmeventreader.implementation.formulatracker.simplification.factoriseur;

import java.util.function.Predicate;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

public class Factoriseur implements VisiteurDeComposants, Maillon {
	
	
	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::factoriser);
	}

	
	/*
	 * Conventions :
	 * - Quand on visite, on modifie composantReponse
	 * - composantReponse = null => il faut regarder ailleurs. composantReponse = composan visité => aucun traitement 
	 */

	private final Operator[] operateursFactorisables = new Operator[] { Operator.PLUS, Operator.MINUS };
	private final Operator[] multiplication = new Operator[] { Operator.TIMES, Operator.DIVIDE };

	private ConstructeurDeRepresentationVariadique factoriseur = new ConstructeurDeRepresentationVariadique();

	private Composant composantReponse;
	private boolean estMultiplication;

	public Composant factoriser(Composant composant) {

		visit(composant);

		return composantReponse;
	}

	@Override
	public void visit(VCalcul vCalcul) {
		VCalcul that = vCalcul;

		TriFunction<VCalcul, Valeur, Valeur, VCalcul> fonctionDeCreation = (base, g, d) -> new VCalcul(g,
				base.operateur, d);

		that = (VCalcul) factoriserFils(that, that.gauche, that.droite,
				v -> v.operateur.appartient(operateursFactorisables), fonctionDeCreation);

		// Fin
		this.estMultiplication = that.operateur.appartient(multiplication);
		composantReponse = that;
	}

	/**
	 * 
	 * On grantie renvoyer un T ou un VCalcul. Donc si T = VCalcul, le cast en VCalcul est sûr.
	 */
	private <T extends Valeur> Valeur factoriserFils(T that, Valeur gauche, Valeur droite,
			Predicate<T> autoriserFactorisation, TriFunction<T, Valeur, Valeur, T> fonctionDeCreation) {
		// Factoriser à gauche
		estMultiplication = false;
		visit(gauche);
		Valeur nouveauComposantGauche = (Valeur) composantReponse;
		boolean gaucheCandidatFactorisation = estMultiplication;

		// Factoriser à droite
		if (droite == null) {
			return that;
		}

		estMultiplication = false;
		visit(droite);
		Valeur nouveauComposantDroite = (Valeur) composantReponse;
		boolean droiteCandidatFactorisation = estMultiplication;

		estMultiplication = false;

		boolean gaucheAChange = composantReponse == gauche;
		boolean droiteAChange = composantReponse == droite;

		// Creation d'un nouveau composant si necessaire
		if (gaucheCandidatFactorisation && droiteCandidatFactorisation && autoriserFactorisation.test(that)) {
			VCalcul vcg = (VCalcul) nouveauComposantGauche;
			VCalcul vcd = (VCalcul) nouveauComposantDroite;

			RepresentationVariadique rg = factoriseur.creerRepresentationVariadique(vcg, Operator.TIMES);
			RepresentationVariadique rd = factoriseur.creerRepresentationVariadique(vcd, Operator.TIMES);
			RepresentationVariadique f = rg.factoriser(rd);

			if (!f.isEmpty()) {
				Valeur nouvelleGauche = rg.convertirEnCalcul();
				Valeur nouvelleDroite = rd.convertirEnCalcul();
				Valeur elementsCommuns = f.convertirEnCalcul();
				return new VCalcul(elementsCommuns, vcg.operateur,
						fonctionDeCreation.apply(that, nouvelleGauche, nouvelleDroite));
			}
		}

		if (gaucheAChange || droiteAChange) {
			return fonctionDeCreation.apply(that, nouveauComposantGauche, nouveauComposantDroite);
		} else {
			return that;
		}
	}

	@Override
	public void visit(VTernaire vTernaire) {
		TriFunction<VTernaire, Valeur, Valeur, VTernaire> fonctionDeCreation = (base, g,
				d) -> new VTernaire(base.condition, g, d);

		this.composantReponse = factoriserFils(vTernaire, vTernaire.siVrai, vTernaire.siFaux, (v) -> true,
				fonctionDeCreation);
	}

	/*
	 * Contenu factorisable
	 */

	@Override
	public void visit(BTernaire bTernaire) {
		feuilleNonExploitable(bTernaire);
	}

	@Override
	public void visit(CArme cArme) {
		feuilleNonExploitable(cArme);
	}

	@Override
	public void visit(CSwitch cSwitch) {
		feuilleNonExploitable(cSwitch);
	}

	@Override
	public void visit(CVariable cVariable) {
		feuilleNonExploitable(cVariable);
	}

	/*
	 * Composants non exploitables
	 */

	private void feuilleNonExploitable(Composant composant) {
		composantReponse = composant;
	}

	@Override
	public void visit(BConstant composant) {
		feuilleNonExploitable(composant);
	}

	@Override
	public void visit(BBase composant) {
		feuilleNonExploitable(composant);
	}

	@Override
	public void visit(VStatistique composant) {
		feuilleNonExploitable(composant);
	}

	@Override
	public void visit(VConstante composant) {
		feuilleNonExploitable(composant);
	}

	@Override
	public void visit(VBase composant) {
		feuilleNonExploitable(composant);
	}

	@Override
	public void visit(VAleatoire composant) {
		feuilleNonExploitable(composant);
	}


}
