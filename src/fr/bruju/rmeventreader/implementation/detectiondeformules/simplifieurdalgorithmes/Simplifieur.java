package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import java.util.List;

import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques.AttaqueALire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur.Executeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;

import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Simplifieur implements Runnable {
	@Override
	public void run() {
		List<AttaqueALire> attaquesALire = ListeDesAttaques.extraireAttaquesALire();
		AttaqueALire uneAttaque = attaquesALire.get(11);
		Algorithme algorithme = creerAlgorithme(uneAttaque);
		System.out.println(algorithme.getString());
	}

	private Algorithme creerAlgorithme(AttaqueALire attaque) {
		Executeur executeur = new Executeur();
		PROJET.lireEvenementCommun(executeur, attaque.numeroEvenementCommun);
		return executeur.extraireAlgorithme();
	}
	
	
	
}
