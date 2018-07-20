package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.ConditionFixe;

/**
 * Pile de conditions
 * @author Bruju
 *
 */
public class PileConditions {
	/**
	 * Element dans la pile, constitué de la condition, des branches à explorer et de la branche en cours d'exporation
	 * dans l'arbre. 
	 * @author Bruju
	 *
	 */
	private static class Element {
		/**
		 * Condition vraie si on est à gauche
		 */
		public final Condition condition;
		/**
		 * Branches à explorer
		 */
		public final BranchesAExplorer branchesAExplorer;
		/**
		 * Branche actuelle
		 */
		public BranchesAExplorer brancheCourante;
		
		/**
		 * Crée un nouvel élément empilable pour se repérer dans l'arbre
		 * @param condition La condition
		 * @param branchesAExplorer La liste des branches à explorer pour cette condition
		 */
		public Element(Condition condition, BranchesAExplorer branchesAExplorer) {
			this.condition = condition;
			this.branchesAExplorer = branchesAExplorer;
			this.brancheCourante = BranchesAExplorer.GAUCHE;
		}
		
		/**
		 * Renvoie la condition actuellement explorée telle que la condition est respectée
		 * @return
		 */
		public Condition getCondition() {
			return brancheCourante == BranchesAExplorer.GAUCHE ? condition : condition.revert();
		}
	}
	
	/**
	 * Branches pouvant être explorées
	 * @author Bruju
	 *
	 */
	public static enum BranchesAExplorer {
		GAUCHE, DROITE, TOUTES
	}
	
	
	/**
	 * Liste des éléments dans la pile
	 */
	private List<Element> elementsEmpiles = new ArrayList<>(10);
	
	
	/* =========================
	 * FONCTIONNEMENT D'UNE PILE
	 * ========================= */
	
	// EMPILER
	
	/**
	 * Empile une condition dont une seule branche doit être explorée
	 */
	public void empiler(Condition condition, boolean estVraiDeBase) {
		if (estVraiDeBase) {
			empiler(condition, BranchesAExplorer.GAUCHE);
		} else {
			empiler(condition, BranchesAExplorer.DROITE);
		}
	}
	
	/**
	 * Empile une condition dont les deux branches doivent être explorées
	 */
	public void empiler(Condition condition) {
		empiler(condition, BranchesAExplorer.TOUTES);
	}
	
	/**
	 * Empile la condition
	 */
	private void empiler(Condition condition, BranchesAExplorer branchesAExplorer) {
		elementsEmpiles.add(new Element(condition, branchesAExplorer));
	}
	
	// DEPILER
	
	public void depiler() {
		elementsEmpiles.remove(dernierElementPosition());
	}
	
	// DERNIER ELEMENT DE LA PILE

	private int dernierElementPosition() {
		return elementsEmpiles.size() - 1;
	}
	
	/* ==========================
	 * INTERACTION AVEC LE SOMMET
	 * ========================== */
	
	/**
	 * Renvoi vrai si le sommet de pile est une condition à ne pas explorer
	 */
	public boolean possedeUnFaux() {
		if (elementsEmpiles.isEmpty())
			return false;
		
		Element element = elementsEmpiles.get(dernierElementPosition());
		
		if (element.branchesAExplorer == BranchesAExplorer.TOUTES) {
			return false;
		}
		
		return element.brancheCourante != element.branchesAExplorer;
	}
	
	/**
	 * Change la branche en cours d'exploration
	 */
	public void passerADroite() {
		elementsEmpiles.get(dernierElementPosition()).brancheCourante = BranchesAExplorer.DROITE;
	}

	/**
	 * Renvoie vrai si la condition au sommet doit être explorée des deux côtés
	 */
	public boolean sommetExplore() {
		return elementsEmpiles.get(dernierElementPosition()).branchesAExplorer == BranchesAExplorer.TOUTES;
	}
	
	/* =========================
	 * FONCTIONNEMENT D'UNE PILE
	 * ========================= */

	public List<Condition> getIntegration() {
		return elementsEmpiles.stream()
				              .map(element -> element.getCondition())
				              .filter(element -> (element == ConditionFixe.VRAI) ? false : true)
				              .collect(Collectors.toList());
	}
}
