package fr.bruju.rmeventreader.implementation.formulatracker.modifmodifstat.diviseurs;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireArmeStrict;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;

public class DiviseurArme implements StrategieDeDivision {
	private int numeroPersonnage;
	
	public DiviseurArme(int numeroPersonnage) {
		this.numeroPersonnage = numeroPersonnage;
	}

	@Override
	public List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions) {
		List<GestionnaireDeCondition> gestionnaires = new ArrayList<>();
		
		gestionnaires.add(new GestionnaireArmeStrict((CArme) condition));
		
		return gestionnaires;
	}

	@Override
	public Extracteur getExtracteur() {
		return new ExtracteurD();
	}
	
	/**
	 * Extracteur de conditions portant sur des armes
	 */
	public class ExtracteurD implements Extracteur {
		Set<Condition> conditions;

		@Override
		public void extraire(Composant composant, Set<Condition> conditions) {
			this.conditions = conditions;;
			visit(composant);
		}
		
		@Override
		public void visit(CArme composant) {
			if (composant.heros == numeroPersonnage) {
				conditions.add(composant);
				conditions.add(new CArme(numeroPersonnage, 0));
			}
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
}
