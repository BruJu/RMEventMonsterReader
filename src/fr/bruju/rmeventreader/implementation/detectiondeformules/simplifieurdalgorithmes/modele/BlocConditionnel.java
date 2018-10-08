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
	public void append(ListeurDInstructions sb) {
		sb.append("Si ").append(condition.getString()).ln().tab();
		siVrai.append(sb);
		sb.ln()
		  .retrait().append("Sinon").tab().ln();
		siFaux.append(sb);
		sb.ln()
		  .retrait().append("Fin si").ln();
	}
	
	
	
	
}
