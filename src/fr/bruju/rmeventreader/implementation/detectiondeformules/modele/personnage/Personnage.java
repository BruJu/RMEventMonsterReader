package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Statistique;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

public abstract class Personnage {
	public abstract String getNom();

	public abstract void ajouterPersonnage(Set<Individu> set);

	private Map<String, Statistique> statistiquesPossedees = new HashMap<>();

	public final void ajouterStatistique(Statistique statistique) {
		statistiquesPossedees.put(statistique.nom, statistique);
	}

	protected final void forEachStatistique(Consumer<Statistique> consumer) {
		statistiquesPossedees.values().forEach(consumer);
	}

	public Statistique getStatistique(String nom) {
		return statistiquesPossedees.get(nom);
	}
}
