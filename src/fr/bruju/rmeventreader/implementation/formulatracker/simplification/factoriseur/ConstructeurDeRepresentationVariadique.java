package fr.bruju.rmeventreader.implementation.formulatracker.simplification.factoriseur;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class ConstructeurDeRepresentationVariadique implements VisiteurDeComposants {
	
	private List<Valeur> facteurs;
	private List<Operator> operateurs;
	
	
	public RepresentationVariadique creerRepresentationVariadique(VCalcul calcul, Operator operateur) {
		facteurs = new ArrayList<>();
		operateurs = new ArrayList<>();
		
		operateurs.add(operateur);
		visit(calcul);
		
		return new RepresentationVariadique(Pair.combiner(facteurs, operateurs), operateur.getNeutre());
	}
	
	@Override
	public void visit(VCalcul vCalcul) {
		if (vCalcul.operateur == Operator.TIMES || vCalcul.operateur == Operator.DIVIDE) {
			visit(vCalcul.gauche);
			operateurs.add(vCalcul.operateur);
			visit(vCalcul.droite);
		} else {
			traitementFeuille(vCalcul);
		}
	}
	
	// Feuilles

	private void traitementFeuille(Valeur composant) {
		facteurs.add(composant);
	}
	
	@Override
	public void visit(VStatistique composant) {
		traitementFeuille(composant);
	}


	@Override
	public void visit(VConstante composant) {
		traitementFeuille(composant);
	}

	@Override
	public void visit(VBase composant) {
		traitementFeuille(composant);
	}

	@Override
	public void visit(VAleatoire composant) {
		traitementFeuille(composant);
	}


	@Override
	public void visit(VTernaire composant) {
		traitementFeuille(composant);
	}
	
	// Cas illégaux
	
	@Override
	public void visit(BBase composant) {
		visiteIllegale();
	}

	@Override
	public void visit(BConstant composant) {
		visiteIllegale();
	}

	@Override
	public void visit(BTernaire composant) {
		visiteIllegale();
	}

	@Override
	public void visit(CArme composant) {
		visiteIllegale();
	}

	@Override
	public void visit(CSwitch composant) {
		visiteIllegale();
	}

	@Override
	public void visit(CVariable composant) {
		visiteIllegale();
	}
	

	private void visiteIllegale() {
		throw new VisiteIllegaleException();
	}
	
	private static class VisiteIllegaleException extends RuntimeException {
		private static final long serialVersionUID = -655754844829272488L;
	}


}
