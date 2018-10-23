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
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Formule;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Groupe;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Simplifieur implements Runnable {
	private static final CreateurDAlgorithme createur = new Initiateur();
	private static final Simplification[] simplifications = new Simplification[] {
			new Borneur(),
			new InlinerGlobal()
			
	};
	
	
	@Override
	public void run() {
		Groupe<String> groupeInitial = creerAlgorithmes();
		
		for (Simplification simplification : simplifications) {
			groupeInitial.simplifier(simplification);
		}

		System.out.println(groupeInitial.getString());
	}

	private Groupe creerAlgorithmes() {
		Map<String, Map<String, Algorithme>> attaquesDesPersonnages = extraireAlgorithmesDesPersonnages();
		return transformerEnGroupes(attaquesDesPersonnages);
	}

	private static Groupe<String> transformerEnGroupes(Map<String, Map<String, Algorithme>> attaquesDesPersonnages) {
		Groupe<String> groupeRacine = new Groupe("Personnage", new HashMap<>());

		for (Map.Entry<String, Map<String, Algorithme>> personnage : attaquesDesPersonnages.entrySet()) {
			String nomPersonnage = personnage.getKey();
			Map<String, Algorithme> attaques = personnage.getValue();

			Groupe groupe = new Groupe("Attaque", attaques);

			for (Map.Entry<String, Algorithme> algorithmes : attaques.entrySet()) {
				groupe.ajouter(algorithmes.getKey(), new Formule(algorithmes.getValue()));
			}

			groupeRacine.ajouter(nomPersonnage, groupe);
		}

		return groupeRacine;
	}

	private static Map<String, Map<String, Algorithme>> extraireAlgorithmesDesPersonnages() {
		List<AttaqueALire> attaquesALire = ListeDesAttaques.extraireAttaquesALire();
		Map<String, Map<String, Algorithme>> personnages = new HashMap<>();

		for (AttaqueALire attaque : attaquesALire) {
			Executeur executeur = new Executeur();
			PROJET.lireEvenementCommun(executeur, attaque.numeroEvenementCommun);
			Algorithme algorithme = executeur.extraireAlgorithme();

			Map<String, Algorithme> attaquesDuPersonnage =
					Utilitaire.Maps.getX(personnages, attaque.nomPersonnage, HashMap::new);

			attaquesDuPersonnage.put(attaque.nomAttaque, algorithme);
		}

		return personnages;
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
