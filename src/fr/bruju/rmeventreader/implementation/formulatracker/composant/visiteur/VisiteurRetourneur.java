package fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.ComposantEtendu;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Borne;
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
 */
public abstract class VisiteurRetourneur<Intermediaire> implements VisiteurDeComposants {
	private Intermediaire composant;

	
	public Intermediaire traiter(Composant composant) {
		visit(composant);
		return this.composant;
	}

	protected Intermediaire traiter(BBase boutonBase) {
		return comportementParDefaut(boutonBase);
	}

	protected Intermediaire traiter(BConstant boutonConstant) {
		return comportementParDefaut(boutonConstant);
	}

	protected Intermediaire traiter(BTernaire boutonTernaire) {
		return comportementParDefaut(boutonTernaire);
	}

	protected Intermediaire traiter(CArme conditionArme) {
		return comportementParDefaut(conditionArme);
	}

	protected Intermediaire traiter(CSwitch conditionSwitch) {
		return comportementParDefaut(conditionSwitch);
	}

	protected Intermediaire traiter(CVariable conditionVariable) {
		return comportementParDefaut(conditionVariable);
	}

	protected Intermediaire traiter(VAleatoire variableAleatoire) {
		return comportementParDefaut(variableAleatoire);
	}

	protected Intermediaire traiter(VBase variableBase) {
		return comportementParDefaut(variableBase);
	}

	protected Intermediaire traiter(VCalcul variableCalcul) {
		return comportementParDefaut(variableCalcul);
	}

	protected Intermediaire traiter(VConstante variableConstante) {
		return comportementParDefaut(variableConstante);
	}

	protected Intermediaire traiter(VStatistique variableStatistique) {
		return comportementParDefaut(variableStatistique);
		
	}

	protected Intermediaire traiter(VTernaire variableTernaire) {
		return comportementParDefaut(variableTernaire);
	}

	protected abstract Intermediaire comportementParDefaut(Composant composant);
	
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
	
	
	/* ==================
	 * COMPOSANTS ETENDUS
	 * ================== */
	
	// Composant étendu
	
	protected Intermediaire composantEtenduNonGere(ComposantEtendu composant) {
		return traiter(composant.getComposantNormal());
	}
	
	@Override
	public void visit(E_Borne composant) {
		this.composant = traiter(composant);
	}

	protected Intermediaire traiter(E_Borne composant) {
		return composantEtenduNonGere(composant);
	}
	
	
}
