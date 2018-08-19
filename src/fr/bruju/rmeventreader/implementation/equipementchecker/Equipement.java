package fr.bruju.rmeventreader.implementation.equipementchecker;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.utilitaire.Pair;

public class Equipement implements Comparable<Equipement> {
	public final int id;
	public final List<Pair<Integer, Integer>> variablesModifiees;
	public final List<Pair<Integer, Boolean>> interrupteursModifies;
	private boolean estTropComplexe = false;
	
	public Equipement(int id) {
		this.id = id;
		this.variablesModifiees = new ArrayList<>();
		this.interrupteursModifies = new ArrayList<>();
	}
	
	public void setComplexe() {
		estTropComplexe = true;
	}
	
	public String getString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append("== OBJET ").append(id).append(" ===\n");
		if (estTropComplexe) {
			sb.append("Complexe\n");
		} else {
			variablesModifiees.forEach(p -> sb.append("V[").append(p.getLeft())
					.append("] ").append(p.getRight()).append("\n"));

			interrupteursModifies.forEach(p -> sb.append("V[").append(p.getLeft())
					.append("] ").append(p.getRight() ? "On" : "Off").append("\n"));
			
		}
		
		return sb.toString();
	}

	@Override
	public int compareTo(Equipement arg0) {
		return Integer.compare(this.id, arg0.id);
	}
	
	
	
}
