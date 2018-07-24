package fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.EvaluateurSimple;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

/**
 * Il s'agit d'une base de constructeur de composants. Elle dispose d'une implémentation par défaut pour toutes les
 * méthodes qui consiste à explorer les fils, puis à renvoyer une nouvelle instance du composant avec les fils reçus. Si
 * les fils sont les mêmes que présents, une nouvelle instance n'est pas créée. Si null est trouvé à une étape, le
 * constructeur de composants renverra null si il n'y était pas déjà.
 * 
 * @author Bruju
 *
 */
public abstract class ConstructeurDeComposantR extends VisiteurRetourneur<Composant, Composant> {

	@Override
	protected final Composant transformer(Composant composant) {
		return composant;
	}

	// Feuilles

	@Override
	protected Composant traiter(BBase boutonBase) {
		return boutonBase;
	}

	@Override
	protected Composant traiter(BConstant boutonConstant) {
		return boutonConstant;
	}

	@Override
	protected Composant traiter(VConstante variableConstante) {
		return variableConstante;
	}

	@Override
	protected Composant traiter(VStatistique variableStatistique) {
		return variableStatistique;
	}

	@Override
	protected Composant traiter(VAleatoire variableAleatoire) {
		return variableAleatoire;
	}

	@Override
	protected Composant traiter(VBase variableBase) {
		return variableBase;
	}

	@Override
	protected Composant traiter(CArme conditionArme) {
		return conditionArme;
	}

	// Feuilles

	@Override
	protected Composant traiter(CSwitch cSwitch) {
		Bouton b = (Bouton) traiter(cSwitch.interrupteur);

		if (b == cSwitch.interrupteur) {
			return cSwitch;
		}

		if (b == null)
			return null;
		
		Boolean evaluation = evaluer(b);

		if (evaluation == null) {
			return new CSwitch(b, cSwitch.valeur);
		} else {
			return CFixe.get(evaluation == cSwitch.valeur);
		}
	}

	private Boolean evaluer(Bouton interrupteur) {
		if (interrupteur == BConstant.get(true)) {
			return true;
		} else if (interrupteur == BConstant.get(false)) {
			return false;
		}

		return null;
	}

	@Override
	protected Composant traiter(BTernaire boutonTernaire) {
		return visitePar(boutonTernaire, (condition, vrai, faux) -> new BTernaire(condition, vrai, faux));
	}

	@SuppressWarnings("unchecked")
	protected <T extends Composant> Composant visitePar(ComposantTernaire<T> ternaire,
			TriFunction<Condition, T, T, T> creation) {
		Condition condition = (Condition) traiter(ternaire.condition);

		Boolean composantBooleen = CFixe.identifier(condition);

		if (composantBooleen != null) {
			return composantBooleen ? traiter(ternaire.siVrai) : traiter(ternaire.siFaux);
		}

		T vrai;
		T faux;

		vrai = (T) traiter(ternaire.siVrai);
		if (vrai == null) {
			return null;
		}

		faux = (T) traiter(ternaire.siFaux);
		if (faux == null) {
			return null;
		}

		if (ternaire.condition == condition && ternaire.siVrai == vrai && ternaire.siFaux == faux) {
			return ternaire;
		}

		return creation.apply(condition, vrai, faux);
	}

	@Override
	protected Composant traiter(VTernaire variableTernaire) {
		return visitePar(variableTernaire, (condition, vrai, faux) -> new VTernaire(condition, vrai, faux));
	}

	@Override
	protected Composant traiter(CVariable cVariable) {
		Valeur gauche = (Valeur) traiter(cVariable.gauche);
		Valeur droite = (Valeur) traiter(cVariable.droite);

		if (gauche == cVariable.gauche && droite == cVariable.droite) {
			return cVariable;
		} else {
			CVariable condition = new CVariable(gauche, cVariable.operateur, droite);
			Boolean r = tenterDEvaluer(condition);

			if (r != null) {
				return CFixe.get(r);
			} else {
				return condition;
			}
		}
	}

	private Boolean tenterDEvaluer(CVariable condition) {
		Valeur gauche = condition.gauche;
		Valeur droite = condition.droite;

		EvaluateurSimple evaluateur = new EvaluateurSimple();

		Integer gVal = evaluateur.evaluer(gauche);
		if (gVal == null) {
			return null;
		}

		Integer dVal = evaluateur.evaluer(droite);

		if (dVal == null) {
			return null;
		}

		return condition.operateur.test(gVal, dVal);
	}

	@Override
	protected Composant traiter(VCalcul vCalcul) {
		Valeur gauche = (Valeur) traiter(vCalcul.gauche);
		if (gauche == null)
			return null;
		
		Valeur droite = (Valeur) traiter(vCalcul.droite);
		if (droite == null)
			return null;

		if (gauche == vCalcul.gauche && droite == vCalcul.droite) {
			return vCalcul;
		} else {
			return new VCalcul(gauche, vCalcul.operateur, droite);
		}
	}
}
