package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import java.util.List;

/**
 * Trie les algorithmes en comparant les classificateurs
 */
public class Tri implements ManipulateurDeListe {
	@Override
	public List<AlgorithmeEtiquete> manipuler(List<AlgorithmeEtiquete> liste) {
		liste.sort(this::comparerDeuxAlgorithmesEtiquetes);
		return liste;
	}

	private int comparerDeuxAlgorithmesEtiquetes(AlgorithmeEtiquete a1, AlgorithmeEtiquete a2) {
		return Utilitaire.comparerIterateurs(a1.new IterateurDeClassificateurs(),
				a2.new IterateurDeClassificateurs(),
				Classificateur::comparer);
	}
}
