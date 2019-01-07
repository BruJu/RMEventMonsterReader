package fr.bruju.rmeventreader.implementation.magasin;

import fr.bruju.rmeventreader.implementation.magasin.objet.Objet;

import java.util.Set;
import java.util.TreeSet;

/**
 * Représente un magasin dans le jeu
 */
public class Magasin {
	/** ID du magasin */
	public final int idMagasin;
	/** Arborescence menant à ce magasin */
	private String cheminMap;
	/** Niveau minimal pour faire un hold up */
	private int niveauHoldup;
	/** Liste des objets vendus */
	private Set<Objet> objets;
	/** Variation de prix du magasin */
	public final int variationPrix;

	/**
	 * Crée un magasin
	 * @param idMagasin ID du magasin
	 * @param cheminMap Emplacement du magasin
	 * @param variationPrix Variation de prix du magasin
	 */
	public Magasin(int idMagasin, String cheminMap, int variationPrix) {
		this.idMagasin = idMagasin;
		this.cheminMap = cheminMap;
		niveauHoldup = 0;
		objets = new TreeSet<>();
		this.variationPrix = variationPrix;
	}

	/**
	 * Crée un nouveau magasin possédant le même id, emplacement et niveau de hold up que le magasin source. Ne reprend
	 * pas les objets vendus.
	 * @param source Le magasin source
	 */
	public Magasin(Magasin source) {
		this.idMagasin = source.idMagasin;
		this.cheminMap = source.cheminMap;
		niveauHoldup = source.niveauHoldup;
		this.variationPrix = source.variationPrix;
		objets = new TreeSet<>();
	}

	/**
	 * Ajoute l'objet à la liste des objets vendus par ce magasin
	 * @param objet L'objet à ajouter
	 */
	public void ajouterObjet(Objet objet) {
		objets.add(objet);
	}

	/**
	 * Modifie le niveau pour faire un hold up
	 * @param niveau Le niveau pour faire un hold up
	 */
	public void setNiveauHoldup(int niveau) {
		this.niveauHoldup = niveau;
	}
	
	/**
	 * Renvoie vrai si le magasin vend l'objet dont le numéro est donné
	 * @param idObjet Le numéro de l'objet
	 * @return Vrai si le magasin vend un objet contenant cet id.
	 */
	public boolean possedeLObjetdID(int idObjet) {
		for (Objet objet : objets) {
			if (objet.id == idObjet) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Donne le lieu où se trouve le magasin
	 * @return Le lieu où se trouve le magasin
	 */
	public String getLieu() {
		return cheminMap;
	}

	/**
	 * Permet de savoir si des objets sont vendus
	 * @return Vrai si au moins un objet est vendu
	 */
	public boolean vendUnObjet() {
		return !objets.isEmpty();
	}

	/**
	 * Donne la liste des objets vendus
	 * @return La liste des objets vendus
	 */
	public Iterable<Objet> objetsVendus() {
		return objets;
	}


	/**
	 * Donne une représentation avec seulement le lieu du magasin et le niveau pour le voler
	 * @return <code>cheminMap + " @ " + niveauHoldup;</code>
	 */
	public String getMagasinCompact() {
		return cheminMap + " @ " + niveauHoldup;
	}

	/**
	 * Donne une représentation complète du magasin sur plusieurs lignes
	 */
	public String getMagasinComplet() {
		StringBuilder sb = new StringBuilder();

		sb.append("> ")
				.append(cheminMap)
				.append("\n")
				.append("ID : ")
				.append(idMagasin)
				.append("\n")
				.append("Niveau :")
				.append(niveauHoldup)
				.append("\n")
				.append("Objets :");

		objets.forEach(objet -> sb.append("\n").append(objet.getString()));

		sb.append("\n");

		return sb.toString();
	}

	/**
	 * Transforme la variation de prix en un pourcentage
	 * @param variableDeBase La variation déclarée dans le jeu
	 * @return La variation représentée sous forme de pourcentage x 100
	 */
	public static int transformerVariation(int variableDeBase) {
		switch (variableDeBase) {
			case 0:
			case 1:
				return 100;
			case 2:
				return 200;
			case 15:
				return 150;
			case -2:
				return 50;
			case -15:
				return 66;
			default:
				return variableDeBase;
		}
	}
}
