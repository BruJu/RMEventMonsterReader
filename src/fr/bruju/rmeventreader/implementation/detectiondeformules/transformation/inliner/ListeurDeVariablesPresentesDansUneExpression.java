package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.inliner;

import java.util.HashSet;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.*;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.visiteurs.VisiteurDExpression;

/**
 * Un visiteur d'expression qui liste les variables qui sont utilisées
 */
public final class ListeurDeVariablesPresentesDansUneExpression implements VisiteurDExpression {
	/**
	 * Liste toutes les variables apparaissant dans l'expression donnée
	 * @param expression L'expression dont on souhaite connaître les variables la composant
	 * @return La liste des variables composant l'expression
	 */
	public static Set<ExprVariable> lister(Expression expression) {
		ListeurDeVariablesPresentesDansUneExpression listeur = new ListeurDeVariablesPresentesDansUneExpression();
		listeur.visit(expression);
		return listeur.variablesPresentes;
	}

	private ListeurDeVariablesPresentesDansUneExpression() {
		// Cette classe n'est pas instanciable car ça n'a pas de sens de visiter plusieurs expressions différentes dans
		// le contexte actuel
	}

	private final Set<ExprVariable> variablesPresentes = new HashSet<>();

	@Override
	public void visit(Calcul composant) {
		visit(composant.gauche);
		visit(composant.droite);
	}
	
	@Override
	public void visit(ExprVariable composant) {
		variablesPresentes.add(composant);
	}

	@Override
	public void visit(Constante composant) {
	}

	@Override
	public void visit(NombreAleatoire composant) {
	}

	@Override
	public void visit(Borne composant) {
		visit(composant.variable);
		visit(composant.borne);
	}
}
