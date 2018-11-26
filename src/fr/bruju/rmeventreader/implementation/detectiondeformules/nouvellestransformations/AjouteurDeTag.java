package fr.bruju.rmeventreader.implementation.detectiondeformules.nouvellestransformations;

import fr.bruju.util.table.Enregistrement;
import fr.bruju.util.table.Table;

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

	protected abstract Object genererNouveauChamp(Enregistrement enregistrement);
}
