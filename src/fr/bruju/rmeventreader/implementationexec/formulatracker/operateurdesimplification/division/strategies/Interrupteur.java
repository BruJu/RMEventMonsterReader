package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.division.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementationexec.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.division.Extracteur;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.division.StrategieDeDivision;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireSwitch;

/**
 * Stratégie de division portant sur l'état d'un interrupteur
 * 
 * @author Bruju
 *
 */
public class Interrupteur implements StrategieDeDivision {
	/** Le numéro de l'interrupteur */
	private int numero;
	private final Function<Condition, String> fonctionDaffichage;

	/**
	 * Crée une stratégie de division portant sur l'état de l'interrupteur donné
	 * @param numero Le numéro de l'interrupteur à tracker
	 */
	public Interrupteur(int numero) {
		this.numero = numero;
		this.fonctionDaffichage = Condition::getString;
	}
	
	public Interrupteur(int numero, Function<Boolean, String> func) {
		this.numero = numero;
		this.fonctionDaffichage = c -> func.apply(((CSwitch) c).valeur);
	}

	@Override
	public Function<Condition, String> getFonctionDAffichage() {
		return fonctionDaffichage;
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
	public class ExtracteurD extends Extracteur {
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
	}
}
