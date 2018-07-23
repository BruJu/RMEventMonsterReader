package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Composant;
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

public abstract class VisiteurRetourneur implements VisiteurDeComposants {
	private Composant composant;

	public Composant get(Composant composant) {
		visit(composant);
		return this.composant;
	}

	protected abstract Composant traiter(BBase boutonBase);

	protected abstract Composant traiter(BConstant boutonConstant);

	protected abstract Composant traiter(BTernaire boutonTernaire);

	protected abstract Composant traiter(CArme conditionArme);

	protected abstract Composant traiter(CSwitch conditionSwitch);

	protected abstract Composant traiter(CVariable conditionVariable);

	protected abstract Composant traiter(VAleatoire variableAleatoire);

	protected abstract Composant traiter(VBase variableBase);

	protected abstract Composant traiter(VCalcul variableCalcul);

	protected abstract Composant traiter(VConstante variableConstante);

	protected abstract Composant traiter(VStatistique variableStatistique);

	protected abstract Composant traiter(VTernaire varaibleTernaire);

	// Visiteurs

	@Override
	public void visit(BBase composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(BConstant composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(BTernaire composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(CArme composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(CSwitch composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(CVariable composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(VAleatoire composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(VBase composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(VCalcul composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(VConstante composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(VStatistique composant) {
		this.composant = traiter(composant);
	}

	@Override
	public void visit(VTernaire composant) {
		this.composant = traiter(composant);
	}

}
