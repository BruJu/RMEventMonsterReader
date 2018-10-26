package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;

import java.util.Arrays;
import java.util.function.UnaryOperator;


public class AlgorithmeEtiquete {

	private final Classificateur[] classificateurs;
	private Algorithme algorithme;

	public AlgorithmeEtiquete(Classificateur[] classificateurs, Algorithme algorithme) {
		this.classificateurs = classificateurs;
		this.algorithme = algorithme;
	}

	public AlgorithmeEtiquete(AlgorithmeEtiquete base, Classificateur nouvelleClassification, Algorithme nouvelAlgorithme) {
		this.classificateurs = Arrays.copyOf(base.classificateurs, base.classificateurs.length + 1);
		this.classificateurs[base.classificateurs.length] = nouvelleClassification;
		this.algorithme = nouvelAlgorithme;
	}

	public void simplifier(Simplification simplification) {
		algorithme = simplification.simplifier(algorithme);
	}


	public String getString() {
		StringBuilder sb = new StringBuilder();

		sb.append("== ");

		for (Classificateur classificateur : classificateurs) {
			sb.append(classificateur.toString()).append(" ");
		}

		sb.append("==\n").append(algorithme.getString()).append("\n");

		return sb.toString();
	}

	public Algorithme getAlgorithme() {
		return algorithme;
	}
}
