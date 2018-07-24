package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.injection.desinjectioncomposant;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class Desinjection {
	private Map<String, List<CVariable>> conditionsADesinjecter;
	
	
	public void remplirAvecFichier(String chemin) {
		conditionsADesinjecter = new HashMap<>();
		List<String[]> ressources;
		
		try {
			ressources = FileReaderByLine.lireFichier(chemin, 3);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		
		for (String[] tableau : ressources) {
			String nomDuMonstre = tableau[0];
			int idVariable = Integer.parseInt(tableau[1]);
			int valeur = Integer.parseInt(tableau[2]);
			
			CVariable condition = new CVariable(new VBase(idVariable), Operator.IDENTIQUE, new VConstante(valeur));
			Utilitaire.mapAjouterElementAListe(conditionsADesinjecter, nomDuMonstre, condition);
		}
	}
	
	public Collection<CVariable> getConditions(String nomDuMonstre) {
		return conditionsADesinjecter.get(nomDuMonstre);
	}
	

}
