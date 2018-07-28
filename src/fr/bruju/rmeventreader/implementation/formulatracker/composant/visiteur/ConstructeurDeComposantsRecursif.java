package fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur;

import java.util.function.Function;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.ComposantEtendu;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Borne;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_Entre;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

public class ConstructeurDeComposantsRecursif extends VisiteurRetourneur<Composant> {
	private static EvaluationRapide fastEval = EvaluationRapide.getInstance();

	protected Composant modifier(BBase boutonBase) {
		return boutonBase;
	}

	protected Composant modifier(VBase variableBase) {
		return variableBase;
	}

	protected Composant modifier(VAleatoire variableAleatoire) {
		return variableAleatoire;
	}

	protected Composant modifier(VConstante variableConstante) {
		return variableConstante;
	}

	protected Composant modifier(VStatistique variableStatistique) {
		return variableStatistique;
	}

	protected Composant modifier(BConstant boutonConstant) {
		return boutonConstant;
	}
	
	protected Composant modifier(BStatistique boutonTernaire) {
		return boutonTernaire;
	}

	protected Composant modifier(CArme conditionArme) {
		return conditionArme;
	}
	
	protected Composant modifier(BTernaire boutonTernaire) {
		return boutonTernaire;
	}

	protected Composant modifier(VTernaire variableTernaire) {
		return variableTernaire;
	}

	protected Composant modifier(VCalcul variableCalcul) {
		return variableCalcul;
	}

	protected Composant modifier(CVariable conditionVariable) {
		return conditionVariable;
	}

	protected Composant modifier(CSwitch conditionSwitch) {
		return conditionSwitch;
	}

	protected Composant modifier(E_Borne borne) {
		return borne;
	}
	
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
			resultats[i] = fastEval.traiter(traiter(fils[i]));
			
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
	

	@SuppressWarnings("unchecked")
	private <T extends Composant, U extends Composant> Composant transformerTernaire(
			TriFunction<Condition, U, U, T> getPere,
			T elementBase, Condition condition, U vrai, U faux,
			Function<T, Composant> transformation) {		
		Condition ct = (Condition) fastEval.traiter(traiter(condition));
		
		if (ct == null)
			return null;
		
		Boolean id = CFixe.identifier(ct);
		
		if (id != null) {
			return id ? fastEval.traiter(traiter(vrai)) : fastEval.traiter(traiter(faux));
		}
		
		U vt = (U) fastEval.traiter(traiter(vrai));
		U vf = (U) fastEval.traiter(traiter(faux));
		
		if (vt == null || vf == null) {
			return null;
		}
		
		return transformation.apply(getPere.apply(ct, vt, vf));
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
