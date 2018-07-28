package fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.evaluationrapide;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Borne;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Entre;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurRetourneur;

/**
 * Evaluateur non récursif de composans 
 * @author Bruju
 *
 */
public class EvaluationRapide extends VisiteurRetourneur<Reponse> {

	@Override
	protected Reponse traiter(BConstant boutonConstant) {
		return new Reponse.B(boutonConstant.value);
	}

	@Override
	protected Reponse traiter(CSwitch conditionSwitch) {
		if (conditionSwitch.interrupteur instanceof BConstant) {
			BConstant cst = (BConstant) conditionSwitch.interrupteur;
			
			return new Reponse.B(cst.value == conditionSwitch.valeur);
		}
		
		return null;
	}

	@Override
	protected Reponse traiter(CVariable conditionVariable) {
		if (conditionVariable.gauche instanceof VConstante
				&& conditionVariable.droite instanceof VConstante) {
			VConstante cstg = (VConstante) conditionVariable.gauche;
			VConstante cstd = (VConstante) conditionVariable.droite;
			
			return new Reponse.B(conditionVariable.operateur.test(cstg.valeur, cstd.valeur));
		}
		
		return null;
	}

	@Override
	protected Reponse traiter(VCalcul variableCalcul) {
		if (variableCalcul.gauche instanceof VConstante
				&& variableCalcul.droite instanceof VConstante) {
			VConstante cstg = (VConstante) variableCalcul.gauche;
			VConstante cstd = (VConstante) variableCalcul.droite;
			
			return new Reponse.V(variableCalcul.operateur.compute(cstg.valeur, cstd.valeur));
		}
		
		return null;
	}

	@Override
	protected Reponse traiter(VConstante variableConstante) {
		return null;
	}

	@Override
	protected Reponse comportementParDefaut(Composant composant) {
		return null;
	}

}
