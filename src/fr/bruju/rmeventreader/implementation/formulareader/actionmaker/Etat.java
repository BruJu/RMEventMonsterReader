package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.formulareader.formule.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurNumerique;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurStatistique;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurTernaire;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurVariable;
import fr.bruju.rmeventreader.implementation.formulareader.model.CreateurPersonnage;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class Etat {
	public static List<Integer> idVariablesCrees = new ArrayList<>();

	private Etat pere = null;

	private Etat filsGauche = null;
	private Etat filsDroite = null;

	private Map<Integer, Valeur> etatMemoire;

	public Etat() {
		etatMemoire = new HashMap<>();

		CreateurPersonnage.getMap()
				.forEach((k, v) -> etatMemoire.put(k, new ValeurStatistique(v.getLeft(), v.getRight())));

		pere = null;
	}

	private Etat(Etat pere) {
		etatMemoire = new HashMap<>();
		this.pere = pere;
	}

	public Pair<Etat, Etat> creerFils() {
		filsGauche = new Etat(this);
		filsDroite = new Etat(this);

		return new Pair<>(filsGauche, filsDroite);
	}

	public void unifierFils(Condition condition) {
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
	}

	public void enregistrerValeurDepart(int idVariable, int valeurInitiale) {
		if (pere == null) {
			etatMemoire.put(idVariable, new ValeurNumerique(valeurInitiale));
		} else {
			throw new RuntimeException("Valeur de depart dans un etat avec pere");
		}
	}

	public Valeur getSortie(int[] idVariables) {
		Valeur val;

		for (int idVariable : idVariables) {
			val = etatMemoire.get(idVariable);

			if (val != null) {
				return val; 
			}
		}

		if (pere == null) {
			return null;
		} else {
			return pere.getSortie(idVariables);
		}
	}

	public Valeur getValeur(int idVariable) {
		Valeur retour = etatMemoire.get(idVariable);

		if (retour == null) {
			if (pere == null)
				retour = new ValeurVariable(idVariable);
			else
				retour = pere.getValeur(idVariable);

			etatMemoire.put(idVariable, retour);
		}

		return retour;
	}

	public void setValue(int idVariable, Valeur nouvelleValeur) {
		etatMemoire.put(idVariable, nouvelleValeur);
	}

	public static void updateStatic(int idVariable) {
		if (!idVariablesCrees.contains(idVariable)) {
			idVariablesCrees.add(idVariable);
		}
	}

}
