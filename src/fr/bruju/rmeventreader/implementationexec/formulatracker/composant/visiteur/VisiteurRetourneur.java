package fr.bruju.rmeventreader.implementationexec.formulatracker.composant.visiteur;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.etendu.ComposantEtendu;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.etendu.E_Borne;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.etendu.E_Entre;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VTernaire;

/**
 * Classse abstraite offrant une base pour traiter les composants en utilisant des valeurs retournées par les fonctions
 * de traitement.
 * 
 * @author Bruju
 *
 * @param <Intermediaire> Type intermédiaire au traitement
 */
public abstract class VisiteurRetourneur<Intermediaire> implements VisiteurDeComposants {
	/** Element retourné */
	private Intermediaire composant;


	/** Traitement d'un composant */
	public Intermediaire traiter(Composant composant) {
		visit(composant);
		return this.composant;
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(BBase boutonBase) {
		return comportementParDefaut(boutonBase);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(BConstant boutonConstant) {
		return comportementParDefaut(boutonConstant);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(BTernaire boutonTernaire) {
		return comportementParDefaut(boutonTernaire);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(BStatistique boutonTernaire) {
		return comportementParDefaut(boutonTernaire);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(CArme conditionArme) {
		return comportementParDefaut(conditionArme);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(CSwitch conditionSwitch) {
		return comportementParDefaut(conditionSwitch);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(CVariable conditionVariable) {
		return comportementParDefaut(conditionVariable);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(VAleatoire variableAleatoire) {
		return comportementParDefaut(variableAleatoire);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(VBase variableBase) {
		return comportementParDefaut(variableBase);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(VCalcul variableCalcul) {
		return comportementParDefaut(variableCalcul);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(VConstante variableConstante) {
		return comportementParDefaut(variableConstante);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(VStatistique variableStatistique) {
		return comportementParDefaut(variableStatistique);
		
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(VTernaire variableTernaire) {
		return comportementParDefaut(variableTernaire);
	}

	/**
	 * Comportement par défaut si le composant n'a pas de traîtement spécifique
	 */
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
	public void visit(BStatistique composant) {
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
	
	/** Traitement d'un composant étendu n'ayant pas de traîtement spécifique */
	protected Intermediaire composantEtenduNonGere(ComposantEtendu composant) {
		return traiter(composant.getComposantNormal());
	}
	
	@Override
	public void visit(E_Borne composant) {
		this.composant = traiter(composant);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(E_Borne composant) {
		return composantEtenduNonGere(composant);
	}

	@Override
	public void visit(E_Entre composant) {
		this.composant = traiter(composant);
	}

	/** Traitement d'un composant */
	protected Intermediaire traiter(E_Entre borne) {
		return composantEtenduNonGere(borne);
	}
	
}
