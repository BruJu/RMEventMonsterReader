package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.Extracteur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.StrategieDeDivision;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireVariableIdentique;

public class Variable1Valeur implements StrategieDeDivision {
	
	private int idVariable;
	
	private int valeur;

	public Variable1Valeur(int idVariable, int valeur) {
		this.idVariable = idVariable;
		this.valeur = valeur;
	}

	@Override
	public List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions) {
		List<GestionnaireDeCondition> gestionnaires = new ArrayList<>();
		gestionnaires.add(new GestionnaireVariableIdentique((CVariable) condition));
		return gestionnaires;
	}

	@Override
	public Extracteur getExtracteur() {
		return new ExtracteurD();
	}
	
	/**
	 * Extracteur de conditions
	 */
	public class ExtracteurD extends Extracteur {
		@Override
		public void visit(CVariable composant) {
			if (composant.gauche instanceof VBase) {
				VBase gauche = (VBase) composant.gauche;
				
				if (gauche.idVariable == idVariable) {
					conditions.add(new CVariable(gauche, Operator.IDENTIQUE, new VConstante(valeur)));
					conditions.add(new CVariable(gauche, Operator.DIFFERENT, new VConstante(valeur)));
					
					return;
				}
			}
			super.visit(composant);
		}
	}
}
