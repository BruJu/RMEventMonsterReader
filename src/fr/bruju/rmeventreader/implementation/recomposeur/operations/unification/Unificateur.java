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
		System.out.println("!");
		
		// Unifier personnages
		Personnage p1 = r1.stat.possesseur;
		Personnage p2 = r2.stat.possesseur;
		Personnage unifie = base.unifierPersonnage(p1, p2);
		
		// Traitement
		
		Algorithme zzreponse1 = new Unificateur(p1, unifie).traiter(r1.algo);
		Algorithme zzreponse2 = new Unificateur(p2, unifie).traiter(r2.algo);

		System.out.println("===== ");
		System.out.println(r1.algo.toString());
		System.out.println(r2.algo.toString());
		System.out.println(zzreponse1.toString());
		System.out.println(zzreponse2.toString());
		System.out.println(zzreponse1.equals(zzreponse2));
		System.out.println("=====");
		
		// Retour
		if (!zzreponse1.equals(zzreponse2)) {
			
			
			return null; 
		}
		
		
		Statistique statFusionnee = unifie.getStatistiques().get(r1.stat.nom);
		return new Resultat(statFusionnee, zzreponse1);
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
