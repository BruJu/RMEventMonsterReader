package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.desinclusion;

import java.util.function.UnaryOperator;

import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.maillon.template.MaillonSurFormule;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;

// TODO : enlever la desinclusion

public class DesinclusionAttaques implements UnaryOperator<FormuleDeDegats> {
	public static Maillon getMaillon() {
		return new MaillonSurFormule(new DesinclusionAttaques());
	}
	
	@Override
	public FormuleDeDegats apply(FormuleDeDegats formule) {
		Suppresseur suppresseur = new Suppresseur();
		suppresseur.traiter(formule.formule);

		return new FormuleDeDegats(formule.operator, suppresseur.getConditions(), suppresseur.getFormule());
	}
}
