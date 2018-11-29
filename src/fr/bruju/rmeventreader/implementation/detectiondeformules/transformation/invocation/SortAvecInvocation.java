package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.invocation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.interfaces.TransformationDeTable;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;
import fr.bruju.util.Pair;
import fr.bruju.util.table.Table;

import java.util.HashMap;
import java.util.Map;

public class SortAvecInvocation implements TransformationDeTable {


	private static final String CHEMIN_INVOCATIONS = "ressources/Invocations.txt";

	@Override
	public Table appliquer(Table table) {
		table = new ProjecteurInvocation().appliquer(table);
		Pair<Map<String, String>, Map<String, String>> nomsDesSorts = lireNomDesSorts();

		table.forEach(enregistrement ->  {
			Integer numeroInvocation = enregistrement.get("Invocations");
			if (numeroInvocation == 0) {
				return;
			}

			String nomAttaque = enregistrement.get("Attaque");
			String nomRecompose = nomAttaque + " - ";

			if (numeroInvocation == 68) {
				nomRecompose += nomsDesSorts.getLeft().get(nomAttaque);
			} else if (numeroInvocation == 69) {
				nomRecompose += nomsDesSorts.getRight().get(nomAttaque);
			}

			enregistrement.set("Attaque", nomRecompose);
		});

		table.retirerChamp("Invocations");
		return table;
	}

	private Pair<Map<String, String>, Map<String, String>> lireNomDesSorts() {
		Pair<Map<String, String>, Map<String, String>> map = new Pair<>(new HashMap<>(), new HashMap<>());

		LecteurDeFichiersLigneParLigne.lectureFichierRessources(CHEMIN_INVOCATIONS, ligne -> {
			String[] donnees = LecteurDeFichiersLigneParLigne.diviser(ligne, 3);

			if (donnees[1].equals("68")) {
				map.getLeft().put(donnees[0], donnees[2]);
			} else {
				map.getRight().put(donnees[0], donnees[2]);
			}
		});

		return map;
	}
}
