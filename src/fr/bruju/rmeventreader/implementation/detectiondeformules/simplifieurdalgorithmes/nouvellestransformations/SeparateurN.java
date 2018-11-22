package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.util.Pair;
import fr.bruju.util.table.Table;

import java.util.ArrayList;
import java.util.List;

public abstract class SeparateurN implements TransformationDeTable {
	private final String nom;

	public SeparateurN(String nom) {
		this.nom = nom;
	}


	protected abstract List<Pair<Algorithme, Object>> diviser(Algorithme algorithme);


	@Override
	public final Table appliquer(Table table) {
		Table nouvelleTable = new Table();

		for (String s : table.getChamps()) {
			nouvelleTable.insererChamp(-1, s, null);
		}

		nouvelleTable.insererChamp(-1, nom, null);

		table.forEach(enregistrement -> {
				Algorithme algorithme = enregistrement.get("Algorithme");
				List<Pair<Algorithme, Object>> resultat = diviser(algorithme);

				for (Pair<Algorithme, Object> algorithmeObjectPair : resultat) {
					Algorithme algoTransforme = algorithmeObjectPair.getLeft();
					Object classification = algorithmeObjectPair.getRight();

					List<Object> nouvelObjet = new ArrayList<>();

					enregistrement.reconstruireObjet((nom, objet) -> {

						if (nom.equals("Algorithme")) {
							nouvelObjet.add(algoTransforme);
						} else {
							nouvelObjet.add(objet);
						}
					});

					nouvelObjet.add(classification);

					nouvelleTable.ajouterContenu(nouvelObjet);
				}
			});

		return nouvelleTable;
	}
}
