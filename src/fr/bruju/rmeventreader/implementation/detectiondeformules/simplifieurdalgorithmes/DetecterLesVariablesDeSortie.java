package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.AjouteurDeTag;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.TransformationDeTable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.inliner.ListeurDeVariablesDeSorties;
import fr.bruju.util.table.Enregistrement;

public class DetecterLesVariablesDeSortie extends AjouteurDeTag {
	public DetecterLesVariablesDeSortie() {
		super("Sorties");
	}

	@Override
	protected Object genererNouveauChamp(Enregistrement enregistrement) {
		return ListeurDeVariablesDeSorties.lireLesVariablesVivantes(enregistrement.get("Algorithme"));
	}
}
