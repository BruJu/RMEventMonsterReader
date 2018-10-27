package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.ManipulateurDeListe;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import java.util.List;

public class Tri implements ManipulateurDeListe {
	@Override
	public List<AlgorithmeEtiquete> manipuler(List<AlgorithmeEtiquete> liste) {
		liste.sort((a1, a2) -> Utilitaire.comparerIterateurs(a1.new IterateurDeClassificateurs(),
						                      				 a2.new IterateurDeClassificateurs(),
						                      				 Classificateur::comparer));
		return liste;
	}
}
