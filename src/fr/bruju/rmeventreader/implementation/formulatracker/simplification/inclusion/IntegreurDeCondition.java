package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.ConstructeurDeComposant;

public class IntegreurDeCondition extends ConstructeurDeComposant {	
	private Condition conditionEnCours;
	private CArme enCoursA;
	private CSwitch enCoursS;
	private CVariable enCoursV;
	
	public IntegreurDeCondition() {
		super();
	}
	
	public Valeur integrerCondition(Condition condition, Valeur valeur) {
		conditionEnCours = condition;
		detecterInstanceof();
		valeur.accept(this);
		return (Valeur) pile.pop();
	}
	

	private void detecterInstanceof() {
		enCoursA = null;
		enCoursS = null;
		enCoursV = null;
		
		if (conditionEnCours instanceof CArme) {
			enCoursA = (CArme) conditionEnCours;
		} else if (conditionEnCours instanceof CSwitch) {
			enCoursS = (CSwitch) conditionEnCours;
		} else if (conditionEnCours instanceof CVariable) {
			enCoursV = (CVariable) conditionEnCours;
		}
	}

	@Override
	public void visit(CArme cArme) {
		if (enCoursA == null
				|| cArme.heros != enCoursA.heros
				|| cArme.objet != enCoursA.objet) {
			super.visit(cArme);
			return;
		}
		
		// Resolution de la condition
		this.pile.push(null);
		this.conditionFlag = enCoursA.haveToOwn == cArme.haveToOwn;
	}

	@Override
	public void visit(CSwitch cSwitch) {
		if (enCoursS == null
				|| !enCoursS.interrupteur.equals(cSwitch.interrupteur)) {
			super.visit(cSwitch);
			return;
		}

		// Resolution de la condition
		this.pile.push(null);
		this.conditionFlag = cSwitch.valeur == enCoursS.valeur;
		
	}

	@Override
	public void visit(CVariable cVariable) {
		if (enCoursV == null) {
			super.visit(cVariable);
			return;
		}
		
		
	}

	
	


	
}
