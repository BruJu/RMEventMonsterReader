package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.Extracteur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.StrategieDeDivision;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireSwitch;

public class Propriete implements StrategieDeDivision {

	private String nomPropriete;

	public Propriete(String nomPropriete) {
		this.nomPropriete = nomPropriete;
	}

	@Override
	public List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions) {
		List<GestionnaireDeCondition> gestionnaires = new ArrayList<>();
		
		gestionnaires.add(new GestionnaireSwitch((CSwitch) condition));
		
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
		public void visit(CSwitch composant) {
			if (composant.interrupteur instanceof BStatistique) {
				BStatistique bStat = (BStatistique) composant.interrupteur;
				
				if (bStat.statistique.nom.equals(nomPropriete)) {
					conditions.add(new CSwitch(bStat, true));
					conditions.add(new CSwitch(bStat, false));
				}
			}
			super.visit(composant);
		}
	}
}
