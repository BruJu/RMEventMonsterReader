package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;


public class EtatMemoirePere extends EtatMemoire {
	//private EtatInitial instanciations = EtatInitial.getEtatInitial();
	private Map<Integer, ExprVariable> casesMemoire = new HashMap<>();
	
	@Override
	protected ExprVariable getValeurManquante(int numeroDeCase) {
		if (!casesMemoire.containsKey(numeroDeCase)) {
			casesMemoire.put(numeroDeCase, new ExprVariable(numeroDeCase));
		}
		
		return casesMemoire.get(numeroDeCase);
	}
}
