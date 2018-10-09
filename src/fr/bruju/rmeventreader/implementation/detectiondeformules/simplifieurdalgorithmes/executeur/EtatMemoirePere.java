package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.CaseMemoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;

public class EtatMemoirePere extends EtatMemoire {

	private Map<Integer, CaseMemoire> casesMemoire = new HashMap<>();
	
	@Override
	protected VariableInstanciee getValeurManquante(int numeroDeCase) {
		casesMemoire.computeIfAbsent(numeroDeCase, CaseMemoire::new);
		
		return casesMemoire.get(numeroDeCase).premiereInstance();
	}
	

}
