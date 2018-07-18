package fr.bruju.rmeventreader.rmdatabase;

import java.util.List;

import fr.bruju.rmeventreader.utilitaire.Pair;

public class RMEventDatabaseMain {

	public static void main_() {
		BaseDeDonneesRPGMaker bdd = BaseDeDonneesRPGMaker.Builder.parDefaut();

		List<Pair<String, String>> stats = Utilitaire.lireFichierRessource("ressources/statistiques/Ufa.txt");

		stats.forEach(paire -> System.out.println(paire.getRight() + " : " + paire.getLeft() + " - "
				+ bdd.getNomDeLaVariable(Integer.parseInt(paire.getRight()))));

		AffectationBaseDeDonnees affectation = AffectationBaseDeDonnees.Builder.parDefaut(bdd);
		
		affectation.display();
	}
}
