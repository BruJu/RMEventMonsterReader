package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.simples;

import java.util.ArrayList;

import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.Maillon;

/**
 * Maillon supprimant les conditions des formules
 * 
 * @author Bruju
 *
 */
public class MaillonSupprimerCondition implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerFormules(f -> new FormuleDeDegats(new ArrayList<>(), f.formule));
	}
}
