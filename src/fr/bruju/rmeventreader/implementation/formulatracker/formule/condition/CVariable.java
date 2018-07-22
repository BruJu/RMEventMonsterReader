package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class CVariable implements Condition {

	private Valeur gauche;
	private Operator operateur;
	private Valeur droite;

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
		return gauche.getString() + " " + Utilitaire.getSymbole(operateur) + " " + droite.getString();
	}
	
	

}
