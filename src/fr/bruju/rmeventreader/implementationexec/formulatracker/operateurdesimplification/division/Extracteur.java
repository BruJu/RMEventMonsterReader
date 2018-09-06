package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.division;

import java.util.Set;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.visiteur.VisiteurDeComposants;

/**
 * Extrait des conditions présentes dans les composants
 * 
 * @author Bruju
 *
 */
public abstract class Extracteur implements VisiteurDeComposants {
	/** Conditions en cours d'extraction */
	protected Set<Condition> conditions;
	
	/**
	 * Extrait les conditions du composant donné et les ajoute dans l'ensemble de conditions
	 * @param composant Le composant dont il faut extraire des conditions
	 * @param conditions Les conditions extraites
	 */
	public void extraire(Composant composant, Set<Condition> conditions) {
		this.conditions = conditions;;
		visit(composant);
	}

	/* ========
	 * Feuilles
	 * ======== */
	@Override
	public void visit(BBase composant) {
		// Feuille
	}

	@Override
	public void visit(BConstant composant) {
		// Feuille
	}

	@Override
	public void visit(BStatistique composant) {
		// Feuille
	}

	@Override
	public void visit(VAleatoire composant) {
		// Feuille
	}

	@Override
	public void visit(VBase composant) {
		// Feuille
	}

	@Override
	public void visit(VConstante composant) {
		// Feuille
	}

	@Override
	public void visit(VStatistique composant) {
		// Feuille
	}	

	@Override
	public void visit(CArme composant) {
		// Feuille
	}

	/* ===================
	 * Composants composés
	 * =================== */
	@Override
	public void visit(VCalcul composant) {
		visit(composant.gauche);
		visit(composant.droite);
	}

	@Override
	public void visit(VTernaire composant) {
		visit(composant.condition);
		visit(composant.siVrai);
		visit(composant.siFaux);
	}

	@Override
	public void visit(BTernaire composant) {
		visit(composant.condition);
		visit(composant.siVrai);
		visit(composant.siFaux);
	}

	
	@Override
	public void visit(CSwitch composant) {
		visit(composant.interrupteur);
	}

	@Override
	public void visit(CVariable composant) {
		visit(composant.gauche);
		visit(composant.droite);
	}
	
	
}
