package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurStatistique;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurSwitch;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurTernaire;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurVariable;
import fr.bruju.rmeventreader.implementation.formulareader.model.CreateurPersonnage;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Etat de la m�moire virtuelle
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
	 * Cr�e un �tat qui est � la racine de l'arbre
	 */
	public Etat() {
		valeursSorties = new ArrayList<>();

		CreateurPersonnage.getMap()
				.forEach((k, v) -> etatMemoire.put(k, new ValeurStatistique(v.getLeft(), v.getRight())));

		pere = null;
	}

	/**
	 * Cr�e un �tat qui est � la racine de l'arbre et le pr�rempli avec les valeurs donn�es
	 */
	public Etat(String cheminValeursPreremplies) {
		this();

		preremplirValeurs("ressources/CalculFormule.txt");
	}

	/**
	 * Rempli les valeurs de l'�tat � partir d'un fichier de la forme idVariable ValeurDeDepart
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
	 * Cr�e un �tat qui a un p�re
	 * 
	 * @param pere Le p�re
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
	 * Permet de cr�er deux fils � l'�tat courant
	 * 
	 * @return Les deux �tats fils
	 */
	public Pair<Etat, Etat> creerFils(Condition condition) {
		filsGauche = new Etat(this);
		filsDroite = new Etat(this);
		this.condition = condition;

		return new Pair<>(filsGauche, filsDroite);
	}

	/**
	 * D�truit les fils de l'�l�ment et cr�ee des valeurs ternaires pour absorber les modifications de m�moire issues
	 * des fils.
	 * 
	 * @param condition La condition qui a permis de cr�er les deux fils.
	 */
	public void unifierFils() {
		filsGauche.etatMemoire.forEach((idVar, valeurGauche) -> {
			Valeur valeurDroite = filsDroite.etatMemoire.get(idVar);

			if (valeurDroite == null) {
				valeurDroite = getValeur(idVar);
			}

			etatMemoire.put(idVar, new ValeurTernaire(condition, valeurGauche, valeurDroite));
		});

		filsDroite.etatMemoire.forEach((idVar, valeurDroite) -> {
			Valeur valeurGauche = filsGauche.etatMemoire.get(idVar);
			if (valeurGauche == null) {
				etatMemoire.put(idVar, new ValeurTernaire(condition, getValeur(idVar), valeurDroite));
			}
		});

		filsGauche = null;
		filsDroite = null;
		condition = null;
	}

	/* =============
	 * Interrupteurs 
	 * ============= */

	public Valeur getSwitch(int idSwitch) {
		Valeur retour = etatInterrupteurs.get(idSwitch);

		if (retour == null) {
			if (pere == null)
				retour = new ValeurSwitch(idSwitch);
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
	 * @param idVariable Le num�ro de la variable
	 * @return La valeur � l'int�rieur.
	 */
	public Valeur getValeur(int idVariable) {
		Valeur retour = etatMemoire.get(idVariable);

		if (retour == null) {
			if (pere == null) {
				retour = new ValeurVariable(idVariable);
				etatMemoire.put(idVariable, retour);
			} else {
				retour = pere.getValeur(idVariable);
			}
		}

		return retour;
	}

	/**
	 * File la variable � la valeur donn�e
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

	public Valeur getSortie() {
		Valeur val;

		for (int idVariable : getVariablesDeSorties()) {
			val = etatMemoire.get(idVariable);

			if (val != null) {
				return val;
			}
		}

		if (pere == null) {
			return null;
		} else {
			return pere.getSortie();
		}
	}

}
