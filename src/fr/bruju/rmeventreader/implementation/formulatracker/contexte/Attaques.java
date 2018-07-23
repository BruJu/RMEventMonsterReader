package fr.bruju.rmeventreader.implementation.formulatracker.contexte;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.attaques.Attaque;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;

public class Attaques {
	private static BiFunction<List<Condition>, UnaryOperator<Composant>, List<Condition>> transListeConditions = (liste,
			func) -> liste.stream().map(func).map(c -> (Condition) c).collect(Collectors.toList());

	public List<Attaque> liste = new ArrayList<>();

	public void ajouterRepertoire(String cheminRepertoire) {
		File repertoire = new File(cheminRepertoire);

		for (String fichier : repertoire.list()) {
			liste.add(getNewAttaque(cheminRepertoire, fichier));
		}
	}

	private Attaque getNewAttaque(String chemin, String fichier) {
		String cheminComplet = chemin + fichier;
		String nomAttaque = fichier.substring(0, fichier.length() - 4);

		return new Attaque(nomAttaque, cheminComplet);
	}

	public Collection<Attaque> getAttaques() {
		return liste;
	}

	public void apply(UnaryOperator<FormuleDeDegats> modificateurDeFormule) {
		liste.stream().map(attaque -> attaque.getResultat()).map(resultat -> resultat.map).forEach(map -> map
				.forEach((k, v) -> map.put(k, v.stream().map(modificateurDeFormule).collect(Collectors.toList()))));
	}

	public void transformerComposants(UnaryOperator<Composant> transformation) {
		liste.stream().map(attaque -> attaque.getResultat().map)
				.forEach(map -> map.replaceAll((cle, valeur) -> valeur.stream()
						.map(formule -> new FormuleDeDegats(formule.operator,
								transListeConditions.apply(formule.conditions, transformation),
								(Valeur) transformation.apply(formule.formule)))
						.collect(Collectors.toList())
						));
	}

}
