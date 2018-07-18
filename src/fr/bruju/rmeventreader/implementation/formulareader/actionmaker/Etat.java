package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NewValeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.model.CreateurPersonnage;
import fr.bruju.rmeventreader.utilitaire.Ensemble;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Etat de la mémoire virtuelle
 * 
 * @author Bruju
 *
 */
public class Etat {
	// Arbre
	private Etat pere = null;
	private Etat filsGauche = null;
	private Etat filsDroite = null;
	private Condition condition = null;

	// Memoire
	private Map<Integer, Valeur> etatMemoire = new HashMap<>();
	private Map<Integer, Valeur> etatInterrupteurs = new HashMap<>();
	private List<Integer> valeursSorties;

	/* =============
	 * Constructeurs 
	 * ============= */

	/**
	 * Crée un état qui est à la racine de l'arbre
	 */
	public Etat() {
		valeursSorties = new ArrayList<>();

		CreateurPersonnage.getMap()
				.forEach((k, v) -> etatMemoire.put(k, NewValeur.Statistique(v.getLeft(), v.getRight())));

		pere = null;
	}

	/**
	 * Crée un état qui est à la racine de l'arbre et le prérempli avec les valeurs données
	 */
	public Etat(String cheminValeursPreremplies) {
		this();

		preremplirValeurs("ressources/CalculFormule.txt");
	}

	/**
	 * Rempli les valeurs de l'état à partir d'un fichier de la forme idVariable ValeurDeDepart
	 * 
	 * @param chemin Le fichier
	 */
	private void preremplirValeurs(String chemin) {
		try {
			FileReaderByLine.lireLeFichier(new File(chemin),
					new EtatPreremplirValeurs(etatMemoire, etatInterrupteurs, valeursSorties));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Crée un état qui a un père
	 * 
	 * @param pere Le père
	 */
	private Etat(Etat pere) {
		this.pere = pere;
	}

	/* ==================
	 * Structure en arbre 
	 * ================== */

	// Getters

	/**
	 * Donne le fils gauche
	 * 
	 * @return Le fils gauche
	 */
	public Etat getFilsGauche() {
		return filsGauche;
	}

	/**
	 * Retourne le fils droit
	 * 
	 * @return Le fils droit
	 */
	public Etat getFilsDroit() {
		return filsDroite;
	}

	/**
	 * Retourn le pere
	 * 
	 * @return Le pere
	 */
	public Etat getPere() {
		return pere;
	}

	// Methodes

	/**
	 * Permet de créer deux fils à l'état courant
	 * 
	 * @return Les deux états fils
	 */
	public Pair<Etat, Etat> creerFils(Condition condition) {
		filsGauche = new Etat(this);
		filsDroite = new Etat(this);
		this.condition = condition;

		return new Pair<>(filsGauche, filsDroite);
	}

	/**
	 * Détruit les fils de l'élément et créee des valeurs ternaires pour absorber les modifications de mémoire issues
	 * des fils.
	 * 
	 * @param condition La condition qui a permis de créer les deux fils.
	 */
	public void unifierFils() {
		Map<Integer, Valeur[]> resultat = new HashMap<>();

		remplirMapUnification(resultat, filsGauche, 1, 3);
		remplirMapUnification(resultat, filsDroite, 2, 3);
		finirMapUnification(resultat, this);

		resultat.forEach((idVariable, valeursBdd) -> {
			if (valeursBdd[1] == null) {
				if (valeursBdd[2] == null) {
					throw new RuntimeException("Erreur de conception");
				} else {
					etatMemoire.put(idVariable, NewValeur.Unification(condition, valeursBdd[0], null, valeursBdd[2]));
				}
			} else {
				if (valeursBdd[2] == null) {
					etatMemoire.put(idVariable, NewValeur.Unification(condition, valeursBdd[0], valeursBdd[1], null));
				} else {
					etatMemoire.put(idVariable, NewValeur.Ternaire(condition, valeursBdd[1], valeursBdd[2]));
				}
			}

		}

		);

		filsGauche = null;
		filsDroite = null;
		condition = null;
	}

	private static void finirMapUnification(Map<Integer, Valeur[]> destination, Etat source) {
		destination.forEach((idVar, values) -> values[0] = source.getValeur(idVar));
	}

	private static void remplirMapUnification(Map<Integer, Valeur[]> destination, Etat etat, int slot, int taille) {

		etat.etatMemoire.forEach((idVar, valeur) -> {
			if (!destination.containsKey(idVar)) {
				destination.put(idVar, new Valeur[taille]);
			}

			destination.get(idVar)[slot] = valeur;
		});
	}

	/* =============
	 * Interrupteurs 
	 * ============= */

	public Valeur getSwitch(int idSwitch) {
		Valeur retour = etatInterrupteurs.get(idSwitch);

		if (retour == null) {
			if (pere == null)
				retour = NewValeur.Switch(idSwitch);
			else
				retour = pere.getSwitch(idSwitch);

			etatInterrupteurs.put(idSwitch, retour);
		}

		return retour;
	}

	public void setSwitch(int idSwitch, Valeur pushed) {
		etatInterrupteurs.put(idSwitch, pushed);
	}

	/* =========
	 * Variables 
	 * ========= */

	public Valeur getValeurUnforced(int idVariable) {
		Valeur retour = etatMemoire.get(idVariable);

		if (retour != null)
			return retour;

		if (pere == null) {
			return null;
		} else {
			return pere.getValeur(idVariable);
		}
	}

	/**
	 * Renvoie la valeur dans la variable
	 * 
	 * @param idVariable Le numéro de la variable
	 * @return La valeur à l'intérieur.
	 */
	public Valeur getValeur(int idVariable) {
		Valeur retour = etatMemoire.get(idVariable);

		if (retour == null) {
			if (pere == null) {
				retour = NewValeur.Variable(idVariable);
				etatMemoire.put(idVariable, retour);
			} else {
				retour = pere.getValeur(idVariable);
			}
		}

		return retour;
	}

	/**
	 * File la variable à la valeur donnée
	 * 
	 * @param idVariable La variable
	 * @param nouvelleValeur La nouvelle valeur
	 */
	public void setValue(int idVariable, Valeur nouvelleValeur) {
		etatMemoire.put(idVariable, nouvelleValeur);
	}

	/* =======
	 * Sorties 
	 * ======= */

	public List<Integer> getVariablesDeSorties() {
		if (this.valeursSorties == null) {
			return pere.getVariablesDeSorties();
		}

		return valeursSorties;
	}

	public boolean estUneSortie(int idVariable) {
		if (valeursSorties == null) {
			valeursSorties = getVariablesDeSorties();
		}

		return Ensemble.appartient(idVariable, valeursSorties);
	}
}
