package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantOpere;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
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
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurRetourneur;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.PersonnageReel;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.PersonnageUnifie;
import fr.bruju.rmeventreader.utilitaire.lambda.TriFunction;

public class Unificateur extends VisiteurRetourneur<Composant> {
	private Composant secondComposant;
	Map<Set<PersonnageReel>, PersonnageUnifie> personnagesUnifies = new HashMap<>();
	
	
	
	
	public void exploiter(Composant second) {
		this.secondComposant = second;
	}


	/* ======
	 * NOEUDS
	 * ====== */

	@SuppressWarnings("unchecked")
	private <T extends Composant, U extends Composant> T explorerFils(U premier, U second,
			Function<U, T> fonctionExploration) {
		secondComposant = fonctionExploration.apply(second);
		return (T) traiter(fonctionExploration.apply(premier));
	}

	private <ValeursDeTernaire extends Composant, TypeTernaire extends ComposantTernaire<ValeursDeTernaire>>

			TypeTernaire traiterTernaire(TypeTernaire premier, TypeTernaire second,
					TriFunction<Condition, ValeursDeTernaire, ValeursDeTernaire, TypeTernaire> fonctionInstanciation) {

		// Condition
		Condition cond = explorerFils(premier, second, composant -> composant.condition);
		if (cond == null)
			return null;

		ValeursDeTernaire siVrai = explorerFils(premier, second, composant -> composant.siVrai);
		if (siVrai == null)
			return null;

		ValeursDeTernaire siFaux;
		if (premier.siFaux == null) {
			if (second.siFaux == null) {
				siFaux = null;
			} else {
				return null;
			}
		} else {
			if (second.siFaux == null) {
				return null;
			} else {
				siFaux = explorerFils(premier, second, composant -> composant.siFaux);
			}
		}

		return fonctionInstanciation.apply(cond, siVrai, siFaux);
	}

	@Override
	protected Composant traiter(BTernaire premier) {
		if (!(secondComposant instanceof BTernaire))
			return null;

		BTernaire second = (BTernaire) secondComposant;

		return traiterTernaire(premier, second, (c, v, f) -> new BTernaire(c, v, f));
	}

	@Override
	protected Composant traiter(VTernaire premier) {
		if (!(secondComposant instanceof VStatistique))
			return null;

		VTernaire second = (VTernaire) secondComposant;

		return traiterTernaire(premier, second, (c, v, f) -> new VTernaire(c, v, f));
	}

	private <VC extends ComposantOpere> VC traiterOpere(VC premier, VC second, Function<VC, Valeur> getGauche,
			Function<VC, Operator> getOperateur, Function<VC, Valeur> getDroite,
			TriFunction<Valeur, Operator, Valeur, VC> fonctionDeCreation) {

		if (getOperateur.apply(premier) != getOperateur.apply(second)) {
			return null;
		}

		Valeur gauche = explorerFils(premier, second, getGauche);
		if (gauche == null)
			return null;

		Valeur droite = explorerFils(premier, second, getDroite);
		if (droite == null)
			return null;

		return fonctionDeCreation.apply(gauche, getOperateur.apply(premier), droite);
	}

	@Override
	protected Composant traiter(VCalcul premier) {
		if (!(secondComposant instanceof VCalcul))
			return null;

		VCalcul second = (VCalcul) secondComposant;

		return traiterOpere(premier, second, v -> v.gauche, v -> v.operateur, v -> v.droite,
				(g, o, d) -> new VCalcul(g, o, d));
	}

	@Override
	protected Composant traiter(CArme conditionArme) {
		return (conditionArme.equals(secondComposant)) ? conditionArme : null;
	}

	@Override
	protected Composant traiter(CSwitch premier) {
		if (!(secondComposant instanceof CSwitch))
			return null;

		CSwitch second = (CSwitch) secondComposant;

		if (premier.valeur != second.valeur) {
			return null;
		}

		Bouton bouton = explorerFils(premier, second, v -> v.interrupteur);

		if (bouton == null)
			return null;

		return new CSwitch(bouton, premier.valeur);
	}

	@Override
	protected Composant traiter(CVariable premier) {
		if (!(secondComposant instanceof CVariable))
			return null;

		CVariable second = (CVariable) secondComposant;

		return traiterOpere(premier, second, v -> v.gauche, v -> v.operateur, v -> v.droite,
				(g, o, d) -> new CVariable(g, o, d));
	}

	/* =============
	 * VSTATISTIQUES
	 * ============= */

	@Override
	protected Composant traiter(VStatistique vStat) {
		if (!(secondComposant instanceof VStatistique))
			return null;

		VStatistique autreS = (VStatistique) secondComposant;

		if (!vStat.statistique.nom.equals(autreS.statistique.nom)) {
			return null;
		}

		Set<PersonnageReel> groupement = new TreeSet<>();
		vStat.statistique.possesseur.getPersonnagesReels().stream().forEach(groupement::add);
		autreS.statistique.possesseur.getPersonnagesReels().stream().forEach(groupement::add);

		PersonnageUnifie perso = personnagesUnifies.get(groupement);
		if (perso == null) {
			perso = new PersonnageUnifie(groupement);
			personnagesUnifies.put(groupement, perso);
		}

		return new VStatistique(perso.getStatistiques().get(vStat.statistique.nom));
	}

	/* ========
	 * FEUILLES
	 * ======== */

	@Override
	protected Composant traiter(BBase boutonBase) {
		if (!boutonBase.equals(secondComposant)) {
			return null;
		}

		return boutonBase;
	}

	@Override
	protected Composant traiter(BConstant boutonConstant) {
		if (!boutonConstant.equals(secondComposant)) {
			return null;
		}

		return boutonConstant;
	}

	@Override
	protected Composant traiter(VAleatoire variableAleatoire) {
		if (!variableAleatoire.equals(secondComposant)) {
			return null;
		}

		return variableAleatoire;
	}

	@Override
	protected Composant traiter(VBase variableBase) {
		if (!variableBase.equals(secondComposant)) {
			return null;
		}

		return variableBase;
	}

	@Override
	protected Composant traiter(VConstante variableConstante) {
		if (!variableConstante.equals(secondComposant)) {
			return null;
		}

		return variableConstante;
	}

}
