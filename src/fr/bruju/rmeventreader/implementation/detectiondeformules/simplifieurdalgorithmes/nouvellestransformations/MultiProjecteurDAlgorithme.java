package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.util.Pair;
import fr.bruju.util.table.Table;

import java.util.ArrayList;
import java.util.List;

/**
 * Transforme un algorithme en plusieurs autres algorithmes et ajoute un nouveau champ à la table pour indiquer comment
 * les projections ont été faites.
 */
public abstract class MultiProjecteurDAlgorithme implements TransformationDeTable {
	private final String nomDuNouveauChamp;

	public MultiProjecteurDAlgorithme(String nomDuNouveauChamp) {
		this.nomDuNouveauChamp = nomDuNouveauChamp;
	}

	/**
	 * Projète l'algorithme pour produire de nouveaux algorithmes selon plusieurs angles de projection
	 * @param algorithme L'algorithme à projeter
	 * @return Une liste de paire algorithme produit - projection faite
	 */
	protected abstract List<Pair<Algorithme, Object>> projeter(Algorithme algorithme);


	@Override
	public final Table appliquer(Table table) {
		Table nouvelleTable = new Table();

		for (String s : table.getChamps()) {
			nouvelleTable.insererChamp(-1, s, null);
		}

		nouvelleTable.insererChamp(-1, nomDuNouveauChamp, null);

		int positionAlgorithme = table.getPosition("Algorithme");

		table.forEach(enregistrement -> {
				Algorithme algorithme = enregistrement.get(positionAlgorithme);
				List<Pair<Algorithme, Object>> resultat = projeter(algorithme);

				for (Pair<Algorithme, Object> algorithmeObjectPair : resultat) {
					Algorithme algoProjete = algorithmeObjectPair.getLeft();
					Object projectionUtilisee = algorithmeObjectPair.getRight();

					List<Object> nouvelObjet = new ArrayList<>(enregistrement.getDonnees());
					nouvelObjet.set(positionAlgorithme, algoProjete);
					nouvelObjet.add(projectionUtilisee);

					nouvelleTable.ajouterContenu(nouvelObjet);
				}
			});

		return nouvelleTable;
	}
}
