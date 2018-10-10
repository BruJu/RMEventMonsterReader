package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;

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
		if (estVide()) {
			return;
		}
		
		sb.append("Si ").append(condition.getString()).tab().ln();
		siVrai.append(sb);
		
		if (!siFaux.estVide()) {
			sb.retrait().append("Sinon").tab().ln();
			siFaux.append(sb);
		}
		
		sb.retrait().append("Fin si").ln();
	}

	@Override
	public boolean estVide() {
		return siVrai.estVide() && siFaux.estVide();
	}

	@Override
	public void accept(VisiteurDAlgorithme visiteur) {
		visiteur.visit(this);
	}
}
