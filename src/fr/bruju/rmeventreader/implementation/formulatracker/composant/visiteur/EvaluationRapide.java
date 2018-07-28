package fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Borne;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Entre;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;

/**
 * Evaluateur non récursif de composants 
 * @author Bruju
 *
 */
public final class EvaluationRapide extends VisiteurRetourneur<Composant> {
	private static EvaluationRapide instance;

	private EvaluationRapide() {
	}

	public static EvaluationRapide getInstance() {
		if (null == instance) {
			instance = new EvaluationRapide();
		}
		return instance;
	}
	
	
	public Integer evaluer(Composant c) {
		Composant evalue = traiter(c);
		
		if (evalue instanceof VConstante) {
			return ((VConstante) evalue).valeur;
		} else {
			return null;
		}
	}
	
	@Override
	protected Composant traiter(CSwitch conditionSwitch) {
		if (conditionSwitch.interrupteur instanceof BConstant) {
			BConstant cst = (BConstant) conditionSwitch.interrupteur;
			
			return CFixe.get(cst.value == conditionSwitch.valeur);
		}
		
		return conditionSwitch;
	}

	@Override
	protected Composant traiter(CVariable conditionVariable) {
		if (conditionVariable.gauche instanceof VConstante
				&& conditionVariable.droite instanceof VConstante) {
			VConstante cstg = (VConstante) conditionVariable.gauche;
			VConstante cstd = (VConstante) conditionVariable.droite;
			
			return CFixe.get(conditionVariable.operateur.test(cstg.valeur, cstd.valeur));
		}
		
		return conditionVariable;
	}

	@Override
	protected Composant traiter(VCalcul variableCalcul) {
		if (variableCalcul.gauche instanceof VConstante
				&& variableCalcul.droite instanceof VConstante) {
			VConstante cstg = (VConstante) variableCalcul.gauche;
			VConstante cstd = (VConstante) variableCalcul.droite;
			
			if (variableCalcul.operateur == Operator.DIVIDE) {
				int modulo = cstg.valeur % cstd.valeur;
				
				if (modulo != 0) {
					return variableCalcul;
				}
			}
				
			return new VConstante(variableCalcul.operateur.compute(cstg.valeur, cstd.valeur));
		}
		
		return variableCalcul;
	}
	
	@Override
	public void visit(CFixe composant) {
		this.composant = traiter(composant);
	}
	
	protected Composant traiter(CFixe composant) {
		return composant;
	}


	@Override
	protected Composant traiter(E_Borne composant) {
		return composant;
	}

	@Override
	protected Composant traiter(E_Entre borne) {
		return borne;
	}

	@Override
	protected Composant comportementParDefaut(Composant composant) {
		return composant;
	}	
}
