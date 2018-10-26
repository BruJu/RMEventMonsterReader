package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import java.util.List;

import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques.AttaqueALire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs.CibleurDeMonstres;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.bornage.Borneur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner.InlinerGlobal;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.BaseDAlgorithmes;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;

import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Simplifieur implements Runnable {
	private static final CreateurDAlgorithme createur = new Initiateur();
	private static final Transformateur[] simplifications = new Transformateur[] {
			new Borneur(),
			new InlinerGlobal(),
			new CibleurDeMonstres(),

			new Borneur(),
			new InlinerGlobal(),
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

		for (AttaqueALire attaque : attaquesALire) {
			Executeur executeur = new Executeur();
			PROJET.lireEvenementCommun(executeur, attaque.numeroEvenementCommun);
			Algorithme algorithme = executeur.extraireAlgorithme();

			Classificateur personnage = new Classificateur.ClassificateurChaine(attaque.nomPersonnage);
			Classificateur attaqueNom = new Classificateur.ClassificateurChaine(attaque.nomAttaque);

			base.ajouter(new AlgorithmeEtiquete(new Classificateur[]{personnage, attaqueNom}, algorithme));
		}

		return base;
	}

	private static class Initiateur implements CreateurDAlgorithme {
		@Override
		public Algorithme creerAlgorithme() {
			List<AttaqueALire> attaquesALire = ListeDesAttaques.extraireAttaquesALire();
			//AttaqueALire.afficher(attaquesALire);
			
			AttaqueALire attaque = attaquesALire.get(27);
			System.out.println(attaque.nomAttaque + " " + attaque.nomPersonnage);
			
			Executeur executeur = new Executeur();
			PROJET.lireEvenementCommun(executeur, attaque.numeroEvenementCommun);
			return executeur.extraireAlgorithme();
		}
	}
	
	private static interface CreateurDAlgorithme {
		public Algorithme creerAlgorithme();
	}
}
