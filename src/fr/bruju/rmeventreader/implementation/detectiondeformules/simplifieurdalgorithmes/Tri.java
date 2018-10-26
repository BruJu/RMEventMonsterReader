package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;

import java.util.Comparator;
import java.util.List;

public class Tri implements ManipulateurDeListe {

	@Override
	public List<AlgorithmeEtiquete> manipuler(List<AlgorithmeEtiquete> liste) {
		liste.sort(AlgorithmeEtiquete::comparer);
		return liste;
	}
}
