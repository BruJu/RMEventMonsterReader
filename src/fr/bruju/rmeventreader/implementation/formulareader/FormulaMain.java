package fr.bruju.rmeventreader.implementation.formulareader;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulareader.actionmaker.FormulaCalculator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.model.CreateurPersonnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.PersonnageReel;
import fr.bruju.rmeventreader.implementation.formulareader.stock.Stockage;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.AutoActionMaker;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Triplet;

public class FormulaMain {

	private static final String MEMBRE = "Membre";
	private static final String MONSTRE = "Monstre";
	private static final String RESSOURCES_ATTAQUES = "ressources/Attaques/";

	public static void main_(String[] args) {
		// Creation du stockage
		Stockage stockage = new Stockage(CreateurPersonnage.creerTousLesPersonnages());

		// Attaques à explorer
		File file = new File(RESSOURCES_ATTAQUES);

		List<Pair<String, String>> liste = new ArrayList<>();
		
		for (String fichier : file.list()) {
			liste.add(new Pair<>(RESSOURCES_ATTAQUES + fichier, fichier.substring(0, fichier.length() - 4)));
		}

		// Remplissage
		stockage.remplir(liste);

		// Recuperation des personnages
		List<PersonnageReel> personnages = stockage.getVraiPersonnages();

		// Filter des personnages voulus
		List<PersonnageReel> persoAffiches = personnages.stream().filter(p -> p.getNom().equals("Ainorie"))
				.collect(Collectors.toList());

		// Affichage des formules les concernant
		persoAffiches.stream()
					.flatMap(p -> stockage.getChaine(p.getNom()).stream())
					.forEach(f -> System.out.println(f));
	}

}
