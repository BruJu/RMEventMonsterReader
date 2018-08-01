package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies;

import java.util.List;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.Extracteur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.StrategieDeDivision;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;

public class VariableDecouverte implements StrategieDeDivision {

	private int idVariable;

	public VariableDecouverte(int idVariable) {
		this.idVariable = idVariable;
	}

	@Override
	public List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions) {
		return null;
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
			if (composant.gauche instanceof VBase && composant.droite instanceof VConstante) {
				VBase gauche = (VBase) composant.gauche;
				VConstante droite = (VConstante) composant.droite;
				
				if (gauche.idVariable == idVariable &&
						(composant.operateur == Operator.IDENTIQUE
						|| composant.operateur == Operator.DIFFERENT)) {
					conditions.add(new CVariable(gauche, Operator.IDENTIQUE, droite));
					conditions.add(CFixe.get(true));		// Subterfuge pour dire "par défaut"
					return;
				}
			}
			
			super.visit(composant);
		}
	}
}
