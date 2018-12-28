package fr.bruju.rmeventreader.implementation.magasin.livre;

import fr.bruju.rmeventreader.implementation.magasin.Magasin;
import fr.bruju.rmeventreader.implementation.magasin.objet.Livre;

import java.util.*;

/**
 * Regroupe des livres augmentant la même statistique
 */
public class CategorieDeLivre {
	public final Livre.StatistiqueDeLivre statistiqueAugmentee;

	/** Liste des livres achetables et les magasins où ils sont disponibles */
	private Map<Livre, List<Magasin>> livresAchetables;

	public CategorieDeLivre(Livre.StatistiqueDeLivre statistiqueAugmentee) {
		this.statistiqueAugmentee = statistiqueAugmentee;
		livresAchetables = new TreeMap<>();
	}

	public void ajouterLivre(Livre livre, Magasin magasin) {
		if (!livresAchetables.containsKey(livre)) {
			livresAchetables.put(livre, new ArrayList<>());
		}

		List<Magasin> zonesDAchat = livresAchetables.get(livre);

		if (!zonesDAchat.isEmpty() && zonesDAchat.get(0).variationPrix > magasin.variationPrix) {
			zonesDAchat.clear();
		}

		zonesDAchat.add(magasin);
	}

	public int nombreDeLivres() {
		return livresAchetables.keySet().size();
	}

	public String getAffichage() {
		StringBuilder sb = new StringBuilder();

		sb.append(statistiqueAugmentee).append(" x ").append(nombreDeLivres()).append("\n");

		for (Map.Entry<Livre, List<Magasin>> livresEnsemble : livresAchetables.entrySet()) {
			Livre livre = livresEnsemble.getKey();

			sb.append("- ").append(livre.id).append(" : ").append(livre.nom).append(" [");

			sb.append(livresEnsemble.getValue().get(0).variationPrix).append(" : ");

			StringJoiner sj = new StringJoiner(" ; ");

			for (Magasin lieu : livresEnsemble.getValue()) {
				sj.add(lieu.getLieu());
			}

			sb.append(sj).append("]\n");
		}

		return sb.toString();
	}
}
