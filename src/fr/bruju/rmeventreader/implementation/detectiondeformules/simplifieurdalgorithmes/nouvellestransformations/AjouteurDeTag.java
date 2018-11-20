package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations;

import fr.bruju.util.table.Enregistrement;
import fr.bruju.util.table.Table;

import java.util.function.Function;

public class AjouteurDeTag implements NouveauTransformateur {

	private final String nom;
	private final Function<Enregistrement, Object> generateur;

	public AjouteurDeTag(String nom, Function<Enregistrement, Object> generateur) {
		this.nom = nom;
		this.generateur = generateur;
	}


	@Override
	public Table appliquer(Table table) {
		table.insererChamp(-1, nom, generateur);
		return table;
	}
}
