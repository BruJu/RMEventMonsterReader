package fr.bruju.rmeventreader.implementation.formulareader.stock;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulareader.actionmaker.FormulaCalculator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.model.PersonnageReel;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.AutoActionMaker;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Triplet;

public class Stockage {
	private List<PersonnageReel> personnages;
	private List<Formule> formulesAcquises;

	public Stockage(List<PersonnageReel> personnages) {
		this.formulesAcquises = new ArrayList<>();
		this.personnages = personnages;
	}

	public void remplir(List<Pair<String, String>> listeDeFichiers) {
		listeDeFichiers.forEach(nomDeFichier -> remplir(nomDeFichier.getLeft(), nomDeFichier.getRight()));
	}

	private void remplir(String nomDeFichier, String nomAttaque) {
		FormulaCalculator calc = new FormulaCalculator();
		new AutoActionMaker(calc, nomDeFichier).faire();
		List<Triplet<Integer, List<Condition>, Valeur>> valeursTrouvees = calc.getSortie();

		valeursTrouvees.forEach(v -> ajouterUneFormule(nomAttaque, v));
	}

	private void ajouterUneFormule(String nomAttaque, Triplet<Integer, List<Condition>, Valeur> v) {
		if (formulesAcquises.isEmpty()) {
			formulesAcquises.add(new Formule(nomAttaque, v.a, v.b, v.c));
			return;
		}

		Formule derniereFormuleEmpilee = formulesAcquises.get(formulesAcquises.size() - 1);

		if (sontUnifiables(derniereFormuleEmpilee, v, nomAttaque)) {
			unifier(derniereFormuleEmpilee, v);
		} else {
			formulesAcquises.add(new Formule(nomAttaque, v.a, v.b, v.c));
		}
	}

	private void unifier(Formule derniereFormuleEmpilee, Triplet<Integer, List<Condition>, Valeur> v) {
		formulesAcquises.remove(derniereFormuleEmpilee);
		
		String nomAttaque = derniereFormuleEmpilee.nomAttaque;
		Valeur valeurUnifiee = derniereFormuleEmpilee.formule.similariser(v.c);
		List<Integer> variablesTouchees = derniereFormuleEmpilee.variableTouchee;
		variablesTouchees.add(v.a);
		List<Condition> conditions = derniereFormuleEmpilee.conditionsRequises;
		
		formulesAcquises.add(new Formule(nomAttaque, variablesTouchees, conditions, valeurUnifiee));
	}

	private boolean sontUnifiables(Formule derniereFormuleEmpilee, Triplet<Integer, List<Condition>, Valeur> v,
			String nomAttaque) {
		return derniereFormuleEmpilee.nomAttaque.equals(nomAttaque) && derniereFormuleEmpilee.formule.estSimilaire(v.c);
	}

	public List<PersonnageReel> getVraiPersonnages() {
		return personnages.stream().filter(p -> !p.getNom().contains("Membre"))
				.filter(p -> !p.getNom().contains("Monstre")).sorted((p, q) -> p.getNom().compareTo(q.getNom()))
				.collect(Collectors.toList());
	}

	public List<String> getChaine(String nomDePersonnage, PersonnageReel personnage) {
		return formulesAcquises.stream().filter(formule -> formule.formule.getString().contains(nomDePersonnage))
				.map(formule -> formule.getString(personnage)).collect(Collectors.toList());
	}

}
