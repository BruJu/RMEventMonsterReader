package fr.bruju.rmeventreader.implementation.detectiondeformules;

import fr.bruju.rmeventreader.implementation.detectiondeformules.nouvellestransformations.AjouteurDeTag;
import fr.bruju.rmeventreader.implementation.detectiondeformules.nouvellestransformations.TransformationDeTable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformations.inliner.ListeurDeVariablesDeSorties;
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
