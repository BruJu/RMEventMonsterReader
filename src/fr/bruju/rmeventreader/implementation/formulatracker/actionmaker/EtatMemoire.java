package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BinaryOperator;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class EtatMemoire {
	/* ==================
	 * ETAT DE LA MEMOIRE
	 * ================== */

	private EtatMemoire pere;

	private Map<Integer, Valeur> variables;
	private Map<Integer, Bouton> interrupteurs;

	private Condition condition = null;
	private EtatMemoire filsGauche = null;
	private EtatMemoire filsDroit = null;

	public EtatMemoire(Map<Integer, Valeur> variablesExistantes, Map<Integer, Bouton> interrupteursExistants) {
		this.variables = variablesExistantes;
		this.interrupteurs = interrupteursExistants;
		this.pere = null;
	}

	public EtatMemoire(EtatMemoire pere) {
		this.pere = pere;
		this.variables = new HashMap<>();
		this.interrupteurs = new HashMap<>();
	}

	/* =================
	 * STRUCTURE D'ARBRE
	 * ================= */

	public EtatMemoire creerFils(Condition condition) {
		this.condition = condition;
		filsGauche = new EtatMemoire(this);
		filsDroit = new EtatMemoire(this);

		return filsGauche;
	}

	/**
	 * Permet d'accéder au petit frère (fils droit du père) de cet état.
	 * 
	 * @return this.pere.filsDroit
	 */
	public EtatMemoire getPetitFrere() {
		return this.pere.filsDroit;
	}

	private boolean estFilsGauche() {
		return this == pere.filsGauche;
	}

	/* ==================
	 * ACCES A LA MEMOIRE
	 * ================== */

	public Valeur getVariable(Integer idVariable) {
		return extraireDonnee(this, idVariable, etat -> etat.variables, numero -> new VBase(numero));
	}

	public Bouton getInterrupteur(int idSwitch) {
		return extraireDonnee(this, idSwitch, etat -> etat.interrupteurs, numero -> new BBase(numero));
	}

	private static <T> T extraireDonnee(EtatMemoire courant, Integer numero,
			Function<EtatMemoire, Map<Integer, T>> fonctionDacces, Function<Integer, T> fonctionDeCreation) {
		T donnee;
		while (true) {
			donnee = fonctionDacces.apply(courant).get(numero);

			if (donnee == null) {
				if (courant.pere == null) {
					fonctionDacces.apply(courant).put(numero, fonctionDeCreation.apply(numero));
				} else {
					courant = courant.pere;
				}
			} else {
				return donnee;
			}
		}
	}

	public EtatMemoire revenirAuPere() {
		pere.integrerFils();
		return pere;
	}

	private void integrerFils() {
		// Combinaisons
		combinerDonnees(this, etat -> etat.variables, (v1, v2) -> new VTernaire(condition, v1, v2));
		combinerDonnees(this, etat -> etat.interrupteurs, (v1, v2) -> new BTernaire(condition, v1, v2));

		// Supression des enfants
		this.condition = null;
		this.filsGauche = null;
		this.filsDroit = null;
	}

	private static <T> void combinerDonnees(EtatMemoire pere, Function<EtatMemoire, Map<Integer, T>> fonctionDacces,
			BinaryOperator<T> fonctionDeFusion) {
		Map<Integer, T> mapPere = fonctionDacces.apply(pere);

		// Combinaison des deux fils
		Map<Integer, Pair<T, T>> nouvellesDonnees = new HashMap<>();

		fonctionDacces.apply(pere.filsGauche).forEach(
				(idVariable, valeur) -> nouvellesDonnees.put(idVariable, new Pair<>(valeur, mapPere.get(idVariable))));

		fonctionDacces.apply(pere.filsDroit).forEach((idVariable, valeur) -> nouvellesDonnees.merge(idVariable,
				new Pair<>(mapPere.get(idVariable), valeur), (v1, v2) -> new Pair<>(v1.getLeft(), v2.getRight())));

		// Modification du père
		nouvellesDonnees.forEach(
				(idVar, paire) -> mapPere.put(idVar, fonctionDeFusion.apply(paire.getLeft(), paire.getRight())));
	}

	public void affecterVariable(Integer variable, Operator operateur, Valeur vDroite) {
		Valeur vGauche = getVariable(variable);

		variables.put(variable, new VCalcul(vGauche, operateur, vDroite));
	}

	public List<Condition> construireListeDeConditions() {
		List<Condition> conditions = construireListe(this, etat -> etat.condition, etat -> etat.condition.revert());
		Collections.reverse(conditions);
		return conditions;
	}

	public static <T> List<T> construireListe(EtatMemoire etat, Function<EtatMemoire, T> elementAjouteSiGauche,
			Function<EtatMemoire, T> elementAjouteSiDroite) {
		List<T> conditions = new ArrayList<>();

		while (etat.pere != null) {
			conditions
					.add(etat.estFilsGauche() ? elementAjouteSiGauche.apply(etat) : elementAjouteSiDroite.apply(etat));

			etat = etat.pere;
		}

		return conditions;
	}

}
