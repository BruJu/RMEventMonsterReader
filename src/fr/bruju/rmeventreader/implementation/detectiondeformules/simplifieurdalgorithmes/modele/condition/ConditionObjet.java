package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

import fr.bruju.rmdechiffreur.modele.Condition.CondHerosPossedeObjet;

public class ConditionObjet implements Condition {
	private final CondHerosPossedeObjet condObjet;
	private final boolean estVrai;

	public ConditionObjet(CondHerosPossedeObjet condHerosPossedeObjet) {
		this.condObjet = condHerosPossedeObjet;
		this.estVrai = true;
	}

	public ConditionObjet(ConditionObjet conditionDeBase) {
		this.condObjet = conditionDeBase.condObjet;
		this.estVrai = !conditionDeBase.estVrai;
	}

	@Override
	public String getString() {
		return PROJET.extraireHeros(condObjet.idHeros) + " a " + PROJET.extraireObjet(condObjet.idObjet);
	}

	@Override
	public Boolean tester() {
		return null;
	}

	@Override
	public ConditionObjet inverser() {
		return new ConditionObjet(this);
	}

}
