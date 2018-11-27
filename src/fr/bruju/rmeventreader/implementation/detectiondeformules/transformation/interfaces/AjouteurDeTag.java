package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.interfaces;

import fr.bruju.util.table.Enregistrement;
import fr.bruju.util.table.Table;

/**
 * Ajoute un champ aux enregistrements
 */
public abstract class AjouteurDeTag implements TransformationDeTable {
	private final String nom;

	public AjouteurDeTag(String nom) {
		this.nom = nom;
	}

	@Override
	public final Table appliquer(Table table) {
		table.insererChamp(-1, nom, this::genererNouveauChamp);
		return table;
	}

	/**
	 * Donne l'objet à gérérer pour remplir le champ pour un enregistrement
	 * @param enregistrement L'enregistrement
	 * @return L'objet correspondant à l'enregistrement pour le champ créé.
	 */
	protected abstract Object genererNouveauChamp(Enregistrement enregistrement);
}
