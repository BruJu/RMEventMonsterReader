package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import javax.xml.ws.Provider;
import java.util.Arrays;
import java.util.function.Supplier;


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

	public AlgorithmeEtiquete(AlgorithmeEtiquete algo1, AlgorithmeEtiquete algo2, Algorithme nouvelAlgorithme,
							  Classificateur nouveauClassificateur) {
		this.classificateurs = Arrays.copyOf(algo1.classificateurs, algo1.classificateurs.length);
		this.algorithme = nouvelAlgorithme;
		classificateurs[classificateurs.length - 1] = nouveauClassificateur;
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

	public Classificateur[] getClassificateurs() {
		return classificateurs;
	}


	public class IterateurDeClassificateurs implements Supplier<Classificateur> {
		int i = 0;

		@Override
		public Classificateur get() {
			return (i == classificateurs.length) ? null : classificateurs[i++];
		}
	}
}
