package fr.bruju.rmeventreader.implementation.monsterlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.imagereader.BuildingMotifs;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.NomDeMonstresViaShowPicture;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.EnregistreurDeDrop;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.FinDeCombat;
import fr.bruju.rmeventreader.implementation.monsterlist.actionmaker.MonsterDatabaseMaker;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.AutoActionMaker;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.AutoCorrespondeur;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.SommeurDePointsDeCapacites;
import fr.bruju.rmeventreader.implementation.monsterlist.autotraitement.LectureAutomatique;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.BDDReduite;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Remplacement;

public class MonsterDBTest {

	public static void main_(String[] args, int csv) throws IOException {
		MonsterDatabase baseDeDonnees = new MonsterDatabase(); 
		
		Runnable[] listeDesActions = new Runnable[] {
				new AutoActionMaker(new MonsterDatabaseMaker(baseDeDonnees)       , "ressources/InitCombat1.txt"),
				new AutoActionMaker(new MonsterDatabaseMaker(baseDeDonnees)       , "ressources/InitCombat2.txt"),
				new LectureAutomatique(new Correcteur(baseDeDonnees)              , "ressources/Correction.txt"),
				new AutoActionMaker(new NomDeMonstresViaShowPicture(baseDeDonnees), "ressources/NomDesMonstres.txt"),
				new AutoCorrespondeur(baseDeDonnees, Remplacement.nom()           , "ressources/Dico/Monstres.txt"),
				new AutoActionMaker(new EnregistreurDeDrop(baseDeDonnees)         , "ressources/CombatDrop.txt"),
				new AutoCorrespondeur(baseDeDonnees, Remplacement.drop()          , "ressources/Dico/Objets.txt"),
				new SommeurDePointsDeCapacites(baseDeDonnees),
				new AutoActionMaker(new FinDeCombat(baseDeDonnees)                , "ressources/FinCombat.txt"),
		};
		
		for (Runnable action : listeDesActions) {
			action.run();
		}

		if (aBesoinDOCR(baseDeDonnees)) {
			ocriserLesMonstresInconnus(baseDeDonnees);	
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
		}
		
	}
	
	private static boolean aBesoinDOCR(MonsterDatabase baseDeDonnees) {
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
		
		return true;
	}

	private static void ocriserLesMonstresInconnus(MonsterDatabase baseDeDonnees) {
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
	}
}
