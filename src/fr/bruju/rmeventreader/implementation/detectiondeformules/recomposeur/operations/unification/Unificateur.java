package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.operations.unification;

import fr.bruju.rmeventreader.implementation.detectiondeformules._personnages.Personne;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.arbre.Resultat;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.StatsRecomposition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.visiteur.template.VisiteurConstructeur;

public class Unificateur extends VisiteurConstructeur {
	private Personne<StatsRecomposition> source;
	private Personne<StatsRecomposition> destination;

	public Unificateur(Personne<StatsRecomposition> source, Personne<StatsRecomposition> destination) {
		this.source = source;
		this.destination = destination;
	}

	public static Resultat fusionner(Resultat r1, Resultat r2, BaseDeVariables base) {
		// Unifier personnages
		Personne<StatsRecomposition> p1 = r1.stat.possesseur;
		Personne<StatsRecomposition> p2 = r2.stat.possesseur;
		Personne<StatsRecomposition> unifie = base.unifierPersonnage(p1, p2);
		
		// Traitement
		Algorithme reponse1 = new Unificateur(p1, unifie).traiter(r1.algo);
		Algorithme reponse2 = new Unificateur(p2, unifie).traiter(r2.algo);

		// Retour
		if (!reponse1.equals(reponse2)) {
			return null; 
		}
		
		Statistique statFusionnee = unifie.getVariablesAssociees().get(r1.stat.nom);
		return new Resultat(statFusionnee, reponse1);
	}

	@Override
	protected Valeur modifier(Entree element) {
		String nomStatistique = source.getVariablesAssociees().getNomStatistique(element.id);
		
		if (nomStatistique == null) {
			return element;
		} else {
			return new Entree(destination.getVariablesAssociees().get(nomStatistique).position);
		}
	}
}
