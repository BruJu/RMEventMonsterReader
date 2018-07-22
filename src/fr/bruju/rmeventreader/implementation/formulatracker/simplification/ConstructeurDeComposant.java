package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import java.util.Stack;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

public abstract class ConstructeurDeComposant implements VisiteurDeComposants {
	protected Stack<Composant> pile;

	protected boolean conditionFlag; // Si vrai, la condition est vérifiée. Si faux la condition n'est pas vérifiée

	protected ConstructeurDeComposant() {
		pile = new Stack<>();
	}

	/* ========
	 * FEUILLES
	 * ======== */

	@Override
	public void visit(BBase composant) {
		pile.push(composant);
	}

	@Override
	public void visit(BConstant composant) {
		pile.push(composant);
	}

	@Override
	public void visit(VStatistique composant) {
		pile.push(composant);
	}

	@Override
	public void visit(VConstante composant) {
		pile.push(composant);
	}

	@Override
	public void visit(VBase composant) {
		pile.push(composant);
	}

	@Override
	public void visit(VAleatoire composant) {
		pile.push(composant);
	}

	/* ======
	 * CALCUL
	 * ====== */

	@Override
	public void visit(VCalcul vCalcul) {
		vCalcul.gauche.accept(this);
		vCalcul.droite.accept(this);

		Valeur nouvelleDroite = (Valeur) pile.pop();
		Valeur nouvelleGauche = (Valeur) pile.pop();

		if (nouvelleGauche == vCalcul.gauche && nouvelleDroite == vCalcul.droite) {
			pile.push(vCalcul);
		} else {
			pile.push(new VCalcul(nouvelleGauche, vCalcul.operateur, nouvelleDroite));
		}
	}

	/* =========
	 * TERNAIRES
	 * ========= */

	@Override
	public void visit(VTernaire vTernaire) {
		visitePar(vTernaire, (condition, vrai, faux) -> new VTernaire(condition, vrai, faux));
	}

	@Override
	public void visit(BTernaire bTernaire) {
		visitePar(bTernaire, (condition, vrai, faux) -> new BTernaire(condition, vrai, faux));
	}

	@SuppressWarnings("unchecked")
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
			visit(ternaire.siVrai);
			T faux = null;
			
			if (ternaire.siFaux != null) {
				visit(ternaire.siFaux);
				faux = (T) pile.pop();
			}
			
			T vrai = (T) pile.pop();

			if (condition == ternaire.condition && faux == ternaire.siFaux && vrai == ternaire.siVrai) {
				pile.push(ternaire);
			} else {
				pile.push(creation.apply(condition, vrai, faux));
			}
		}
	}

	/* ==========
	 * CONDITIONS
	 * ========== */

	@Override
	public void visit(CArme cArme) {
		pile.push(cArme);
	}

	@Override
	public void visit(CSwitch cSwitch) {
		visit(cSwitch.interrupteur);

		Bouton interrupteur = (Bouton) pile.pop();

		if (interrupteur == null) {
			// TODO : on trouvera jamias null ici. Il faut faire un évaluateur de bouton
			pile.push(null);
			// (nouveau)conditionFlag = (ancien)conditionFlag;
		} else if (interrupteur == cSwitch) {
			pile.push(cSwitch);
		} else {
			pile.push(new CSwitch(interrupteur, cSwitch.valeur));
		}
	}

	@Override
	public void visit(CVariable cVariable) {
		visit(cVariable.gauche);
		visit(cVariable.droite);

		Valeur droite = (Valeur) pile.pop();
		Valeur gauche = (Valeur) pile.pop();

		if (gauche == cVariable.gauche && droite == cVariable.droite) {
			pile.push(cVariable);
		} else {
			CVariable condition = new CVariable(gauche, cVariable.operateur, droite);
			Boolean r = tenterDEvaluer(condition);

			if (r != null) {
				pile.push(null);
				conditionFlag = r;
			} else {
				pile.push(condition);
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
}
