package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.Extracteur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.StrategieDeDivision;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireSwitch;

/**
 * Stratégie de division portant sur l'état d'un interrupteur
 * 
 * @author Bruju
 *
 */
public class Interrupteur implements StrategieDeDivision {
	/** Le numéro de l'interrupteur */
	private int numero;

	/**
	 * Crée une stratégie de division portant sur l'état de l'interrupteur donné
	 * @param numero Le numéro de l'interrupteur à tracker
	 */
	public Interrupteur(int numero) {
		this.numero = numero;
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
	 * Extracteur de conditions portant sur l'état d'un interrupteur
	 */
	public class ExtracteurD implements Extracteur {
		/** Condition en cours d'extraction */
		private Set<Condition> conditions;

		@Override
		public void extraire(Composant composant, Set<Condition> conditions) {
			this.conditions = conditions;;
			visit(composant);
		}
		
		@Override
		public void visit(CArme composant) {
		}

		@Override
		public void visit(CSwitch composant) {
			if (composant.interrupteur instanceof BBase) {
				BBase interrupteur = (BBase) composant.interrupteur;
				
				if (interrupteur.numero == numero) {
					conditions.add(new CSwitch(new BBase(numero), true));
					conditions.add(new CSwitch(new BBase(numero), false));
				}
				
				return;
			}
			visit(composant.interrupteur);
		}

		@Override
		public void visit(CVariable composant) {
			visit(composant.gauche);
			visit(composant.droite);
		}
	}

}
