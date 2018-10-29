package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VisiteurReecrivainDExpression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.Individu;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.Personnage;

public class ContexteDeSubstitution extends VisiteurReecrivainDExpression {

	private final Individu individuSource;
	private final Individu individuDestination;

	public ContexteDeSubstitution(Individu individuSource, Individu individuDestination) {
		this.individuSource = individuSource;
		this.individuDestination = individuDestination;
	}

	@Override
	public Expression explorer(ExprVariable composant) {
		return super.explorer(composant);
	}

	@Override
	public Expression explorer(Statistique composant) {
		return super.explorer(composant);
	}
}
