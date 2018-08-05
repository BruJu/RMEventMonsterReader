package fr.bruju.rmeventreader.implementation.recomposeur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.AutoActionMaker;
import fr.bruju.rmeventreader.actionmakers.composition.actionmaker.ComposeurInitial;
import fr.bruju.rmeventreader.actionmakers.composition.actionmaker.Extracteur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
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

		/*
		carteAremplir.entrySet().stream()
		.forEach(entry -> entry.getValue().forEach((a, b) -> System.out.println("A = " + a + " ;;; " + b)));
		*/

		enregistrerDansFichier(carteAremplir);

	}

	private static void enregistrerDansFichier(Map<Header, Map<Integer, Algorithme>> carteAremplir) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		File f = new File("sorties/recompo_" + sdf.format(timestamp) + ".txt");

		try {
			f.createNewFile();
			FileWriter ff = new FileWriter(f);
			carteAremplir.entrySet().stream().forEach(entry -> {
				try {
					ff.write(entry.getKey() + "\n");
					entry.getValue().forEach((a, b) -> {
						try {
							ff.write("A = " + a + " ;;; " + b + "\n");
						} catch (IOException e) {
							e.printStackTrace();
						}
					});
				} catch (IOException e) {
					e.printStackTrace();
				}

			});

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
