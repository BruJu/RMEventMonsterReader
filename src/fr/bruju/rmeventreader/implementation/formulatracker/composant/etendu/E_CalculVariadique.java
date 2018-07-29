package fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;
import fr.bruju.rmeventreader.utilitaire.Container;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import java.util.Objects;

// WIP / TODO

/**
 * Représente un calcul variadique
 * 
 * @author Bruju
 *
 */
public class E_CalculVariadique implements ComposantEtendu, Valeur {
	/* ======
	 * STATIC
	 * ====== */

	// Décomposition

	/** Groupes pour l'addition */
	private static final Operator[][] groupesPlus = { { Operator.PLUS, Operator.MINUS } };
	/** Groupes pour la multiplication */
	private static final Operator[][] groupesFois = { { Operator.TIMES }, { Operator.DIVIDE } };

	/* =========
	 * COMPOSANT
	 * ========= */
	
	public final List<List<Pair<Valeur, Operator>>> calcul;
	
	
	/** Opérateur appliqué lors du calcul */
	public final Operator base;
	/** Liste des différentes valeurs */
	//public final List<Valeur> valeurs;
	/**
	 * Première valeur = vrai. Pour les autres, si inverses[i] = faux, alors il faut appliquer l'opérateur de base
	 * inversé pour valeurs[i]
	 */
	//public final List<Boolean> inverses;

	/**
	 * Crée un calcul variadique à partir d'une liste de valeurs et de leurs opérateurs
	 * 
	 * @param base Opérateur de base
	 * @param valeurs Suite de valeurs
	 * @param inverse Suite de booléens disant si il faut prendre l'opérateur de base ou l'inverse
	 */
	public E_CalculVariadique(Operator base, List<Valeur> valeurs, List<Boolean> inverse) {
		this.base = base;
		this.calcul = this.decomposerEnGroupes(valeurs, inverse);
	}

	/**
	 * Crée un calcul variadique à partir d'une liste de paires valeurs x opérateurs
	 * 
	 * @param liste La suite des valeurs et opérateurs du calcul variadique
	 */
	public E_CalculVariadique(List<List<Pair<Valeur, Operator>>> liste) {
		this.base = liste.get(0).get(0).getRight();
		this.calcul = liste;
	}

	/**
	 * Crée un calcul variadique à partir du calcul de base et en réecrivant les valeurs avec les composants donnés
	 * 
	 * @param base Le calcul de base
	 * @param composants La liste des composants à donner
	 */
	public E_CalculVariadique(E_CalculVariadique base, Composant[] composants) {
		this.base = base.base;
		
		this.calcul = new ArrayList<>();
		
		int composant = 0;
		
		for (List<Pair<Valeur, Operator>> groupe : base.calcul) {
			List<Pair<Valeur, Operator>> groupeInterne = new ArrayList<>();
			for (Pair<Valeur, Operator> element : groupe) {
				if (composants[composant] == element.getLeft()) {
					groupeInterne.add(element);
				} else {
					groupeInterne.add(new Pair<>((Valeur) composants[composant], element.getRight()));
				}
				composant++;
			}
			
			calcul.add(groupeInterne);
		}
	}

	/* ================
	 * AFFICHAGE SIMPLE
	 * ================ */

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();

		forEach(() -> sb.append(base.getNeutre()),
				(valeur) -> sb
						.append(VCalcul.getValeurParenthesee(getPriorite(), valeur.getPriorite(), valeur.getString())),
				(valeur, operateur) -> sb.append(" ").append(Utilitaire.getSymbole(operateur)).append(" ")
						.append(VCalcul.getValeurParenthesee(getPriorite(), valeur.getPriorite(), valeur.getString())),
				Utilitaire::doNothing);

		return sb.toString();
	}

	@Override
	public int getPriorite() {
		return Utilitaire.getPriorite(base);
	}

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposants) {
		visiteurDeComposants.visit(this);
	}

	@Override
	public Composant evaluationRapide() {
		List<List<Pair<Valeur, Operator>>> splitter = copyList();
		reduireContenu(splitter);
		reduireDeGaucheADroite(splitter);

		return new E_CalculVariadique(splitter);
	}

	private List<List<Pair<Valeur, Operator>>> copyList() {
		return this.calcul.stream().map(liste -> new ArrayList<>(liste)).collect(Collectors.toList());
	}

	@Override
	public Composant getComposantNormal() {
		Container<Valeur> c = new Container<Valeur>();

		forEach(() -> c.item = new VConstante(base.getNeutre()), (valeur) -> c.item = valeur,
				(valeur, operateur) -> c.item = new VCalcul(c.item, operateur, valeur), Utilitaire::doNothing);

		return c.item;
	}

	/* =================
	 * EQUALS / HASHCODE
	 * ================= */

	@Override
	public int hashCode() {
		return Objects.hash(base, calcul);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof E_CalculVariadique) {
			E_CalculVariadique that = (E_CalculVariadique) object;
			return Objects.equals(this.base, that.base) && Objects.equals(this.calcul, that.calcul);
		}
		return false;
	}

	/* ==========
	 * UTILITAIRE
	 * ========== */

	/**
	 * Donne la liste des sous composants de cet objet
	 * 
	 * @return La liste des sous composants
	 */
	public Composant[] getTousLesFils() {
		ArrayList<Composant> composants = new ArrayList<>();
		
		forEach(Utilitaire::doNothing,
				composants::add,
				(c, o) -> composants.add(c),
				Utilitaire::doNothing);
		
		return composants.toArray(new Composant[0]);
	}

	/**
	 * Exécute des actions pour tous les éléments
	 * 
	 * @param actionSiVide Action exécutée si il n'y a pas d'élément
	 * @param premiereAction Action exécutée pour le premier élément
	 * @param actionsSuivantes Action exécutée pour les paires suivantes élément x opérateur
	 * @param finSiNonVide Action exacutée à la fin si il y a eu des éléments
	 */
	private void forEach(Runnable actionSiVide, Consumer<Valeur> premiereAction,
			BiConsumer<Valeur, Operator> actionsSuivantes, Runnable finSiNonVide) {
		if (calcul.isEmpty()) {
			actionSiVide.run();
			return;
		}

		boolean estPremier = true;
		
		for (List<Pair<Valeur, Operator>> groupe : calcul) {
			for (Pair<Valeur, Operator> element : groupe) {
				
				if (estPremier) {
					estPremier = false;
					premiereAction.accept(element.getLeft());
				} else {
					actionsSuivantes.accept(element.getLeft(), element.getRight());
				}
			}
		}
		
		finSiNonVide.run();
	}

	/* =============
	 * DECOMPOSITION
	 * ============= */

	/**
	 * Décompose l'opération variadique en plusieurs groupes de couples valeurs x opérateurs. Les paires d'un même
	 * groupe sont commutatifs.
	 * @param inverse 
	 * @param valeurs 
	 * 
	 * @return Une liste de groupes de paires valeur x opérateur appliqué
	 */
	public List<List<Pair<Valeur, Operator>>> decomposerEnGroupes(List<Valeur> valeurs, List<Boolean> inverse) {
		Operator[][] groupes = recupererGroupes();

		List<List<Pair<Valeur, Operator>>> dejaConstruits = new ArrayList<>();

		List<Pair<Valeur, Operator>> enCours = new ArrayList<>();

		forEach(Utilitaire::doNothing, v -> enCours.add(new Pair<>(v, base)), (valeur, operateur) -> {
			if (!sontDuMemeGroupe(enCours.get(0).getRight(), operateur, groupes)) {
				dejaConstruits.add(enCours.stream().collect(Collectors.toList()));
				enCours.clear();
			}
			enCours.add(new Pair<>(valeur, operateur));
		}, () -> dejaConstruits.add(enCours));

		return dejaConstruits;
	}

	/**
	 * Donne les groupes d'opérateurs pour ce calcul variadique
	 * 
	 * @return La liste des groupes pertinents pour ce calcul variadique
	 */
	private Operator[][] recupererGroupes() {
		if (base == Operator.PLUS) {
			return groupesPlus;
		}

		if (base == Operator.TIMES) {
			return groupesFois;
		}

		throw new UnsupportedOperationException("Pas de groupes pour " + base);
	}

	/**
	 * Renvoie vrai si les deux opérateurs sont dans le même groupes suivant la classification par groupes donnée
	 */
	private boolean sontDuMemeGroupe(Operator operateur1, Operator operateur2, Operator[][] groupes) {
		boolean op1DansCeGroupe;
		boolean op2DansCeGroupe;

		for (int idGroupe = 0; idGroupe != groupes.length; idGroupe++) {
			op1DansCeGroupe = false;
			op2DansCeGroupe = false;

			for (Operator elementActuel : groupes[idGroupe]) {
				if (elementActuel == operateur1) {
					op1DansCeGroupe = true;
				}

				if (elementActuel == operateur2) {
					op2DansCeGroupe = true;
				}
			}

			if (op1DansCeGroupe && op2DansCeGroupe) {
				return true;
			}
		}

		return false;
	}

	/* =========
	 * REDUCTION
	 * ========= */

	// TODO : Simplifier ces méthodes. Peut être les déplacer dans une nouvelle classe ?
	// Ou alors changer la représentation interne pour prendre le modèle list list paire ?

	/**
	 * Réduit la liste de gauche à droite en tentant de fusionner les groupes de taille 1
	 */
	private static void reduireDeGaucheADroite(List<List<Pair<Valeur, Operator>>> splitter) {
		if (splitter.size() == 0 || splitter.get(0).size() != 1) {
			return;
		}

		while (splitter.size() != 1 && splitter.get(1).size() == 1) {
			Pair<Valeur, Operator> reduction = tenterReduction(splitter.get(0).get(0), splitter.get(1).get(0));

			if (reduction != null) {
				splitter.get(0).set(0, reduction);
				splitter.remove(1);
			}
		}
	}

	/**
	 * Réduit la taille des groupes internes
	 */
	private static void reduireContenu(List<List<Pair<Valeur, Operator>>> splitter) {
		splitter.forEach(liste -> reduireListe(liste));
	}

	/**
	 * Réduit la liste en tendant d'appliquer la commutatitivé et le fait que certaines valeurs peuvent être chiffrées
	 * 
	 * @param liste La liste des paires à réduire
	 */
	private static void reduireListe(List<Pair<Valeur, Operator>> liste) {
		for (int i = 0; i != liste.size(); i++) {
			if (!(liste.get(i).getLeft() instanceof VConstante))
				continue;

			int j = i + 1;
			while (j != liste.size()) {
				Pair<Valeur, Operator> nouvellePaire = tenterReduction(liste.get(i), liste.get(j));

				if (nouvellePaire != null) {
					liste.set(i, nouvellePaire);
					liste.remove(j);
				} else {
					j = j + 1;
				}
			}
		}
	}

	/**
	 * Réduit les deux paires à une seule si c'est possible
	 */
	private static Pair<Valeur, Operator> tenterReduction(Pair<Valeur, Operator> pair, Pair<Valeur, Operator> pair2) {
		if (!(pair.getLeft() instanceof VConstante && pair2.getLeft() instanceof VConstante)) {
			return null;
		}
		int v1 = ((VConstante) pair.getLeft()).valeur;
		int v2 = ((VConstante) pair2.getLeft()).valeur;

		if (pair.getRight() == pair2.getRight()) {
			return new Pair<>(new VConstante(Operator.sensConventionnel(pair.getRight()).compute(v1, v2)),
					pair.getRight());
		} else {
			if (pair.getRight() == Operator.sensConventionnel(pair.getRight())) {
				return new Pair<>(new VConstante(pair2.getRight().compute(v1, v2)), pair.getRight());
			} else {
				if (pair.getRight() == Operator.DIVIDE) {
					return null;
				}

				// Operateur moins
				return new Pair<>(new VConstante(pair.getRight().compute(v1, v2)), pair.getRight());
			}

		}
	}

}
