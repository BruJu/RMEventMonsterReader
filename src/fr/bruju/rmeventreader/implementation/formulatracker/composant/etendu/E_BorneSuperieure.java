package fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

public final class E_BorneSuperieure implements ComposantEtendu, Valeur {
	public final Valeur valeur;
	public final Valeur borneSuperieure;
	
	public E_BorneSuperieure(Valeur valeur, Valeur borneSuperieure) {
		this.valeur = valeur;
		this.borneSuperieure = borneSuperieure;
	}

	@Override
	public void accept(VisiteurDeComposants visiteur) {
		visiteur.visit(this);
	}

	@Override
	public Composant getComposantNormal() {
		return new VTernaire(new CVariable(valeur, Operator.SUP, borneSuperieure), valeur, borneSuperieure);
	}

	@Override
	public String getString() {
		return "max(" + valeur.getString() + ", " + borneSuperieure.getString() + ")";
	}

}
