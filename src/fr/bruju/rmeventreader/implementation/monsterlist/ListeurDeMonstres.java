package fr.bruju.rmeventreader.implementation.monsterlist;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import fr.bruju.rmeventreader.Parametre;
import fr.bruju.rmeventreader.imagereader.ReconnaisseurDImages;
import fr.bruju.rmeventreader.imagereader.Motif;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.EnregistreurDeDrop;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.ExtracteurDeFond;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.FinDeCombat;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.LectureDesElements;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.MonsterDatabaseMaker;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.NomDeMonstresViaShowPicture;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.Correcteur;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.Correspondance;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.ElementsFinalisation;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.ElementsInit;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.SommeurDePointsDeCapacites;
import fr.bruju.rmeventreader.implementation.monsterlist.contexte.Contexte;
import fr.bruju.rmeventreader.implementation.monsterlist.contexte.ContexteElementaire;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.BDDReduite;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.ChercheObjet;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class ListeurDeMonstres implements Runnable {
	/** Variables où se situent les statistiques des monstres + autres positions de variables */
	public static final String PARAMETRES = "ressources/ListeurDeMonstres_Parametres.txt";
	/** Nom des éléments et des parties du corps */
	public static final String ELEMENTS   = "ressources/ListeurDeMonstres_Elements.txt";
	/** Correspondance id du fond - nom du lieu */
	public static final String ZONES      = "ressources/ListeurDeMonstres_Zone.txt";
	/** Correction de combats buggés pour ne pas les afficher */
	public static final String CORRECTION = "ressources/ListeurDeMonstres_Correction.txt";
	/** Correspondance id de l'image affichant le nom du monstre - texte */
	public static final String MONSTRES   = "ressources/ListeurDeMonstres_Nom.txt";
	
	private int option;
	
	public ListeurDeMonstres(int option) {
		this.option = option;
	}

	public void run() {
		// Contexte général
		Contexte contexte = new Contexte();
		
		// Contexte élémentaire
		ContexteElementaire ce = new ContexteElementaire();
		ce.lireContexteElementaire(ELEMENTS);
		
		// Base de données des monstres
		MonsterDatabase baseDeDonnees = new MonsterDatabase(contexte);
		
		Runnable[] listeDesActions = new Runnable[] {
			() -> PROJET.lireEvenement(new MonsterDatabaseMaker(baseDeDonnees), 53, 37, 1),
			() -> PROJET.lireEvenement(new MonsterDatabaseMaker(baseDeDonnees), 53, 102, 1),
			() -> PROJET.lireEvenement(new ExtracteurDeFond(baseDeDonnees), 53, 37, 1),
			() -> PROJET.lireEvenement(new ExtracteurDeFond(baseDeDonnees), 53, 102, 1),
			new Correcteur(baseDeDonnees, CORRECTION),
			() -> PROJET.lireEvenement(new NomDeMonstresViaShowPicture(baseDeDonnees), 53, 39, 1),
			() -> PROJET.lireEvenement(new EnregistreurDeDrop(baseDeDonnees), 453, 18, 1),
			new Correspondance(baseDeDonnees, Correspondance.fond(ZONES)),
			new Correspondance(baseDeDonnees, Correspondance.nom(MONSTRES)),
			new Correspondance(baseDeDonnees, Correspondance.drop()),
			new SommeurDePointsDeCapacites(baseDeDonnees),
			() -> PROJET.lireEvenementCommun(new FinDeCombat(baseDeDonnees), 44),
			
			// Elements
			
			new ElementsInit(baseDeDonnees, ce),
			() -> PROJET.lireEvenementCommun(new LectureDesElements(baseDeDonnees, ce), 277),
			new ElementsFinalisation(baseDeDonnees, ce)
		};
		
		for (Runnable action : listeDesActions) {
			action.run();
		}

		if (interrompreSiOCR(baseDeDonnees)) {
			return;
		}

		baseDeDonnees.trouverLesCombatsAvecDesNomsInconnus();
		
		switch (option) {
		case 0:
			System.out.println(baseDeDonnees.getString());
			break;
		case 1:
			System.out.println(baseDeDonnees.getCSVRepresentationOfBattles());
			break;
		case 2:
			System.out.println(baseDeDonnees.getCSVRepresentationOfMonsters());
			break;
		case 3:
			BDDReduite bddR = new BDDReduite(baseDeDonnees.extractMonsters());
			System.out.println(bddR.getCSV());
			break;
		case 4: // Affiche les combats n'ayant pas de fonds
			MonsterDatabase nouvelleBdd = baseDeDonnees.filtrer(combat -> combat.fonds.isEmpty());
			System.out.println(nouvelleBdd.getCSVRepresentationOfBattles());
			break;
		case 5:	// Affiche les objets - zone - monstres
			System.out.println(new ChercheObjet(baseDeDonnees).toString());
			break;
		case 6:
			sauvegarder(baseDeDonnees);
			break;
			
		}
		
	}
	
	
	public static String getTimeStamp() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return sdf.format(timestamp);
	}
	
	private static void sauvegarder(MonsterDatabase bdd) {
		String timeStamp = getTimeStamp();

		String[] aEnregistrer = new String[] {
				"Liste",
				bdd.getString(),
				"Combats",
				bdd.getCSVRepresentationOfBattles(),
				"Monstres",
				bdd.getCSVRepresentationOfMonsters(),
				"Reduite",
				new BDDReduite(bdd.extractMonsters()).getCSV(),
				"Drop",
				new ChercheObjet(bdd).toString()
		};
		
		
		int i = 0;
		
		while (i != aEnregistrer.length) {
			Utilitaire.Fichier_Ecrire("sorties/monstres_" + aEnregistrer[i] +"_" + timeStamp + ".txt",
					aEnregistrer[i+1]);
			
			i = i + 2;
		}
	}

	private static boolean interrompreSiOCR(MonsterDatabase baseDeDonnees) {
		List<Monstre> monstresInconnus = baseDeDonnees.extractMonsters().stream()
				.filter(m -> m != null && m.nom.startsWith("id"))
				.collect(Collectors.toList());
		
		if (monstresInconnus.isEmpty()) {
			return false;
		}
		
		System.out.println("== Des monstres n'ont pas été reconnus ==");
		monstresInconnus.forEach(monstre -> System.out.println(monstre.nom));
		System.out.println();
		
		
		ReconnaisseurDImages chercheurDeMotifs = new ReconnaisseurDImages(Parametre.get("DOSSIER") + "Picture\\");
		monstresInconnus.stream().map(monstre -> monstre.nom).forEach(chercheurDeMotifs::identifier);
		
		
		String nomEnErreurs = chercheurDeMotifs.listeLesErreurs();
		if (!nomEnErreurs.equals("")) {
			System.out.println("== Noms non identifiés ==");
			System.out.println(nomEnErreurs);
		}
		
		String motifsNonReconnus = chercheurDeMotifs
				.getMotifsInconnus()
				.stream()
				.map(Motif::getChaineDeNonReconnaissance)
				.collect(Collectors.joining("\n"));
		if (!motifsNonReconnus.equals("")) {
			System.out.println("== Motifs non reconnus ==");
			System.out.println(motifsNonReconnus);
		}
		
		String nomsIdentifies = chercheurDeMotifs.getNomsIdentifies();
		if (!nomsIdentifies.equals("")) {
			System.out.println("== Noms identifiés ==");
			System.out.println(nomsIdentifies);
			
			//inscrireDesNomsDeMonstres("\n// Monstres identifiés le " + getTimeStamp() + "\n" + nomsIdentifies);
		}
		
		return true;
	}

	private static void inscrireDesNomsDeMonstres(String nomsIdentifies) {
		try {
			FileWriter fileWriter = new FileWriter(MONSTRES, true);
			fileWriter.write(nomsIdentifies);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Les nouveaux noms n'ont pas eu être inscrits dans le fichier");
			e.printStackTrace();
		}
	}
}
