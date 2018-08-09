package fr.bruju.rmeventreader.implementation.recomposeur;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.composition.actionmaker.Extracteur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.arbre.Arbre;
import fr.bruju.rmeventreader.implementation.recomposeur.arbre.Experimentation;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.Ensemble;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.Header;
import fr.bruju.rmeventreader.implementation.recomposeur.maillon.FormuleToString;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.desinjection.PreTraitementDesinjection;
import fr.bruju.rmeventreader.utilitaire.Triplet;

public class Recomposition {
	private final static String CHEMIN_PARAMETRES = "ressources\\recomposeur\\Parametres.txt";
	private final static String CHEMIN_ATTAQUES = "ressources\\recomposeur\\attaques";

	public static void exploiter() {
		new Recomposition().exp();
	}

	Parametres parametres;
	BaseDeVariables base;

	@SuppressWarnings("unused")
	private void exp() {
		parametres = new Parametres(CHEMIN_PARAMETRES);
		base = new BaseDeVariables();

		base.remplir(parametres);

		visionArbre();

		if (true)
			return;

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

		String sortie = ens.reconstruire(new Injecteur(parametres))
				.injecterHeader(new PreTraitementDesinjection(parametres)).getMap().entrySet().stream()
				.map(entry -> entry.getKey().toString() + " ;;; " + fts.traiter(entry.getValue()).s)
				.collect(Collectors.joining("\n"));

		//System.out.print(sortie);

		enregistrerDansFichier(sortie);

	}

	private void visionArbre() {
		Arbre arbre = construireArbre();

		// Traitements de l'arbre

		// Recupération des fruits

		List<Triplet<List<GroupeDeConditions>, Statistique, Algorithme>> fruits = arbre.getFruits();

		String sortie = fruits.stream().map(this::construireSortie).collect(Collectors.joining("\n"));

		enregistrerDansFichier(sortie);
	}

	private String construireSortie(Triplet<List<GroupeDeConditions>, Statistique, Algorithme> triplet) {
		StringBuilder sb = new StringBuilder();

		sb.append(triplet.a.get(0).conditions.get(0).getString()).append(";")
				.append(triplet.a.get(1).conditions.get(0).getString()).append(";").append(triplet.b.toString())
				.append(";");

		triplet.a.subList(2, triplet.a.size()).stream().map(groupe -> groupe.toString())
				.forEach(s -> sb.append(s).append(";"));

		sb.append(triplet.c.toString());

		return sb.toString();
	}

	private Arbre construireArbre() {
		File dossierAttaques = new File(CHEMIN_ATTAQUES);

		Experimentation exp = new Experimentation(base);

		for (String nomPerso : dossierAttaques.list()) {
			File sousDossier = new File(dossierAttaques + "/" + nomPerso);

			for (String nomAttaqueTXT : sousDossier.list()) {
				String nomAttaque = nomAttaqueTXT.substring(0, nomAttaqueTXT.length() - 4);

				String fichierComplet = dossierAttaques + "\\" + nomPerso + "\\" + nomAttaqueTXT;

				Map<Integer, Algorithme> extrait = new Extracteur().extraireAlgorithmes(base.getVariablesStatistiques(),
						fichierComplet);

				exp.add(nomPerso, nomAttaque, extrait);
			}

		}

		return exp.creerArbre();
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
