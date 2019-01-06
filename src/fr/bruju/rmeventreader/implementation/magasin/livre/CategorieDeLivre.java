package fr.bruju.rmeventreader.implementation.magasin.livre;

import fr.bruju.rmeventreader.implementation.magasin.Magasin;
import fr.bruju.rmeventreader.implementation.magasin.objet.Livre;

import java.util.*;

/**
 * Cette classe donne pour chaque livre qu'elle contient la liste des magasins qui le vend au prix le moins élevé.
 * Les livres contenus sont considérés comme augmentant la statistique augmentée donnée lors de la création de la
 * classe.
 */
public class CategorieDeLivre {
	/** Statistique augmentée par tous les livres contenus dans cette catégorie */
	public final Livre.StatistiqueDeLivre statistiqueAugmentee;

	/** Liste des livres achetables et les magasins où ils sont disponibles */
	private Map<Livre, List<Magasin>> livresAchetables;

	/**
	 * Crée une catégorie de livres
	 * @param statistiqueAugmentee La statistique augmentée par tous les livres qui seront contenus
	 */
	public CategorieDeLivre(Livre.StatistiqueDeLivre statistiqueAugmentee) {
		this.statistiqueAugmentee = statistiqueAugmentee;
		livresAchetables = new TreeMap<>();
	}

	/**
	 * Ajoute un livre à cette catégorie
	 * @param livre Le livre
	 * @param magasin Le magasin vendant le livre
	 */
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

	/**
	 * Donne le nombre de livres
	 * @return Le nombre de livres
	 */
	public int nombreDeLivres() {
		return livresAchetables.keySet().size();
	}

	/**
	 * Donne un affichage de la catégorie
	 * @return Un affichage du type
	 * <pre>
	 *      x Statistique
	 *      - IdLibre : NomLivre [VariationDePrix ; Lieux des magasins]
	 * </pre>
	 */
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
