package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;

import java.util.Stack;

public class Formule implements Element {

	private Algorithme algorithme;

	public Formule(Algorithme algorithme) {
		this.algorithme = algorithme;
	}

	@Override
	public String getString(Stack<String> categories) {
		StringBuilder stringBuilder = new StringBuilder();

		stringBuilder.append("== ");

		for (String categorie : categories) {
			stringBuilder.append(categorie).append(" ");
		}


		stringBuilder.append(" ==\n").append(algorithme.getString()).append("\n");

		return stringBuilder.toString();
	}

	@Override
	public void simplifier(Simplification simplification) {
		algorithme = simplification.simplifier(algorithme);
	}
}
