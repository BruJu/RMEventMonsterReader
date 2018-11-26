package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;

public interface VisiteurDAlgorithme {

	
	public default void visit(Algorithme algorithme) {
		algorithme.accept(this);
	}
	
	public default void visit(InstructionGenerale instruction) {
		instruction.accept(this);
	}
	
	public void visit(BlocConditionnel blocConditionnel);

	public void visit(InstructionAffectation instructionAffectation);

	public void visit(InstructionAffichage instructionAffichage);
	
	
	
	public static interface IntegreConditionnel extends VisiteurDAlgorithme {
		
		public default void visit(BlocConditionnel blocConditionnel) {
			conditionDebut(blocConditionnel.condition);
			visit(blocConditionnel.siVrai);
			conditionSinon();
			visit(blocConditionnel.siFaux);
			conditionFin();
		}



		public void conditionDebut(Condition condition);
		public void conditionSinon();
		public void conditionFin();
		
	}
	
	
}
