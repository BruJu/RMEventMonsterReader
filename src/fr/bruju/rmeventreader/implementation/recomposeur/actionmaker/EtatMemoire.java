package fr.bruju.rmeventreader.implementation.recomposeur.actionmaker;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;

public class EtatMemoire {
	public EtatMemoire(Map<Integer, Valeur> variablesExistantes, Map<Integer, Bouton> interrupteursExistants) {
	}

	public Valeur getVariable(int i) {
		return null;
	}

	public EtatMemoire getPetitFrere() {
		return null;
	}

	public EtatMemoire revenirAuPere() {
		return null;
	}

	public Bouton getInterrupteur(int numero) {
		return null;
	}

	public void affecterVariable(Integer variable, Operator operator, Valeur vDroite) {
	}

	public EtatMemoire creerFils(Condition condition) {
		return null;
	}

}
