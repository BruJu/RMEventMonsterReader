package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.BaseDePersonnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.ManipulateurDeListe;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Separateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Simplification;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Unificateur;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;
import fr.bruju.util.ListUtils;
import fr.bruju.util.similaire.CollectorBySimilarity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class BaseDAlgorithmes implements Transformateur.Visiteur {
	private final BaseDePersonnages baseDePersonnages;
	private List<AlgorithmeEtiquete> algorithmes;

	public BaseDAlgorithmes(BaseDePersonnages baseDePersonnages) {
		this.algorithmes = new ArrayList<>();
		this.baseDePersonnages = baseDePersonnages;
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

	@Override
	public void visit(Unificateur unificateur) {
		CollectorBySimilarity<AlgorithmeEtiquete> collecteur
				= new CollectorBySimilarity<>(a -> 0, this::classifier);

		Collection<List<AlgorithmeEtiquete>> algorithmesClassifies =
				algorithmes.stream().collect(collecteur).getMap().values();

		List<AlgorithmeEtiquete> nouvelleListe = new ArrayList<>();

		for (List<AlgorithmeEtiquete> liste : algorithmesClassifies) {
			nouvelleListe.addAll(ListUtils.fusionnerJusquaStabilite(liste, (a, b) -> unificateur.unifier(a, b, baseDePersonnages)));
		}

		algorithmes = nouvelleListe;
	}

	public boolean classifier(AlgorithmeEtiquete a1, AlgorithmeEtiquete a2) {
		if (a1.getClassificateurs().length != a2.getClassificateurs().length) {
			return false;
		}

		return Utilitaire.comparerIterateursBoolean(a1.new IterateurDeClassificateurs(),
				a2.new IterateurDeClassificateurs(),
				Classificateur::estUnifiable);
	}

	@Override
	public void visit(ManipulateurDeListe manipulateur) {
		algorithmes = manipulateur.manipuler(algorithmes);
	}
}
