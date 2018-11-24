package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.BaseDePersonnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.Personnage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.TransformationDeTable;
import fr.bruju.util.table.Enregistrement;
import fr.bruju.util.table.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class UnifierSubstitutions implements TransformationDeTable {
	private final BaseDePersonnages baseDePersonnages;

	public UnifierSubstitutions(BaseDePersonnages baseDePersonnages) {
		this.baseDePersonnages = baseDePersonnages;
	}

	@Override
	public Table appliquer(Table table) {
		table.unifier(this::unificateur);
		return table;
	}


	private Enregistrement unificateur(Enregistrement e1, Enregistrement e2) {
		if (!e1.get("Personnage").equals(e2.get("Personnage"))
			|| !e1.get("Attaque").equals(e2.get("Attaque"))) {
			return null;
		}


		Personnage p1 = baseDePersonnages.getPersonnage(e1.get("Monstre").toString());
		Personnage p2 = baseDePersonnages.getPersonnage(e2.get("Monstre").toString());
		Personnage unifie = baseDePersonnages.getPersonnageUnifie(p1, p2);

		Algorithme a1 = e1.get("Algorithme");
		Algorithme a2 = e2.get("Algorithme");

		Algorithme nouvelAlgorithme = combiner(a1, p1, a2, p2, unifie);

		if (nouvelAlgorithme == null) {
			return null;
		}

		e1.set("Algorithme", nouvelAlgorithme);
		e1.set("Monstre",
				new SeparateurParHPDeMonstres.ClassificateurMonstreCible(e1.get("Monstre"), e2.get("Monstre")));

		return e1;
	}

	private Algorithme combiner(Algorithme a1, Personnage p1, Algorithme a2, Personnage p2, Personnage unifie) {
		Supplier<InstructionGenerale> iterateur1 = a1.getIterateur();
		Supplier<InstructionGenerale> iterateur2 = a2.getIterateur();





		return a1;
	}


}
