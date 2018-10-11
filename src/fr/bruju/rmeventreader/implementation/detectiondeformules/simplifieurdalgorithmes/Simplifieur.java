package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import java.util.List;

import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques.AttaqueALire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur.Executeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner.Inliner;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;

import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Simplifieur implements Runnable {
	private static final CreateurDAlgorithme createur = new Initiateur();
	private static final Simplification[] simplifications = new Simplification[] {
			new Inliner()
			
	};
	
	
	@Override
	public void run() {
		Algorithme algorithme = createur.creerAlgorithme();
		
		for (Simplification simplification : simplifications) {
			algorithme = simplification.simplifier(algorithme);
		}
		
		System.out.println(algorithme.getString());
	}
	
	private static class Initiateur implements CreateurDAlgorithme {
		@Override
		public Algorithme creerAlgorithme() {
			List<AttaqueALire> attaquesALire = ListeDesAttaques.extraireAttaquesALire();
			AttaqueALire.afficher(attaquesALire);
			
			AttaqueALire attaque = attaquesALire.get(11);
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
