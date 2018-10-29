package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Unificateur;

import java.util.function.Supplier;

public class Fusionneur implements Unificateur {
	@Override
	public AlgorithmeEtiquete unifier(AlgorithmeEtiquete algo1, AlgorithmeEtiquete algo2) {

		Supplier<InstructionGenerale> iterateur1 = algo1.getAlgorithme().getIterateur();
		Supplier<InstructionGenerale> iterateur2 = algo2.getAlgorithme().getIterateur();


		ContexteDeSubstitution contexteDeSubstitution = new ContexteDeSubstitution(null, null );



		return null;
	}
}
