package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

public class BlocConditionnel {
	public final Condition condition;
	public final Algorithme siVrai;
	public final Algorithme siFaux;
	public BlocConditionnel(Condition condition, Algorithme siVrai, Algorithme siFaux) {
		this.condition = condition;
		this.siVrai = siVrai;
		this.siFaux = siFaux;
	}
	
	
}
