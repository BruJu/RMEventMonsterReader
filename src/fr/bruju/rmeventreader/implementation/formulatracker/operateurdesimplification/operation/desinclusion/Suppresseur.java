package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.desinclusion;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteIllegale;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposantsADefaut;

/**
 * Transforme une valeur avec des ternaires dont la condition fausse est vide en une liste de conditions et une
 * valeur.
 * 
 * @author Bruju
 *
 */
public class Suppresseur implements VisiteurDeComposantsADefaut {
	/*
	 * Algorithme : Visite une valeur. Continue à visiter jusqu'à qu'il trouve une valeur qui n'est pas une ternaire
	 * ou une ternaire ayant deux fils.
	 */

	private List<Condition> liste = new ArrayList<>();
	private Valeur valeur;
	
	public List<Condition> getConditions() {
		return liste;
	}

	public Valeur getFormule() {
		return valeur;
	}

	public void traiter(Valeur formule) {
		visit(formule);
	}
	
	@Override
	public void visit(VTernaire vTernaire) {
		if (vTernaire.siFaux == null) {
			liste.add(vTernaire.condition);
			visit(vTernaire.siVrai);
		} else {
			valeur = vTernaire;
		}
	}

	@Override
	public void visit(VAleatoire composant) {
		valeur = composant;
	}

	@Override
	public void visit(VBase composant) {
		valeur = composant;
	}

	@Override
	public void visit(VConstante composant) {
		valeur = composant;
	}

	@Override
	public void visit(VStatistique composant) {
		valeur = composant;
	}

	@Override
	public void visit(VCalcul vCalcul) {
		valeur = vCalcul;
	}

	@Override
	public void comportementParDefaut() {
		throw new VisiteIllegale();
	}
}
