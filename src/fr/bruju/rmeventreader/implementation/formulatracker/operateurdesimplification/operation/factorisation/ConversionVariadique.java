package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.factorisation;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu.E_CalculVariadique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class ConversionVariadique extends ConstructeurDeComposantsRecursif implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}


	private void ajouter(List<Valeur> valeurs, List<Boolean> booleens, Operator base, Valeur valeur) {
		int prioriteActuel = Utilitaire.getPriorite(base);
		
		if (valeur instanceof E_CalculVariadique) {
			E_CalculVariadique ecv = (E_CalculVariadique) valeur;
			
			int prioriteValeur = Utilitaire.getPriorite(ecv.base);
			
			if (prioriteActuel == prioriteValeur) {
				valeurs.addAll(ecv.valeurs);
				booleens.addAll(ecv.inverses);				
				return;
			}
		}
		
		valeurs.add(valeur);
		booleens.add(true);
	}
	
	@Override
	protected Composant modifier(VCalcul variableCalcul) {
		Valeur g = variableCalcul.gauche;
		Valeur d = variableCalcul.droite;
		
		Operator operateur = variableCalcul.operateur;
		Operator base = Operator.sensConventionnel(operateur);
		List<Valeur> valeurs = new ArrayList<>();
		List<Boolean> booleens = new ArrayList<>();
		
		if (g instanceof VCalcul || d instanceof VCalcul) {
			throw new RuntimeException("Erreur de reccursion");
		}
		
		ajouter(valeurs, booleens, base, g);
		int dernierIndex = booleens.size();
		ajouter(valeurs, booleens, base, d);
		booleens.set(dernierIndex, operateur == base);
		
		return new E_CalculVariadique(operateur, valeurs, booleens);
	}
	
	
	

	
	
}
