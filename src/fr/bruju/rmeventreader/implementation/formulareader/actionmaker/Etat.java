package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurNumerique;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurStatistique;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurVariable;
import fr.bruju.rmeventreader.implementation.formulareader.model.CreateurPersonnage;

public class Etat {
	public static List<Integer> idVariablesCrees = new ArrayList<>();
	
	private Map<Integer, Valeur> etatMemoire;
	
	public Etat() {
		etatMemoire = new HashMap<>();
		
		CreateurPersonnage.getMap().forEach((k, v) -> etatMemoire.put(k, new ValeurStatistique(v.getLeft(), v.getRight())));
	}
	
	
	
	public void enregistrerValeurDepart(int idVariable, int valeurInitiale) {
		etatMemoire.put(idVariable, new ValeurNumerique(valeurInitiale));
	}
	
	
	public Valeur getSortie(int[] idVariables) {
		Valeur val;
		
		for (int idVariable : idVariables) {
			val = etatMemoire.get(idVariable);
			
			if (val != null)
				return val;			
		}
		
		
		return null;
	}
	
	public Valeur getValeur(int idVariable) {
		Valeur retour = etatMemoire.get(idVariable);
		
		if (retour == null) {
			retour = new ValeurVariable(idVariable);
			
			etatMemoire.put(idVariable, retour);
		}
		
		return retour;
	}

	public void setValue(int idVariable, Valeur nouvelleValeur) {
		etatMemoire.put(idVariable, nouvelleValeur);
	}


	public static void updateStatic(int idVariable) {
		if (!idVariablesCrees.contains(idVariable)) {
			idVariablesCrees.add(idVariable);
		}
	}
	
	
}
