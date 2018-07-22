package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class CVariable implements ConditionGauche<Valeur> {

	public final Valeur gauche;
	public final Operator operateur;
	public final Valeur droite;

	public CVariable(Valeur gauche, Operator operateur, Valeur vDroite) {
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = vDroite;
	}

	@Override
	public Condition revert() {
		return new CVariable(gauche, operateur.revert(), droite);
	}

	@Override
	public String getString() {
		return gauche.getString() + " " + getStringSansGauche();
	}

	@Override
	public Valeur getGauche() {
		return gauche;
	}

	@Override
	public String getStringSansGauche() {
		return Utilitaire.getSymbole(operateur) + " " + droite.getString();
	}
	
	

}
