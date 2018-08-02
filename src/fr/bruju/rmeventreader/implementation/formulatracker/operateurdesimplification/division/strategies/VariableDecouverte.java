package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.Extracteur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.StrategieDeDivision;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireVariableDifferent;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireVariableIdentique;

public class VariableDecouverte implements StrategieDeDivision {

	private int idVariable;
	private final Function<Condition, String> fonctionDaffichage;

	public VariableDecouverte(int idVariable) {
		this.idVariable = idVariable;
		this.fonctionDaffichage = Condition::getString;
	}
	
	public VariableDecouverte(int idVariable, Function<Integer, String> func) {
		this.idVariable = idVariable;
		this.fonctionDaffichage = c -> (c == CFixe.get(true) ? func.apply(null) : 
				func.apply(((VConstante) (((CVariable) c).droite)).valeur));
	}

	@Override
	public Function<Condition, String> getFonctionDAffichage() {
		return fonctionDaffichage;
	}

	@Override
	public List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions) {
		List<GestionnaireDeCondition> gestionnaires = new ArrayList<>();
		
		Boolean identifier = CFixe.identifier(condition);
		
		if (identifier == null) {
			gestionnaires.add(new GestionnaireVariableIdentique((CVariable) condition));
		} else {
			conditions
			.stream()
			.filter(c -> CFixe.identifier(c) == null)
			.forEach(c -> gestionnaires.add(new GestionnaireVariableDifferent((CVariable) c.revert())));
		}
		
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
