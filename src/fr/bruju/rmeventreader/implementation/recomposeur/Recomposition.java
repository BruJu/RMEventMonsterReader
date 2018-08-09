package fr.bruju.rmeventreader.implementation.recomposeur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.composition.actionmaker.Extracteur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.Ensemble;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.Header;
import fr.bruju.rmeventreader.implementation.recomposeur.maillon.FormuleToString;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.desinjection.PreTraitementDesinjection;

public class Recomposition {
	private final static String CHEMIN_PARAMETRES = "ressources\\recomposeur\\Parametres.txt";
	private final static String CHEMIN_ATTAQUES = "ressources\\recomposeur\\attaques";

	public static void exploiter() {
		new Recomposition().exp();
	}
	
	
	Parametres parametres;
	BaseDeVariables base;
	
	private void exp() {
		parametres = new Parametres(CHEMIN_PARAMETRES);
		base = new BaseDeVariables();
		
		base.remplir(parametres);

		Map<Header, Map<Integer, Algorithme>> carteAremplir = new HashMap<>();

		MaillonRemplissage(base, carteAremplir);
		
		Ensemble ens = new Ensemble(carteAremplir, base);
		
		
		/*		Opérations dans FormulaTracker :
				new MaillonUnificateur(),
				new Factorisation(),
				new Borne(),
				new Encadrer(),
				new MaillonDiviseur(),
		 */
		FormuleToString fts = new FormuleToString(base);
		
		String sortie = ens
			.reconstruire(new Injecteur(parametres))
			.injecterHeader(new PreTraitementDesinjection(parametres))
			.getMap()
			.entrySet()
			.stream()
			.map(entry -> entry.getKey().toString() + " ;;; " + fts.traiter(entry.getValue()).s)
			.collect(Collectors.joining("\n"));
		
		
		//System.out.print(sortie);
		
		enregistrerDansFichier(sortie);

	}

	private static void enregistrerDansFichier(String sortie) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		File f = new File("sorties/recompo_" + sdf.format(timestamp) + ".txt");

		try {
			f.createNewFile();
			FileWriter ff = new FileWriter(f);
			ff.write(sortie);

			ff.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void MaillonRemplissage(BaseDeVariables base, Map<Header, Map<Integer, Algorithme>> carteAremplir) {
		File dossierAttaques = new File(CHEMIN_ATTAQUES);

		for (String nomPerso : dossierAttaques.list()) {
			File sousDossier = new File(dossierAttaques + "/" + nomPerso);

			for (String nomAttaqueTXT : sousDossier.list()) {
				String nomAttaque = nomAttaqueTXT.substring(0, nomAttaqueTXT.length() - 4);

				String fichierComplet = dossierAttaques + "\\" + nomPerso + "\\" + nomAttaqueTXT;

				carteAremplir.put(new Header(nomPerso, nomAttaque, base),
						new Extracteur().extraireAlgorithmes(base.getVariablesStatistiques(), fichierComplet));
			}

		}

	}
}
