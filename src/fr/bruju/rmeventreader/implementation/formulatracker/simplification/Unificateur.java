package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

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
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurRetourneur;

public class Unificateur extends VisiteurRetourneur<Valeur, Valeur> {
	private Composant secondComposant;
	
	public void fixerParam(Composant second) {
		this.secondComposant = second;
	}
	
	
	@Override
	protected Valeur transformer(Valeur composant) {
		return null;
	}

	@Override
	protected Valeur traiter(BBase boutonBase) {
		return null;
	}

	@Override
	protected Valeur traiter(BConstant boutonConstant) {
		return null;
	}

	@Override
	protected Valeur traiter(BTernaire boutonTernaire) {
		return null;
	}

	@Override
	protected Valeur traiter(CArme conditionArme) {
		return null;
	}

	@Override
	protected Valeur traiter(CSwitch conditionSwitch) {
		return null;
	}

	@Override
	protected Valeur traiter(CVariable conditionVariable) {
		return null;
	}

	@Override
	protected Valeur traiter(VAleatoire variableAleatoire) {
		return null;
	}

	@Override
	protected Valeur traiter(VBase variableBase) {
		return null;
	}

	@Override
	protected Valeur traiter(VCalcul variableCalcul) {
		return null;
	}

	@Override
	protected Valeur traiter(VConstante variableConstante) {
		return null;
	}

	@Override
	protected Valeur traiter(VStatistique variableStatistique) {
		return null;
	}

	@Override
	protected Valeur traiter(VTernaire varaibleTernaire) {
		return null;
	}
	
	

}
