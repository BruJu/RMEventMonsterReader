package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

public class BlocConditionnel implements InstructionGenerale {
	public final Condition condition;
	public final Algorithme siVrai;
	public final Algorithme siFaux;
	public BlocConditionnel(Condition condition, Algorithme siVrai, Algorithme siFaux) {
		this.condition = condition;
		this.siVrai = siVrai;
		this.siFaux = siFaux;
	}
	
	@Override
	public void append(StringBuilder sb) {
		sb.append("Si " + condition.getString() + "\n")
		  .append(siVrai.getString())
		  .append("Sinon\n")
		  .append(siFaux.getString())
		  .append("Fin si");
	}
	
	
	
	
}
