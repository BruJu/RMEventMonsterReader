package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;

import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class BaseDAlgorithmes {
	private List<AlgorithmeEtiquete> algorithmes;

	public BaseDAlgorithmes() {
		this.algorithmes = new ArrayList<>();
	}

	public void ajouter(AlgorithmeEtiquete algorithme) {
		algorithmes.add(algorithme);
	}

	public void transformer(Simplification simplification) {
		for (AlgorithmeEtiquete algorithme : algorithmes) {
			algorithme.simplifier(simplification);
		}
	}

	public String getString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (AlgorithmeEtiquete algorithme : algorithmes) {
			stringBuilder.append(algorithme.getString());
		}

		return stringBuilder.toString();
	}
}
