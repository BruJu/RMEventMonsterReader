package fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur;

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

/**
 * Classse abstraite offrant une base pour traiter les composants en utilisant des valeurs retournées par les fonctions
 * de traitement.
 * 
 * @author Bruju
 *
 * @param <Intermediaire> Type intermédiaire au traitement
 * @param <Retour> Type de retour
 */
public abstract class VisiteurRetourneur<Intermediaire, Retour> implements VisiteurDeComposants {
	private Intermediaire composant;

	public Retour get(Composant composant) {
		visit(composant);
		return transformer(this.composant);
	}

	protected abstract Retour transformer(Intermediaire composant);

	protected abstract Intermediaire traiter(BBase boutonBase);

	protected abstract Intermediaire traiter(BConstant boutonConstant);

	protected abstract Intermediaire traiter(BTernaire boutonTernaire);

	protected abstract Intermediaire traiter(CArme conditionArme);

	protected abstract Intermediaire traiter(CSwitch conditionSwitch);

	protected abstract Intermediaire traiter(CVariable conditionVariable);

	protected abstract Intermediaire traiter(VAleatoire variableAleatoire);

	protected abstract Intermediaire traiter(VBase variableBase);

	protected abstract Intermediaire traiter(VCalcul variableCalcul);

	protected abstract Intermediaire traiter(VConstante variableConstante);

	protected abstract Intermediaire traiter(VStatistique variableStatistique);

	protected abstract Intermediaire traiter(VTernaire varaibleTernaire);

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
