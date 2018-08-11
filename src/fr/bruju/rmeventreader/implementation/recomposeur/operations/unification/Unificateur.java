package fr.bruju.rmeventreader.implementation.recomposeur.operations.unification;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Entree;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Valeur;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurConstructeur;
import fr.bruju.rmeventreader.implementation.recomposeur.arbre.Resultat;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Personnage;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;

public class Unificateur extends VisiteurConstructeur {
	private Personnage source;
	private Personnage destination;

	public Unificateur(Personnage source, Personnage destination) {
		this.source = source;
		this.destination = destination;
	}

	public static Resultat fusionner(Resultat r1, Resultat r2, BaseDeVariables base) {
		// Unifier personnages
		Personnage p1 = r1.stat.possesseur;
		Personnage p2 = r2.stat.possesseur;
		Personnage unifie = base.unifierPersonnage(p1, p2);
		
		// Traitement
		
		Algorithme reponse1 = new Unificateur(p1, unifie).traiter(r1.algo);
		Algorithme reponse2 = new Unificateur(p2, unifie).traiter(r2.algo);

		// Retour
		if (!reponse1.equals(reponse2)) {
			return null; 
		}
		
		Statistique statFusionnee = unifie.getStatistiques().get(r1.stat.nom);
		return new Resultat(statFusionnee, reponse1);
	}

	@Override
	protected Valeur modifier(Entree element) {
		int idVariable = element.id;
		
		String nomStatistique = source.getStatistique(idVariable);
		
		if (nomStatistique == null) {
			return element;
		} else {
			return new Entree(destination.getStatistiques().get(nomStatistique).position);
		}
	}
	
	
	
	
	
	
	
}
