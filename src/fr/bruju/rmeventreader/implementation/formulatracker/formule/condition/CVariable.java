package fr.bruju.rmeventreader.implementation.formulatracker.formule.condition;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import java.util.Objects;

public class CVariable implements Condition {

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

	public String getStringSansGauche() {
		return Utilitaire.getSymbole(operateur) + " " + droite.getString();
	}
	
	

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof CVariable)) {
			return false;
		}
		CVariable castOther = (CVariable) other;
		return Objects.equals(gauche, castOther.gauche) && Objects.equals(operateur, castOther.operateur)
				&& Objects.equals(droite, castOther.droite);
	}

	@Override
	public int hashCode() {
		return Objects.hash(gauche, operateur, droite);
	}

	
}
