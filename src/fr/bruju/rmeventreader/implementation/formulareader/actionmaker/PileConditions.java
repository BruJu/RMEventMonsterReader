package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;

public class PileConditions {
	public static class Element {
		public Condition condition;
		public BranchesAExplorer branchesAExplorer;
		public BranchesAExplorer brancheCourante;
		
		public Element(Condition condition, BranchesAExplorer branchesAExplorer) {
			this.condition = condition;
			this.branchesAExplorer = branchesAExplorer;
			this.brancheCourante = BranchesAExplorer.GAUCHE;
		}
	}
	
	public static enum BranchesAExplorer {
		GAUCHE, DROITE, TOUTES
	}
	
	List<Element> elementsEmpiles = new ArrayList<>();
	
	
	public void empiler(Condition condition, BranchesAExplorer branchesAExplorer) {
		elementsEmpiles.add(new Element(condition, branchesAExplorer));
	}
	
	public void empiler(Condition condition, boolean estVraiDeBase) {
		if (estVraiDeBase) {
			empiler(condition, BranchesAExplorer.GAUCHE);
		} else {
			empiler(condition, BranchesAExplorer.DROITE);
		}
	}
	
	public void depiler() {
		elementsEmpiles.remove(dernierElementPosition());
	}

	private int dernierElementPosition() {
		return elementsEmpiles.size() - 1;
	}
	
	public boolean possedeUnFaux() {
		if (elementsEmpiles.isEmpty())
			return false;
		
		Element element = elementsEmpiles.get(dernierElementPosition());
		
		if (element.branchesAExplorer == BranchesAExplorer.TOUTES) {
			return false;
		}
		
		return element.brancheCourante != element.branchesAExplorer;
	}
	
	public void passerADroite() {
		elementsEmpiles.get(dernierElementPosition()).brancheCourante = BranchesAExplorer.DROITE;
	}

	public boolean sommetExplore() {
		return elementsEmpiles.get(dernierElementPosition()).branchesAExplorer == BranchesAExplorer.TOUTES;
	}
}
