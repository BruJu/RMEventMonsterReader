package fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;
import java.util.Objects;

public final class E_Borne implements ComposantEtendu, Valeur {
	public final Valeur valeur;
	public final Valeur borne;
	public final boolean estBorneSup;

	public E_Borne(Valeur valeur, Valeur borneSuperieure, boolean estborneSup) {
		this.valeur = valeur;
		this.borne = borneSuperieure;
		this.estBorneSup = estborneSup;
	}

	@Override
	public void accept(VisiteurDeComposants visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Composant getComposantNormal() {
		return new VTernaire(new CVariable(valeur, estBorneSup ? Operator.SUP : Operator.INF, borne), valeur, borne);
	}

	@Override
	public String getString() {
		return ((estBorneSup) ? "max" : "min") + "(" + valeur.getString() + ", " + borne.getString() + ")";
	}

	@Override
	public int hashCode() {
		return Objects.hash(valeur, borne, estBorneSup);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof E_Borne) {
			E_Borne that = (E_Borne) object;
			return Objects.equals(this.valeur, that.valeur) && Objects.equals(this.borne, that.borne)
					&& this.estBorneSup == that.estBorneSup;
		}
		return false;
	}
	
	

}
