package fr.bruju.rmeventreader.implementation.recomposeur;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.recomposeur.visiteur.template.VisiteurConstructeur;

public class Injecteur extends VisiteurConstructeur {
	Map<Integer, Integer> assocuiationsVariablesValeurs;
	
	public Injecteur(Parametres parametres) {
		assocuiationsVariablesValeurs = new HashMap<>();
		
		parametres.getParametres("Injection").forEach(tableau -> {
			int idVariable = Integer.decode(tableau[0]);
			int idValeur;
			
			if (tableau[1].equals("ON")) {
				idVariable += 5000;
				idValeur = 1;
			} else if (tableau[1].equals("OFF")) {
				idVariable += 5000;
				idValeur = -1;
			} else {
				idValeur = Integer.decode(tableau[1]);
			}
			
			assocuiationsVariablesValeurs.put(idVariable, idValeur);
		});
	}

	@Override
	protected Valeur modifier(Entree element) {
		int idVariable = element.id;
		
		Integer valeur = assocuiationsVariablesValeurs.get(idVariable);
		
		if (valeur == null) {
			return element;
		} else {
			return new Constante(valeur);
		}
	}

	
	
}
