package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.assignationdevaleurs.ClassificationCible;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Unificateur;

public class UnificateurIdentique implements Unificateur {


	@Override
	public AlgorithmeEtiquete unifier(AlgorithmeEtiquete algo1, AlgorithmeEtiquete algo2) {
		if (!algo1.getAlgorithme().estIdentique(algo2.getAlgorithme())) {
			return null;
		}

		Classificateur classificateur = new ClassificationCible(ClassificationCible.Cible.PlusieursCibles);
		return new AlgorithmeEtiquete(algo1, algo2, algo1.getAlgorithme(), classificateur);
	}
}
