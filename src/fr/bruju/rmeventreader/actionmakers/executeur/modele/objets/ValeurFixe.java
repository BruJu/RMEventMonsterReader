package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurFixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;

public class ValeurFixe implements FixeVariable, ValeurDroite, ValeurDroiteVariable, ValeurMembre {
	public final int valeur;
	
	public ValeurFixe(int valeur) {
		this.valeur = valeur;
	}

	@Override
	public <T> T accept(VisiteurValeurDroite<T> visiteur)  {
		return visiteur.visit(this);
	}

	@Override
	public <T> T accept(VisiteurFixeVariable<T> visiteur)  {
		return visiteur.visit(this);
	}

	@Override
	public <T> T accept(VisiteurValeurDroiteVariable<T> visiteur)  {
		return visiteur.visit(this);
	}

	@Override
	public <T> T accept(VisiteurMembre<T> visiteur)  {
		return visiteur.visit(this);
	}

	@Override
	public <T> T appliquerFV(Function<ValeurFixe, T> fonctionFixe, Function<Variable, T> fonctionVariable) {
		return fonctionFixe == null ? null : fonctionFixe.apply(this);
	}
}
