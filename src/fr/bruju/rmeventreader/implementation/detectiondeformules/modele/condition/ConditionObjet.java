package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

import fr.bruju.rmdechiffreur.modele.Condition.CondHerosPossedeObjet;

import java.util.Objects;

/**
 * Une condition portant sur le port d'un objet chez un personnage.
 * <br>Cette classe adapte l'objet CondHerosPossedeObjet de RMDechiffreur à l'interface Condition
 */
public class ConditionObjet implements Condition {
	private final CondHerosPossedeObjet condObjet;

	public ConditionObjet(CondHerosPossedeObjet condHerosPossedeObjet) {
		this.condObjet = condHerosPossedeObjet;
	}

	@Override
	public String getString() {
		return PROJET.extraireHeros(condObjet.idHeros) + " a " + PROJET.extraireObjet(condObjet.idObjet);
	}

	@Override
	public Boolean evaluer() {
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ConditionObjet that = (ConditionObjet) o;
		return Objects.equals(condObjet, that.condObjet);
	}

	@Override
	public int hashCode() {
		return Objects.hash(condObjet);
	}
}
