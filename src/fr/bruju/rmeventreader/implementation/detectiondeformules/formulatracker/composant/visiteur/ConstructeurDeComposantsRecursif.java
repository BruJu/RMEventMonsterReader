package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.visiteur;

import java.util.function.Function;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.etendu.ComposantEtendu;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.etendu.E_Borne;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.etendu.E_Entre;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

public class ConstructeurDeComposantsRecursif extends VisiteurRetourneur<Composant> {
	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(BBase boutonBase) {
		return boutonBase;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(VBase variableBase) {
		return variableBase;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(VAleatoire variableAleatoire) {
		return variableAleatoire;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(VConstante variableConstante) {
		return variableConstante;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(VStatistique variableStatistique) {
		return variableStatistique;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(BConstant boutonConstant) {
		return boutonConstant;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(BStatistique boutonTernaire) {
		return boutonTernaire;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(CArme conditionArme) {
		return conditionArme;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(BTernaire boutonTernaire) {
		return boutonTernaire;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(VTernaire variableTernaire) {
		return variableTernaire;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(VCalcul variableCalcul) {
		return variableCalcul;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(CVariable conditionVariable) {
		return conditionVariable;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(CSwitch conditionSwitch) {
		return conditionSwitch;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(E_Borne borne) {
		return borne;
	}

	/**
	 * Transformation appliquée lors de la modification du composant, aprés le traîtement des éventuels fils
	 */
	protected Composant modifier(E_Entre entre) {
		return entre;
	}

	/* ========
	 * FEUILLES
	 * ======== */
	
	@Override
	protected final Composant traiter(BBase boutonBase) {
		return modifier(boutonBase);
	}

	@Override
	protected final Composant traiter(VAleatoire variableAleatoire) {
		return modifier(variableAleatoire);
	}

	@Override
	protected final Composant traiter(VBase variableBase) {
		return modifier(variableBase);
	}

	@Override
	protected final Composant traiter(VConstante variableConstante) {
		return modifier(variableConstante);
	}

	@Override
	protected final Composant traiter(VStatistique variableStatistique) {
		return modifier(variableStatistique);
	}	

	@Override
	protected final Composant traiter(BConstant boutonConstant) {
		return modifier(boutonConstant);
	}

	@Override
	protected final Composant traiter(BStatistique boutonTernaire) {
		return modifier(boutonTernaire);
	}

	@Override
	protected final Composant traiter(CArme conditionArme) {
		return modifier(conditionArme);
	}
	
	

	
	/* ======
	 * NOEUDS
	 * ====== */

	/**
	 * Transforme l'élément composé en un nouvel élément composé
	 * @param getFils Fonction donnant la liste des fils de l'élément
	 * @param getPere Fonction permettant de reconstituer un père à partir des fils reconstitués
	 * @param elementBase Element de base
	 * @param transformation Fonction de transformation appliquée au père après traitement de ses fils et recomposition
	 * @return L'élément recomposé
	 */
	private <T extends Composant> Composant transformerElementCompose(
			Function<T, Composant[]> getFils,
			Function<Composant[], T> getPere,
			T elementBase,
			Function<T, Composant> transformation) {
		
		// Traiter les fils
		Composant[] fils = getFils.apply(elementBase);
		Composant[] resultats = new Composant[fils.length];

		boolean sontIdentiques = true;
		
		for (int i = 0 ; i != fils.length ; i++) {
			resultats[i] = traiter(fils[i]).evaluationRapide();
			
			if (resultats[i] == null) {
				return null;
			}
			
			sontIdentiques &= (resultats[i] == fils[i]);
		}
		
		T elementReforge = sontIdentiques ? elementBase : getPere.apply(resultats);
		
		// Traiter l'élément actuel
		Composant elementTraite = transformation.apply(elementReforge);
		
		// Retour
		return elementTraite;
	}
	

	/**
	 * Fonction permettant de traiter les ternaires
	 * @param getPere Fonction reconstituant le père aprés traîtement des fils
	 * @param elementBase Ternaire de base
	 * @param condition Condition
	 * @param vrai Valeur si vraie
	 * @param faux Valeur si faux
	 * @param transformation Fonction de transformation appliquée à la ternaire
	 * @return La ternaire transformée. Eventuellement vrai ou faux traités si la condition a pu être évaluée
	 */
	@SuppressWarnings("unchecked")
	private <T extends Composant, U extends Composant> Composant transformerTernaire(
			TriFunction<Condition, U, U, T> getPere,
			T elementBase, Condition condition, U vrai, U faux,
			Function<T, Composant> transformation) {
		Condition ct = (Condition) traiter(condition).evaluationRapide();
		
		if (ct == null)
			return null;
		
		Boolean id = CFixe.identifier(ct);
		
		if (id != null) {
			U reponse;
			if (id) {
				ternaireAvantVrai(condition);
				reponse = (U) traiter(vrai).evaluationRapide();
				ternaireApres(condition);
			} else {
				ternaireAvantFaux(condition);
				reponse = (U) traiter(faux).evaluationRapide();
				ternaireApres(condition);
			}
			
			return reponse;
		}

		ternaireAvantVrai(condition);
		U vt = (U) traiter(vrai);
		ternaireApres(condition);
		ternaireAvantFaux(condition);
		U vf = (U) traiter(faux);
		ternaireApres(condition);
		
		if (vt == null || vf == null) {
			return null;
		}
		
		if (vt == vrai && vf == faux && ct == condition) {
			return transformation.apply(elementBase);
		}

		vt = (U) vt.evaluationRapide();
		vf = (U) vf.evaluationRapide();
		
		return transformation.apply(getPere.apply(ct, vt, vf));
	}
	
	/**
	 * Méthode appelée aprés la fin du traitement d'une branche d'un traitement ternaire
	 * @param condition Un rappel de la condition qui avait été donnée
	 */
	protected void ternaireApres(Condition condition) {
	}

	/**
	 * Méthode appelée avant le traitement d'une branche faux d'une condition
	 * @param condition La condition non traitée de base (non inversée)
	 */
	protected void ternaireAvantFaux(Condition condition) {
	}

	/**
	 * Méthode appelée avant le traitement d'une branche vraie d'une condition
	 * @param condition La condition non traitée de base
	 */
	protected void ternaireAvantVrai(Condition condition) {
	}

	@Override
	protected final Composant traiter(CSwitch conditionSwitch) {
		return transformerElementCompose(
				c -> new Composant[]{c.interrupteur},
				tableau -> new CSwitch((Bouton) tableau[0], conditionSwitch.valeur),
				conditionSwitch,
				this::modifier);
	}

	@Override
	protected final Composant traiter(CVariable conditionVariable) {
		return transformerElementCompose(
				c -> new Composant[]{c.gauche, c.droite},
				tableau -> new CVariable((Valeur) tableau[0], conditionVariable.operateur, (Valeur) tableau[1]),
				conditionVariable,
				this::modifier);
	}

	@Override
	protected final Composant traiter(VCalcul variableCalcul) {
		return transformerElementCompose(
				c -> new Composant[]{c.gauche, c.droite},
				tableau ->  new VCalcul((Valeur) tableau[0], variableCalcul.operateur, (Valeur) tableau[1]),
				variableCalcul,
				this::modifier);
	}
	
	@Override
	protected final Composant traiter(VTernaire variableTernaire) {
		return transformerTernaire(
				(condition, vrai, faux) -> new VTernaire(condition, vrai, faux),
				variableTernaire, variableTernaire.condition, variableTernaire.siVrai, variableTernaire.siFaux,
				this::modifier);
	}

	@Override
	protected final Composant traiter(BTernaire boutonTernaire) {
		return transformerTernaire(
				(condition, vrai, faux) -> new BTernaire(condition, vrai, faux),
				boutonTernaire, boutonTernaire.condition, boutonTernaire.siVrai, boutonTernaire.siFaux,
				this::modifier);
	}
	
	
	@Override
	protected final Composant traiter(E_Borne borne) {
		return transformerElementCompose(
				c -> new Composant[]{c.valeur, c.borne},
				tableau -> new E_Borne((Valeur) tableau[0], (Valeur) tableau[1], borne.estBorneSup),
				borne,
				this::modifier);
	}
	

	@Override
	protected final Composant traiter(E_Entre borne) {
		return transformerElementCompose(
				c -> new Composant[]{c.borneInf, c.valeur, c.borneSup},
				tableau -> new E_Entre((Valeur) tableau[0], (Valeur) tableau[1], (Valeur) tableau[2]),
				borne,
				this::modifier);
	}

	/* ===============
	 * JAMAIS APPELEES
	 * =============== */

	@Override
	protected Composant composantEtenduNonGere(ComposantEtendu composant) {
		throw new VisiteIllegale();
	}
	
	@Override
	protected Composant comportementParDefaut(Composant composant) {
		throw new VisiteIllegale();
	}

}
