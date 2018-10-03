package fr.bruju.rmeventreader.implementation.monsterlist;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.Explorateur;
import fr.bruju.rmeventreader.imagereader.BuildingMotifs;
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
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

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
		contexte.remplirContexte(PARAMETRES);
		
		// Contexte élémentaire
		ContexteElementaire ce = new ContexteElementaire();
		ce.lireContexteElementaire(ELEMENTS);
		
		// Base de données des monstres
		MonsterDatabase baseDeDonnees = new MonsterDatabase(contexte);
		
		Runnable[] listeDesActions = new Runnable[] {
			() -> Explorateur.lireEvenement(new MonsterDatabaseMaker(baseDeDonnees), 53, 37, 1),
			() -> Explorateur.lireEvenement(new MonsterDatabaseMaker(baseDeDonnees), 53, 102, 1),
			() -> Explorateur.lireEvenement(new ExtracteurDeFond(baseDeDonnees), 53, 37, 1),
			() -> Explorateur.lireEvenement(new ExtracteurDeFond(baseDeDonnees), 53, 102, 1),
			new Correcteur(baseDeDonnees, CORRECTION),
			() -> Explorateur.lireEvenement(new NomDeMonstresViaShowPicture(baseDeDonnees), 53, 39, 1),
			() -> Explorateur.lireEvenement(new EnregistreurDeDrop(baseDeDonnees), 453, 18, 1),
			new Correspondance(baseDeDonnees, Correspondance.fond(ZONES)),
			new Correspondance(baseDeDonnees, Correspondance.nom(MONSTRES)),
			new Correspondance(baseDeDonnees, Correspondance.drop()),
			new SommeurDePointsDeCapacites(baseDeDonnees),
			() -> Explorateur.lireEvenementCommun(new FinDeCombat(baseDeDonnees), 44),
			
			// Elements
			
			new ElementsInit(baseDeDonnees, ce),
			() -> Explorateur.lireEvenementCommun(new LectureDesElements(baseDeDonnees, contexte, ce), 277),
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
	
	private static void sauvegarder(MonsterDatabase bdd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

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
			Utilitaire.Fichier_Ecrire("sorties/monstres_" + aEnregistrer[i] +"_" + sdf.format(timestamp) + ".txt",
					aEnregistrer[i+1]);
			
			i = i + 2;
		}
	}

	private static boolean interrompreSiOCR(MonsterDatabase baseDeDonnees) {
		{
			Object[] monstresInconnus = baseDeDonnees.extractMonsters().stream()
					.filter(m -> m != null)
					.filter(m -> m.nom.substring(0, 2).equals("id"))
					.toArray();
			
			if (monstresInconnus.length == 0) {
				return false;
			}
			
			System.out.println("== Des monstres n'ont pas été reconnus ==");
			
			for (Object m : monstresInconnus) {
				System.out.println(((Monstre) m).getString());
			}
			
			System.out.println();
		}
		
		{
			List<String> monstresInconnus = new ArrayList<>();
			
			for (Monstre monstre : baseDeDonnees.extractMonsters()) {
				if (monstre == null)
					continue;
				
				if (!monstre.nom.substring(0, 2).equals("id"))
					continue;
				
				monstresInconnus.add(monstre.nom);
			}
			
			
			BuildingMotifs chercheurDeMotifs = new BuildingMotifs(monstresInconnus);
			chercheurDeMotifs.lancer();
			
			System.out.println("== Identification == ");
			chercheurDeMotifs.getMap().forEach( (cle, valeur) -> System.out.println(cle + " " + valeur));
			
			return true;
		}
	}
}
