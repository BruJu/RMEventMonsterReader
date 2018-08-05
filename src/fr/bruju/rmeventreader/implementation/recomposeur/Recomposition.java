package fr.bruju.rmeventreader.implementation.recomposeur;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.AutoActionMaker;
import fr.bruju.rmeventreader.implementation.recomposeur.actionmaker.ComposeurInitial;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Personnage;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.Header;

public class Recomposition {
	private final static String CHEMIN_PARAMETRES = "ressources\\recomposeur\\Parametres.txt";
	private final static String CHEMIN_ATTAQUES = "ressources\\recomposeur\\attaques";
	
	
	
	public static void exploiter() {
		Parametres parametres = new Parametres(CHEMIN_PARAMETRES);
		
		BaseDeVariables base = new BaseDeVariables();
		base.remplir(parametres);
		
		
		
		Map<Header, Map<Integer, Algorithme>> carteAremplir = new HashMap<>();
		
		MaillonRemplissage(base, carteAremplir);
		
		
		carteAremplir.entrySet().stream()
		.filter(a -> a.getKey().toString().equals("Yeranae.Congelation"))
		.forEach(entry -> entry.getValue().forEach((a, b) -> System.out.println("A = " + a + " ;;; " + b)));
		
		
		
		
		
	}



	private static void MaillonRemplissage(BaseDeVariables base, Map<Header, Map<Integer, Algorithme>> carteAremplir) {
		File dossierAttaques = new File(CHEMIN_ATTAQUES);
		
		for (String nomPerso : dossierAttaques.list()) {
			File sousDossier = new File(dossierAttaques + "/" + nomPerso);
			
			for (String nomAttaqueTXT : sousDossier.list()) {
				String nomAttaque = nomAttaqueTXT.substring(0, nomAttaqueTXT.length() - 4);
				
				String fichierComplet = dossierAttaques + "\\" + nomPerso + "\\" + nomAttaqueTXT;
				
				ComposeurInitial composeur = new ComposeurInitial(base.getVariablesStatistiques());
				AutoActionMaker autoActionMaker = new AutoActionMaker(composeur, fichierComplet);
				
				autoActionMaker.run();
				
				Map<Integer, Algorithme> r = composeur.getResultat();
				
				carteAremplir.put(new Header(nomPerso, nomAttaque, base), r);
			}
			
			
			
		}
		
		
		
	}
}
