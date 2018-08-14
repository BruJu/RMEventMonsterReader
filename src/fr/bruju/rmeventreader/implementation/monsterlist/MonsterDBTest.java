package fr.bruju.rmeventreader.implementation.monsterlist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.decrypter.AutoEventFactory;
import fr.bruju.rmeventreader.actionmakers.xml.AutoLibLcfXML;
import fr.bruju.rmeventreader.imagereader.BuildingMotifs;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.NomDeMonstresViaShowPicture;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.LectureDesElements;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.EnregistreurDeDrop;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.ExtracteurDeFond;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.FinDeCombat;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.MonsterDatabaseMaker;
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

public class MonsterDBTest {

	public static void main_(String[] args, int csv) throws IOException {
		// Contexte général
		Contexte contexte = new Contexte();
		contexte.remplirContexte("ressources/monsterlist/Parametres.txt");
		
		// Contexte élémentaire
		ContexteElementaire ce = new ContexteElementaire();
		ce.lireContexteElementaire(ContexteElementaire.FICHIER_RESSOURCE_CONTEXTE);
		
		// Base de données des monstres
		MonsterDatabase baseDeDonnees = new MonsterDatabase(contexte);
		
		Runnable[] listeDesActions = new Runnable[] {
			new AutoLibLcfXML(new MonsterDatabaseMaker(baseDeDonnees), "ressources\\xml\\Map0053.xml", 37, 1),
			new AutoLibLcfXML(new MonsterDatabaseMaker(baseDeDonnees), "ressources\\xml\\Map0053.xml", 102, 1),
			new AutoLibLcfXML(new ExtracteurDeFond(baseDeDonnees), "ressources\\xml\\Map0053.xml", 37, 1),
			new AutoLibLcfXML(new ExtracteurDeFond(baseDeDonnees), "ressources\\xml\\Map0053.xml", 102, 1),
			new Correspondance<>(baseDeDonnees, Correspondance.Remplacement.fond() , "ressources/monsterlist/Zones.txt"),
			new Correcteur(baseDeDonnees                                           , "ressources/Correction.txt"),
			new AutoLibLcfXML(new NomDeMonstresViaShowPicture(baseDeDonnees), "ressources\\xml\\Map0053.xml", 39, 1),
			new Correspondance<>(baseDeDonnees, Correspondance.Remplacement.nom()  , "ressources/Dico/Monstres.txt"),
			new AutoLibLcfXML(new EnregistreurDeDrop(baseDeDonnees), "ressources\\xml\\Map0453.xml", 18, 1),
			new Correspondance<>(baseDeDonnees, Correspondance.Remplacement.drop() , "ressources/Dico/Objets.txt"),
			new SommeurDePointsDeCapacites(baseDeDonnees),
			
			new AutoEventFactory(new FinDeCombat(baseDeDonnees)                     , "ressources/FinCombat.txt"),
			
			// Elements
			new ElementsInit(baseDeDonnees, ce),
			new AutoEventFactory(new LectureDesElements(baseDeDonnees, contexte, ce), ContexteElementaire.PREMIERFICHIER),
			new ElementsFinalisation(baseDeDonnees, ce)
		};
		
		for (Runnable action : listeDesActions) {
			action.run();
		}

		if (interrompreSiOCR(baseDeDonnees)) {
			return;
		}

		baseDeDonnees.trouverLesCombatsAvecDesNomsInconnus();
		
		switch (csv) {
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
			File f = new File("sorties/monstres_" + aEnregistrer[i] +"_" + sdf.format(timestamp) + ".txt");
			
			i++;
			
			try {
				f.createNewFile();
				FileWriter ff = new FileWriter(f);
				
				ff.write(aEnregistrer[i]);			

				ff.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			i++;
			
			
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
