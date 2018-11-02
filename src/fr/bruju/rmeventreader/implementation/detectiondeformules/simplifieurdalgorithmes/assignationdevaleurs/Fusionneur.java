package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.BaseDePersonnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.Personnage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Unificateur;

import java.util.function.Supplier;

public class Fusionneur implements Unificateur {
	@Override
	public AlgorithmeEtiquete unifier(AlgorithmeEtiquete algo1, AlgorithmeEtiquete algo2, BaseDePersonnages personnages) {

		Focaliseur.ClassificateurMonstreCible c1 = (Focaliseur.ClassificateurMonstreCible) algo1.getClassificateurs()[algo1.getClassificateurs().length - 1];
		Focaliseur.ClassificateurMonstreCible c2 = (Focaliseur.ClassificateurMonstreCible) algo2.getClassificateurs()[algo2.getClassificateurs().length - 1];

		Personnage p1 = personnages.getPersonnage("Monstre" + (c1.idMonstre + 1));
		Personnage p2 = personnages.getPersonnage("Monstre" + (c2.idMonstre + 1));

		Supplier<InstructionGenerale> iterateur1 = algo1.getAlgorithme().getIterateur();
		Supplier<InstructionGenerale> iterateur2 = algo2.getAlgorithme().getIterateur();

		ContexteDeSubstitution contexteDeSubstitution = new ContexteDeSubstitution(p2, p1);

		while (true) {
			InstructionGenerale i1 = iterateur1.get();
			InstructionGenerale i2 = iterateur2.get();




		}
	}
}
