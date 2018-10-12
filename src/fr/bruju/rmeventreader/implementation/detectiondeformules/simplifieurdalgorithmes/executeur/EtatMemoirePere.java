package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules._variables.EtatInitial;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.CaseMemoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;

public class EtatMemoirePere extends EtatMemoire {
	private EtatInitial instanciations = EtatInitial.getEtatInitial();
	private Map<Integer, CaseMemoire> casesMemoire = new HashMap<>();
	
	@Override
	protected VariableInstanciee getValeurManquante(int numeroDeCase) {
		if (!casesMemoire.containsKey(numeroDeCase)) {
			casesMemoire.put(numeroDeCase, new CaseMemoire(numeroDeCase, instanciations.getValeur(numeroDeCase)));
		}
		
		return casesMemoire.get(numeroDeCase).premiereInstance();
	}
}
