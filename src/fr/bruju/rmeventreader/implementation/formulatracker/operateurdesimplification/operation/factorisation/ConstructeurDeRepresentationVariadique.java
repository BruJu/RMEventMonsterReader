package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.factorisation;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BStatistique;
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

public class ConstructeurDeRepresentationVariadique implements VisiteurDeComposants {
	
	private List<Valeur> facteurs;
	private List<Operator> operateurs;
	private Operator operateur;

	public RepresentationVariadique creerRepresentationVariadique(Valeur valeur, Operator operateur) {
		// Recherche des paramètres
		facteurs = new ArrayList<>();
		operateurs = new ArrayList<>();
		this.operateur = operateur;
		
		visit(valeur);
		
		return new RepresentationVariadique(facteurs, operateurs, operateur);
	}

	@Override
	public void visit(VCalcul vCalcul) {
		if (vCalcul.operateur == Operator.MODULO) {
			traitementFeuille(vCalcul);
			return;
		}
		
		
		if (vCalcul.operateur == operateur || vCalcul.operateur == operateur.revert()) {
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
	public void visit(BStatistique composant) {
		visiteIllegale();
	}

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
