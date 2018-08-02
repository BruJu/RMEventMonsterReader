package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.IntStream;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.Extracteur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.StrategieDeDivision;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireSwitch;

public class DisjonctionInterrupteurs implements StrategieDeDivision {
	private int[] idSwitch;
	private final Function<Condition, String> fonctionDaffichage;

	public DisjonctionInterrupteurs(int[] idSwitch) {
		this.idSwitch = idSwitch;
		this.fonctionDaffichage = Condition::getString;
	}
	
	public DisjonctionInterrupteurs(int[] idSwitch, Function<Integer, String> func) {
		this.idSwitch = idSwitch;
		this.fonctionDaffichage = condition -> func.apply(((BBase) (((CSwitch) condition).interrupteur)).numero);
	}

	@Override
	public Function<Condition, String> getFonctionDAffichage() {
		return fonctionDaffichage;
	}

	@Override
	public List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions) {
		List<GestionnaireDeCondition> gestionnaires = new ArrayList<>();
		
		conditions.forEach(c -> {
			if (c == condition) {
				gestionnaires.add(new GestionnaireSwitch((CSwitch) condition));
			} else {
				gestionnaires.add(new GestionnaireSwitch((CSwitch) c.revert()));
			}
		});
		
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
			if (composant.interrupteur instanceof BBase) {
				BBase inter = (BBase) composant.interrupteur;
				
				if (IntStream.of(idSwitch).anyMatch(id -> id == inter.numero)) {
					for (int id : idSwitch) {
						this.conditions.add(new CSwitch(new BBase(id), true));
					}
				}
				
				return;
			}
			
			super.visit(composant);
		}
	}
}
