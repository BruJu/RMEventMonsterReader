package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDAlgorithme;
import fr.bruju.util.IndentedStringBuilder;

/**
 * Un bloc conditionnel est une instruction possédant une condition et deux sous algorithmes lisant les instructions
 * à exécuter si le bloc conditionnel est vrai ou faux.
 */
public class BlocConditionnel implements InstructionGenerale {
	/** Condition */
	public final Condition condition;
	/** Instructions à exécuter si la condition est vraie */
	public final Algorithme siVrai;
	/** Instructions à exécuter si la condition est fausse */
	public final Algorithme siFaux;

	/**
	 * Crée un bloc conditionnel
	 * @param condition La condition
	 * @param siVrai Les instructions si la condition est vraie
	 * @param siFaux Les instructions si la condition est fausse
	 */
	public BlocConditionnel(Condition condition, Algorithme siVrai, Algorithme siFaux) {
		this.condition = condition;
		this.siVrai = siVrai;
		this.siFaux = siFaux;
	}
	
	@Override
	public void listerTextuellement(IndentedStringBuilder listeur) {
		if (estVide()) {
			return;
		}
		
		listeur.append("Si ").append(condition.getString()).tab().ln();
		siVrai.lister(listeur);
		
		if (!siFaux.estVide()) {
			listeur.untab().append("Sinon").tab().ln();
			siFaux.lister(listeur);
		}
		
		listeur.untab().append("Fin si").ln();
	}

	@Override
	public boolean estVide() {
		return siVrai.estVide() && siFaux.estVide();
	}

	@Override
	public void accept(VisiteurDAlgorithme visiteur) {
		visiteur.visit(this);
	}

	@Override
	public boolean estIdentique(InstructionGenerale instructionGenerale) {
		if (!(instructionGenerale instanceof BlocConditionnel)) {
			return false;
		}

		BlocConditionnel autre = (BlocConditionnel) instructionGenerale;

		return condition.equals(autre.condition)
				&& siVrai.estIdentique(autre.siVrai)
				&& siFaux.estIdentique(autre.siFaux);
	}
}
