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

public class VariableEnsemble implements StrategieDeDivision {
	private int idVariable;
	private int[] ensembleDeValeursPossibles;

	public VariableEnsemble(int idVariable, int[] ensembleDeValeursPossibles) {
		this.idVariable = idVariable;
		this.ensembleDeValeursPossibles = ensembleDeValeursPossibles;
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
	 * Extracteur de conditions portant sur des armes
	 */
	public class ExtracteurD extends Extracteur {
		@Override
		public void visit(CVariable composant) {
			if (composant.gauche instanceof VBase) {
				VBase gauche = (VBase) composant.gauche;
				
				if (gauche.idVariable == idVariable) {
					for (int valeurPossible : ensembleDeValeursPossibles) {
						conditions.add(new CVariable(gauche, Operator.IDENTIQUE, new VConstante(valeurPossible)));
					}
					
					return;
				}
			}
			
			visit(composant.gauche);
			visit(composant.droite);
		}
	}
}