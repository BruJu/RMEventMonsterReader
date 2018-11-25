package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Statistique;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;
import fr.bruju.util.MapsUtils;

import java.util.*;

public class BaseDePersonnages {

	private static String FICHIER_PERSONNAGES = "ressources/formulatracker/Statistiques.txt";

	private Map<Integer, ExprVariable> variablesInstanciees;
	private Map<String, Personnage> personnages;
	private int numeroVariable = 5001;
	private int numeroInterrupteur = -5001;

	int getNouvelleVariable() {
		return numeroVariable++;
	}

	int getNouvelInterrupteur() {
		return numeroInterrupteur--;
	}


	public BaseDePersonnages() {
		variablesInstanciees = new HashMap<>();
		personnages = new HashMap<>();

		LecteurDeFichiersLigneParLigne.lectureFichierRessources(FICHIER_PERSONNAGES, ligne -> {
			String[] donnees = ligne.split(" ", 3);

			String nomPersonnage = donnees[0];
			String nomStatistique = donnees[1];
			boolean estPropriete = donnees[2].charAt(0) == 'S';
			Integer numeroVariable = Integer.decode((estPropriete ? donnees[2].substring(1) : donnees[2]));

			if (estPropriete) {
				numeroVariable = -numeroVariable;
			}

			Personnage personnage = MapsUtils.getY(personnages, nomPersonnage, Individu::new);
			Statistique statistique = new Statistique(numeroVariable, personnage, nomStatistique);
			variablesInstanciees.put(numeroVariable, statistique);
			personnage.ajouterStatistique(statistique);
		});
	}

	public Map<Integer, ExprVariable> getVariablesInstanciees() {
		return variablesInstanciees;
	}

	public Personnage getPersonnage(String nom) {
		if (!personnages.containsKey(nom)) {
			String messageDeBase = "Aucun personnage nommé " + nom + "\nPersonnages existants : ";

			StringJoiner sj = new StringJoiner(" ", messageDeBase, "");

			for (String s : personnages.keySet()) {
				sj.add(s);
			}

			System.err.println(sj.toString());

			throw new RuntimeException();
		}

		return personnages.get(nom);
	}

	public Personnage getPersonnageUnifie(Personnage p1, Personnage p2) {
		Set<Individu> individus = new TreeSet<>();
		p1.ajouterPersonnage(individus);
		p2.ajouterPersonnage(individus);

		String nomPersonnageUnifie = Groupe.definirNom(individus);

		System.out.println(nomPersonnageUnifie);

		Personnage personnage = personnages.get(nomPersonnageUnifie);

		if (personnage == null) {
			Groupe personnageGroupe = new Groupe(nomPersonnageUnifie, individus);
			personnage = personnageGroupe;
			personnages.put(nomPersonnageUnifie, personnageGroupe);

			personnageGroupe.ajouterNouvellesStatistiquesIssuesDe(p1, this);
			personnageGroupe.ajouterNouvellesStatistiquesIssuesDe(p2, this);
		}

		return personnage;
	}
}
