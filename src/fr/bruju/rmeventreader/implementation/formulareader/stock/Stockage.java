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

/**
 * Base de données de formules et de personnages
 * 
 * @author Bruju
 *
 */
public class Stockage {
	/* ====
	 * BASE
	 * ==== */
	
	/**
	 * Liste des personnages
	 */
	private List<PersonnageReel> personnages;
	
	/**
	 * Liste des formules
	 */
	private List<Formule> formulesAcquises;

	/**
	 * Construction d'un stockage pour la liste des personnages donnés
	 * 
	 * @param personnages Les personnages qui seront concernés par les formules
	 */
	public Stockage(List<PersonnageReel> personnages) {
		this.formulesAcquises = new ArrayList<>();
		this.personnages = personnages;
	}
	
	
	/* =========
	 * AFFICHAGE
	 * ========= */

	/**
	 * Donne une liste de représentations textuelles des formules concernant le personnage donnné
	 */
	public List<String> getChaine(String nomDePersonnage, PersonnageReel personnage) {
		return formulesAcquises.stream().filter(formule -> formule.formule.getString().contains(nomDePersonnage))
				.map(formule -> formule.getString(personnage)).collect(Collectors.toList());
	}

	/* ========
	 * SERVICES
	 * ======== */
	
	/**
	 * Decrypte des formules écrites dans la liste des fichiers donnés
	 * 
	 * @param listeDeFichiers Une liste d'association nom de fichier - attaque stockée dans le fichier
	 */
	public void remplir(List<Pair<String, String>> listeDeFichiers) {
		listeDeFichiers.forEach(nomDeFichier -> remplir(nomDeFichier.getLeft(), nomDeFichier.getRight()));
	}
	
	/**
	 * Renvoie la liste des vrais personnages de la base (ie ceux qui n'ont ni Membre ni Monstre dans leur nom)
	 * 
	 * @return La liste des personnages
	 */
	public List<PersonnageReel> getVraiPersonnages() {
		return personnages.stream().filter(p -> !p.getNom().contains("Membre"))
				.filter(p -> !p.getNom().contains("Monstre")).sorted((p, q) -> p.getNom().compareTo(q.getNom()))
				.collect(Collectors.toList());
	}

	/* ======================
	 * FONCTIONNEMENT INTERNE
	 * ====================== */

	// Déchiffrage

	/**
	 * Décrypte la formule de dégâts de l'attaque donnée
	 * 
	 * @param nomDeFichier Le nom du fichier
	 * @param nomAttaque Le nom de l'attaque
	 */
	private void remplir(String nomDeFichier, String nomAttaque) {
		FormulaCalculator calc = new FormulaCalculator();
		new AutoActionMaker(calc, nomDeFichier).faire();
		List<Triplet<Integer, List<Condition>, Valeur>> valeursTrouvees = calc.getSortie();

		valeursTrouvees.forEach(v -> ajouterUneFormule(nomAttaque, v));
	}

	// Ajout de formules
	
	/**
	 * Ajoute une formule pour l'attaque donnée dans la base. Tente d'unifier des formules portant sur la même attaque
	 * 
	 * @param nomAttaque Nom de l'attaque
	 * @param v Le triplet décrypté par l'action maker
	 */
	private void ajouterUneFormule(String nomAttaque, Triplet<Integer, List<Condition>, Valeur> v) {
		if (formulesAcquises.isEmpty()) {
			formulesAcquises.add(new Formule(nomAttaque, v.a, v.b, v.c));
			return;
		}

		Formule derniereFormuleEmpilee = formulesAcquises.get(formulesAcquises.size() - 1);

		if (sontUnifiables(derniereFormuleEmpilee, v, nomAttaque)) {
			formulesAcquises.set(formulesAcquises.size() - 1, derniereFormuleEmpilee.unifier(v));
		} else {
			formulesAcquises.add(new Formule(nomAttaque, v.a, v.b, v.c));
		}
	}

	// Unification
	
	/**
	 * Retourne vrai si la formule et le triplet sont unifiables
	 */
	private boolean sontUnifiables(Formule derniereFormuleEmpilee, Triplet<Integer, List<Condition>, Valeur> v,
			String nomAttaque) {
		return derniereFormuleEmpilee.nomAttaque.equals(nomAttaque) && derniereFormuleEmpilee.formule.estSimilaire(v.c);
	}
}
