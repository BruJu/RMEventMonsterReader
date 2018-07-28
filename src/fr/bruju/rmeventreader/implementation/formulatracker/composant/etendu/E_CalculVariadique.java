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
import fr.bruju.rmeventreader.utilitaire.Container;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import java.util.Objects;

public class E_CalculVariadique implements ComposantEtendu, Valeur {
	public final Operator base;
	public final List<Valeur> valeurs;
	public final List<Boolean> inverses;

	public E_CalculVariadique(Operator base, List<Valeur> valeurs, List<Boolean> inverse) {
		this.base = base;
		this.valeurs = valeurs;
		this.inverses = inverse;
	}
	
	public E_CalculVariadique(List<List<Pair<Valeur, Operator>>> liste) {
		this.base = liste.get(0).get(0).getRight();

		if (this.base != Operator.sensConventionnel(this.base)) {
			throw new UnsupportedOperationException("Sens conventionnel non trouvé");
		}

		List<Pair<Valeur, Operator>> valeursApplaties = liste.stream().flatMap(a -> a.stream())
				.collect(Collectors.toList());

		this.valeurs = valeursApplaties.stream().map(paire -> paire.getLeft()).collect(Collectors.toList());
		this.inverses = valeursApplaties.stream().map(paire -> paire.getRight() == base).collect(Collectors.toList());
	}
	
	public E_CalculVariadique(E_CalculVariadique base, Composant[] composants) {
		this.base = base.base;
		this.inverses = base.inverses;
		this.valeurs = new ArrayList<>();
		
		for (Composant composant : composants) {
			valeurs.add((Valeur) composant);
		}
	}
	
	public Composant[] decomposer() {
		Composant[] composants = new Composant[valeurs.size()];
		
		for (int i = 0 ; i != valeurs.size() ; i++) {
			composants[i] = valeurs.get(i);
		}
		
		return composants;
	}

	@Override
	public String getString() {
		StringBuilder sb = new StringBuilder();

		forEach(() -> sb.append(base.getNeutre()), (valeur) -> sb.append("•[").append("("+valeur.getString()+")"),
				(valeur, operateur) -> sb.append(" ").append(Utilitaire.getSymbole(operateur)).append(" ")
						.append("("+valeur.getString()+")"),
				() -> sb.append("]"));

		return sb.toString();
	}

	@Override
	public Composant getComposantNormal() {
		Container<Valeur> c = new Container<Valeur>();

		forEach(() -> c.item = new VConstante(base.getNeutre()), (valeur) -> c.item = valeur,
				(valeur, operateur) -> c.item = new VCalcul(c.item, operateur, valeur), Utilitaire::doNothing);

		return c.item;
	}

	private void forEach(Runnable actionSiVide, Consumer<Valeur> premiereAction,
			BiConsumer<Valeur, Operator> actionsSuivantes, Runnable finSiNonVide) {
		if (valeurs.isEmpty()) {
			actionSiVide.run();
			return;
		}

		premiereAction.accept(valeurs.get(0));

		for (int i = 1; i != valeurs.size(); i++) {
			boolean operateurBase = inverses.get(i);
			actionsSuivantes.accept(valeurs.get(i), operateurBase ? base : base.revert());
		}
	}

	/* ==========================
	 * VISITEUR INTERNE FAST EVAL
	 * ========================== */

	@Override
	public Composant evaluationRapide() {
		if (true)
		return this;
		
		List<List<Pair<Valeur, Operator>>> splitter = splitter();

		reduireContenu(splitter);

		reduireDeGaucheADroite(splitter);

		if (splitter.size() == 0) {
			return null;
		}
		
		return new E_CalculVariadique(splitter);
	}

	/* ==========================
	 * AZAF
	 * ========================== */

	private void reduireDeGaucheADroite(List<List<Pair<Valeur, Operator>>> splitter) {
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


	private void reduireContenu(List<List<Pair<Valeur, Operator>>> splitter) {
		splitter.forEach(liste -> reduireListe(liste));
	}

	private void reduireListe(List<Pair<Valeur, Operator>> liste) {
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

	private Pair<Valeur, Operator> tenterReduction(Pair<Valeur, Operator> pair, Pair<Valeur, Operator> pair2) {
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
				return new Pair<>(new VConstante(pair2.getRight().compute(v1, v2)),
						pair.getRight());
			} else {
				if (pair.getRight() == Operator.DIVIDE) {
					return null;
				}

				// Operateur moins
				return new Pair<>(new VConstante(pair.getRight().compute(v1, v2)),
						pair.getRight());
			}
			
		}
	}

	public List<Pair<Valeur, Operator>> AZAFconvertir() {
		List<Pair<Valeur, Operator>> liste = new ArrayList<>();

		forEach(Utilitaire::doNothing, v -> liste.add(new Pair<>(v, base)), (v, o) -> liste.add(new Pair<>(v, o)),
				Utilitaire::doNothing);

		return liste;
	}

	public Operator[][] AZAFgetGroupes() {
		if (base == Operator.PLUS) {
			Operator[][] tableau = { { Operator.PLUS, Operator.MINUS } };
			return tableau;
		}

		if (base == Operator.TIMES) {
			Operator[][] tableau = { { Operator.TIMES }, { Operator.DIVIDE } };
			return tableau;
		}

		throw new UnsupportedOperationException("Pas de groupes pour " + base);
	}

	public List<List<Pair<Valeur, Operator>>> splitter() {
		Operator[][] groupes = AZAFgetGroupes();

		List<List<Pair<Valeur, Operator>>> dejaConstruits = new ArrayList<>();

		List<Pair<Valeur, Operator>> enCours = new ArrayList<>();

		forEach(Utilitaire::doNothing, v -> enCours.add(new Pair<>(v, base)), (valeur, operateur) -> {
			if (memeGroupe(enCours.get(0).getRight(), operateur, groupes)) {
				enCours.add(new Pair<>(valeur, operateur));
			} else {
				dejaConstruits.add(enCours.stream().collect(Collectors.toList()));
				enCours.clear();
			}
		}, () -> dejaConstruits.add(enCours));

		return dejaConstruits;
	}

	private boolean memeGroupe(Operator operateur1, Operator operateur2, Operator[][] groupes) {
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

	@Override
	public int hashCode() {
		return Objects.hash(base, valeurs, inverses);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof E_CalculVariadique) {
			E_CalculVariadique that = (E_CalculVariadique) object;
			return Objects.equals(this.base, that.base) && Objects.equals(this.valeurs, that.valeurs)
					&& Objects.equals(this.inverses, that.inverses);
		}
		return false;
	}

	
	
}
