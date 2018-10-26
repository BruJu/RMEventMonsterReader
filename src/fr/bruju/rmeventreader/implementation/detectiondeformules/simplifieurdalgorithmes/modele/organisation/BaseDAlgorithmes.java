package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Separateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;

import java.util.ArrayList;
import java.util.List;

public class BaseDAlgorithmes implements Transformateur.Visiteur {
	private List<AlgorithmeEtiquete> algorithmes;

	public BaseDAlgorithmes() {
		this.algorithmes = new ArrayList<>();
	}

	public void ajouter(AlgorithmeEtiquete algorithme) {
		algorithmes.add(algorithme);
	}

	public String getString() {
		StringBuilder stringBuilder = new StringBuilder();

		for (AlgorithmeEtiquete algorithme : algorithmes) {
			stringBuilder.append(algorithme.getString());
		}

		return stringBuilder.toString();
	}

	public void transformer(Transformateur simplification) {
		visit(simplification);
	}

	@Override
	public void visit(Simplification simplification) {
		for (AlgorithmeEtiquete algorithme : algorithmes) {
			algorithme.simplifier(simplification);
		}
	}

	@Override
	public void visit(Separateur separateur) {
		List<AlgorithmeEtiquete> nouveauxAlgorithmes = new ArrayList<>();

		for (AlgorithmeEtiquete algorithme : algorithmes) {
			separateur.separer(nouveauxAlgorithmes::add, algorithme);
		}

		algorithmes = nouveauxAlgorithmes;
	}
}
