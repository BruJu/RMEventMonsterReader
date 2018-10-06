package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.ListeDesAttaques;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.actionmaker.ComposeurInitial;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.arbre.Arbre;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.arbre.MonteurDArbre;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.maillon.FormuleToString;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.operations.desinjection.PreTraitementDesinjection;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.operations.unification.Unificateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.visiteur.deduction.Deducteur;
import fr.bruju.rmeventreader.utilitaire.Triplet;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class Recomposition implements Runnable {
	private final static String CHEMIN_PARAMETRES = "ressources\\Recomposition.txt";

	Parametres parametres;
	BaseDeVariables base;

	public void run() {
		parametres = new Parametres(CHEMIN_PARAMETRES);
		base = new BaseDeVariables();

		base.remplir(parametres);

		visionArbre();

		/*		Opérations dans FormulaTracker :
				new Borne(),
				new Encadrer(),
				new MaillonDiviseur(),
		*/

	}

	private void visionArbre() {
		Arbre arbre = construireArbre();

		// Traitements de l'arbre
		
		arbre.transformerAlgorithmes(new Injecteur(parametres))
			.pimp(new PreTraitementDesinjection(parametres))
			.unifier((r1, r2) -> Unificateur.fusionner(r1, r2, base));
		

		// Recupération des fruits

		List<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> fruits = arbre.getFruits();

		String sortie = fruits.stream().map(this::construireSortie).collect(Collectors.joining("\n"));

		System.out.println(sortie);
		enregistrerDansFichier(sortie);
	}

	private String construireSortie(Triplet<List<GroupeDeConditions>, Statistique, Algorithme> triplet) {
		StringBuilder sb = new StringBuilder();

		sb.append(triplet.a.get(0).conditions.get(0).getString()).append(";")
				.append(triplet.a.get(1).conditions.get(0).getString()).append(";").append(triplet.b.toString())
				.append(";");

		triplet.a.subList(2, triplet.a.size()).stream().map(groupe -> groupe.toString())
				.forEach(s -> sb.append(s).append(";"));

		sb.append(new FormuleToString(base).traiter(triplet.c).s);
		return sb.toString();
	}

	private Arbre construireArbre() {
		MonteurDArbre exp = new MonteurDArbre(base);
		
		ListeDesAttaques.extraireAttaquesALire().forEach(attaque -> {
			ComposeurInitial composeur = new ComposeurInitial(base.getVariablesStatistiques());
			PROJET.lireEvenementCommun(composeur, attaque.numeroEvenementCommun);
			
			// Extraction d'un premier résultat
			
			Map<Integer, Algorithme> extrait = composeur.getResultat();
			
			// Simplifications ne se reposant pas sur des connaissances métier
			extrait.replaceAll((key, algo) -> (Algorithme) new Deducteur().traiter(algo));
			
			
			exp.add(attaque.nomPersonnage, attaque.nomAttaque, extrait);
		});

		return exp.creerArbre();
	}


	private static void enregistrerDansFichier(String sortie) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		Utilitaire.Fichier_Ecrire("sorties/recompo_" + sdf.format(timestamp) + ".txt", sortie);
	}
	
}
