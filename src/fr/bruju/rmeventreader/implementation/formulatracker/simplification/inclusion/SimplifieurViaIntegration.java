package fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposant;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions.CreateurDeGestionnaire;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.inclusion.gestionnairesdeconditions.GestionnaireDeCondition;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

public class SimplifieurViaIntegration extends ConstructeurDeComposant implements Integreur {
	private List<GestionnaireDeCondition> gestionnaires;
	private CreateurDeGestionnaire createur = new CreateurDeGestionnaire();

	@SuppressWarnings("unchecked")
	@Override
	protected <T extends Composant> void visitePar(ComposantTernaire<T> ternaire,
			TriFunction<Condition, T, T, T> creation) {
		visit(ternaire.condition);

		Condition condition = (Condition) pile.pop();

		if (condition == null) {
			if (conditionFlag) {
				visit(ternaire.siVrai);
			} else {
				visit(ternaire.siFaux);
			}
		} else {
			gestionnaires.add(createur.getGestionnaire(this, condition));
			
			visit(ternaire.siVrai);
			T faux = null;
			
			if (ternaire.siFaux != null) {
				visit(ternaire.siFaux);
				faux = (T) pile.pop();
			}
			
			T vrai = (T) pile.pop();
			

			gestionnaires.remove(gestionnaires.size() - 1);

			if (condition == ternaire.condition && faux == ternaire.siFaux && vrai == ternaire.siVrai) {
				pile.push(ternaire);
			} else {
				pile.push(creation.apply(condition, vrai, faux));
			}
		}
	}

	public Composant integerer(Composant valeur) {
		gestionnaires = new ArrayList<>();
		valeur.accept(this);
		return pile.pop();
	}

	public void gestionnairePush(Condition condition, boolean reponse) {
		pile.push(condition);
		conditionFlag = reponse;
	}

	@Override
	public void visit(CArme cArme) {
		traiterCondition(cArme, (g, c) -> g.conditionArme(c));
	}

	@Override
	public void visit(CSwitch cSwitch) {
		traiterCondition(cSwitch, (g, c) -> g.conditionSwitch(c));
	}

	@Override
	public void visit(CVariable cVariable) {
		traiterCondition(cVariable, (g, c) -> g.conditionVariable(c));
	}

	private <T extends Condition> void traiterCondition(T condition,
			BiConsumer<GestionnaireDeCondition, T> funcTraitement) {
		Condition resultat = integrerUneCondition(condition, funcTraitement);

		if (resultat == null) {
			gestionnairePush(null, conditionFlag);
			return;
		}

		pile.push(resultat);
	}

	@SuppressWarnings("unchecked")
	private <T extends Condition> Condition integrerUneCondition(T condition,
			BiConsumer<GestionnaireDeCondition, T> funcTraitement) {
		T condActuelle = condition;

		for (GestionnaireDeCondition gestionnaire : gestionnaires) {
			funcTraitement.accept(gestionnaire, condActuelle);

			condActuelle = (T) this.pile.pop();

			if (condActuelle == null) {
				return null;
			}
		}

		return condActuelle;
	}

	public void refuse(CArme cArme) {
		super.visit(cArme);
	}

	public void refuse(CSwitch cSwitch) {
		super.visit(cSwitch);
	}

	public void refuse(CVariable cVariable) {
		super.visit(cVariable);
	}
}
