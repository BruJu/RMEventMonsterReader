package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.inliner;


import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.TransformationDeTable;
import fr.bruju.util.table.Enregistrement;
import fr.bruju.util.table.Table;

import java.util.List;


public class InlinerGlobal implements TransformationDeTable {
	@Override
	public Table appliquer(Table table) {
		table.forEach(this::remplacerAlgorithme);
		return table;
	}

	public void remplacerAlgorithme(Enregistrement enregistrement) {
		List<ExprVariable> variablesVivantes = enregistrement.get("Sorties");
		Algorithme algorithme = enregistrement.get("Algorithme");
		enregistrement.set("Algorithme", enleverInstructionsMortes(variablesVivantes, algorithme));
	}

	public static Algorithme enleverInstructionsMortes(List<ExprVariable> variablesVivantes, Algorithme algorithme) {
		DetecteurDeSimplifications detecteur = new DetecteurDeSimplifications(variablesVivantes);
		algorithme.acceptInverse(detecteur);
		Reecrivain reecrivain = new Reecrivain(algorithme, detecteur);
		return reecrivain.produireResultat();
	}
}
