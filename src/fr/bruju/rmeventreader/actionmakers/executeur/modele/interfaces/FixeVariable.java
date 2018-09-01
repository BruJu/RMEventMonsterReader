package fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurFixeVariable;

public interface FixeVariable {
	public <T> T accept(VisiteurFixeVariable<T> visiteur);
	
	public <T> T appliquerFV(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> fonctionVariable);
	
}
