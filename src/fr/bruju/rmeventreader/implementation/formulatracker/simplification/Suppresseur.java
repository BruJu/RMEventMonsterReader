package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class Suppresseur implements VisiteurDeComposantsADefaut {

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
	public void visit(CArme cArme) {
		return;
	}

	@Override
	public void visit(CSwitch cSwitch) {
		return;
	}

	@Override
	public void visit(CVariable cVariable) {
		return;
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
		throw new RuntimeException("invalid state");
	}



	
	

}
