package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques.AttaqueALire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs.CibleurDeMonstres;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs.DetermineurDeCiblage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.bornage.Borneur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner.InlinerGlobal;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.UnificateurIdentique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.BaseDAlgorithmes;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.Individu;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.Personnage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Tri;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Simplifieur implements Runnable {
	private static final Transformateur[] simplifications = new Transformateur[] {
			new Borneur(),
			new InlinerGlobal(),
			new DetermineurDeCiblage(),
			new Tri()
	};
	
	
	@Override
	public void run() {
		BaseDAlgorithmes algorithmes = creerAlgorithmes();
		
		for (Transformateur simplification : simplifications) {
			algorithmes.transformer(simplification);
		}

		System.out.println(algorithmes.getString());
	}

	private BaseDAlgorithmes creerAlgorithmes() {
		return extraireAlgorithmesDesPersonnages();
	}

	private static BaseDAlgorithmes extraireAlgorithmesDesPersonnages() {
		List<AttaqueALire> attaquesALire = ListeDesAttaques.extraireAttaquesALire();

		BaseDAlgorithmes base = new BaseDAlgorithmes();

		Map<Integer, ExprVariable> variablesInstanciees = lireVariablesInitiales();

		for (AttaqueALire attaque : attaquesALire) {
			Executeur executeur = new Executeur(variablesInstanciees);
			PROJET.lireEvenementCommun(executeur, attaque.numeroEvenementCommun);
			Algorithme algorithme = executeur.extraireAlgorithme();

			Classificateur personnage = new Classificateur.ClassificateurChaine(attaque.nomPersonnage);
			Classificateur attaqueNom = new Classificateur.ClassificateurChaine(attaque.nomAttaque);

			base.ajouter(new AlgorithmeEtiquete(new Classificateur[]{personnage, attaqueNom}, algorithme));
		}

		return base;
	}

	private static String FICHIER_PERSONNAGES = "ressources/formulatracker/Statistiques.txt";

	private static Map<Integer,ExprVariable> lireVariablesInitiales() {
		Map<Integer, ExprVariable> variablesInitiales = new HashMap<>();

		Map<String, Personnage> personnages = new HashMap<>();


		LecteurDeFichiersLigneParLigne.lectureFichierRessources(FICHIER_PERSONNAGES, ligne -> {
			String[] donnees = ligne.split(" ", 3);

			String nomPersonnage = donnees[0];
			String nomStatistique = donnees[1];
			boolean estPropriete = donnees[2].charAt(0) == 'S';
			Integer numeroVariable = Integer.decode((estPropriete ? donnees[2].substring(1) : donnees[2]));

			if (estPropriete) {
				numeroVariable = -numeroVariable;
			}

			Personnage personnage = Utilitaire.Maps.getY(personnages, nomPersonnage, Individu::new);
			Statistique statistique = new Statistique(numeroVariable, personnage, nomStatistique);
			variablesInitiales.put(numeroVariable, statistique);
		});

		return variablesInitiales;
	}

}
