package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

import fr.bruju.rmdechiffreur.modele.Condition.CondHerosPossedeObjet;

public class ConditionObjet implements Condition {
	private final CondHerosPossedeObjet condObjet;

	public ConditionObjet(CondHerosPossedeObjet condHerosPossedeObjet) {
		this.condObjet = condHerosPossedeObjet;
	}

	@Override
	public String getString() {
		return PROJET.extraireHeros(condObjet.idHeros) + " a " + PROJET.extraireObjet(condObjet.idObjet);
	}

}
