package fr.bruju.rmeventreader.rmdatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AffectationFlexible implements Affectation {
	private Map<Integer, Integer> quantiteDobjets = new HashMap<>();
	private Map<Integer, Integer> variables = new HashMap<>();
	private Map<Integer, Boolean> interrupteurs = new HashMap<>();
	private Map<Integer, List<Integer>> objetsPossedes = new HashMap<>();
	
	
	public void putObjet(Integer idObjet, Integer quantite) {
		quantiteDobjets.put(idObjet, quantite);
	}

	public void putVariable(Integer idVariable, Integer valeur) {
		variables.put(idVariable, valeur);
	}
	
	public void putInterrupteur(Integer idSwitch, Boolean etat) {
		interrupteurs.put(idSwitch, etat);
	}
	
	public void putObjetEquipe(Integer idHeros, Integer idObjet) {		
		List<Integer> equipes = objetsPossedes.get(idHeros);

		if (equipes == null) {
			equipes = new ArrayList<>();
			objetsPossedes.put(idObjet, equipes);
		}

		equipes.add(idObjet);
	}
	
	
	@Override
	public Integer getQuantitePossedee(int idObjet) {
		return quantiteDobjets.get(idObjet);
	}

	@Override
	public Integer getVariable(int idVariable) {
		return variables.get(idVariable);
	}

	@Override
	public Boolean getInterrupteur(int idInterrupteur) {
		return interrupteurs.get(idInterrupteur);
	}
	
	@Override
	public Boolean herosPossedeEquipement(int idHeros, int idObjet) {
		List<Integer> objetsEquipesParLeHeros = objetsPossedes.get(idHeros);

		if (objetsEquipesParLeHeros == null) {
			return false;
		}

		return objetsEquipesParLeHeros.contains(idObjet);
	}

}
