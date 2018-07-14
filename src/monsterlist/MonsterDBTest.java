package monsterlist;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import imagereader.BuildingMotifs;
import monsterlist.actionmaker.CompleterWithShowPicture;
import monsterlist.actionmaker.DropCompleter;
import monsterlist.actionmaker.MonsterDatabaseMaker;
import monsterlist.autotraitement.ActionAutomatique;
import monsterlist.autotraitement.AutoActionMaker;
import monsterlist.autotraitement.AutoCorrespondeur;
import monsterlist.metier.MonsterDatabase;
import monsterlist.metier.Monstre;
import monsterlist.metier.Monstre.RemplacementNom;
import monsterlist.metier.Monstre.RemplacementDrop;

public class MonsterDBTest {

	public static void main(String[] args) throws IOException {
		
		
		MonsterDatabase baseDeDonnees = new MonsterDatabase(); 
		
		ActionAutomatique[] listeDesActions = new ActionAutomatique[] {
				new AutoActionMaker(new MonsterDatabaseMaker(baseDeDonnees)    , "ressources/InitCombat1.txt"),
				new AutoActionMaker(new MonsterDatabaseMaker(baseDeDonnees)    , "ressources/InitCombat2.txt"),
				() -> { new AeAdd(baseDeDonnees).activate(); } ,
				new AutoActionMaker(new CompleterWithShowPicture(baseDeDonnees), "ressources/NomDesMonstres.txt"),
				new AutoCorrespondeur(baseDeDonnees, new RemplacementNom()     , "ressources/Dico/Correspondance_Monstres.txt"),
				new AutoActionMaker(new DropCompleter(baseDeDonnees)           , "ressources/CombatDrop.txt"),
				new AutoCorrespondeur(baseDeDonnees, new RemplacementDrop()    , "ressources/Dico/Objets.txt"),
		};
		
		
		for (ActionAutomatique action : listeDesActions) {
			action.faire();
		}
		
		
		ocriserLesMonstresInconnus(baseDeDonnees);
		
		baseDeDonnees.extractMonsters().stream()
		.filter(m -> m != null)
		.filter(m -> m.name.substring(0, 2).equals("id"))
		.forEach(m -> System.out.println(m.getString()));
		
		
		
		baseDeDonnees.trouverLesCombatsAvecDesNomsInconnus();

		baseDeDonnees.trouverLesMonstresAvecDesNomsInconnus();
		
		System.out.println(baseDeDonnees.getString());
		
	}

	private static void ocriserLesMonstresInconnus(MonsterDatabase baseDeDonnees) {
		List<Monstre> monstresInconnus = new ArrayList<>();
		
		for (Monstre monstre : baseDeDonnees.extractMonsters()) {
			if (monstre == null)
				continue;
			
			if (!monstre.name.substring(0, 2).equals("id"))
				continue;
			
			monstresInconnus.add(monstre);
		}
		
		
		BuildingMotifs chercheurDeMotifs = new BuildingMotifs(monstresInconnus);
		chercheurDeMotifs.lancer();
		chercheurDeMotifs.getMap().forEach( (cle, valeur) -> System.out.println(cle + " " + valeur));
	}
}
