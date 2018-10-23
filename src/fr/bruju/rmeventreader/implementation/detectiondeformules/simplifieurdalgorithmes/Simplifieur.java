package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques.AttaqueALire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.bornage.Borneur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner.InlinerGlobal;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Groupe;

import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Simplifieur implements Runnable {
	private static final CreateurDAlgorithme createur = new Initiateur();
	private static final Simplification[] simplifications = new Simplification[] {
			new Borneur(),
			new InlinerGlobal()
			
	};
	
	
	@Override
	public void run() {
		Groupe groupeInitial = creerAlgorithmes();


		Algorithme algorithme = createur.creerAlgorithme();
		
		for (Simplification simplification : simplifications) {
			algorithme = simplification.simplifier(algorithme);
		}

		System.out.println(algorithme.getString());
	}

	private Groupe creerAlgorithmes() {
		List<AttaqueALire> attaquesALire = ListeDesAttaques.extraireAttaquesALire();
		Map<String, Groupe> personnages = new HashMap<>();

		for (AttaqueALire attaque : attaquesALire) {



		}

		return new Groupe(null, new ArrayList<>(personnages.values()));
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
