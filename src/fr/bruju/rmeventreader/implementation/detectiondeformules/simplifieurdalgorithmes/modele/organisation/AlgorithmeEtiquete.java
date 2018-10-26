package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;

import java.util.Arrays;


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

	public AlgorithmeEtiquete(AlgorithmeEtiquete algo1, AlgorithmeEtiquete algo2, Algorithme nouvelAlgorithme) {
		this.classificateurs = Arrays.copyOf(algo1.classificateurs, algo1.classificateurs.length);
		this.algorithme = nouvelAlgorithme;
		classificateurs[classificateurs.length - 1] = classificateurs[classificateurs.length - 1].ajouter(algo2.classificateurs[classificateurs.length - 1]);
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


	public static boolean classificationUnifiable(AlgorithmeEtiquete a1, AlgorithmeEtiquete a2) {
		if (a1.classificateurs.length != a2.classificateurs.length) {
			return false;
		}

		for (int i = 0 ; i != a1.classificateurs.length ; i++) {
			if (!a1.classificateurs[i].estUnifiable(a2.classificateurs[i])) {
				return false;
			}
		}

		return true;
	}

	public static int comparer(AlgorithmeEtiquete algorithmeEtiquete, AlgorithmeEtiquete algorithmeEtiquete1) {
		int i;

		for (i = 0 ; i != algorithmeEtiquete.classificateurs.length ; i++) {

			if (algorithmeEtiquete1.classificateurs.length < i) {
				return -1;
			}

			int comparaison = algorithmeEtiquete.classificateurs[i].comparer(algorithmeEtiquete1.classificateurs[i]);

			if (comparaison != 0) {
				return comparaison;
			}


		}

		if (algorithmeEtiquete1.classificateurs.length >= i) {
			return 1;
		} else {
			return 0;
		}


	}
}
