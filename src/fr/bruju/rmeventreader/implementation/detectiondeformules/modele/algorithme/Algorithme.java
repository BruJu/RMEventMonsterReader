package fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

/**
 * Un algorithme est une suite d'instructions. Cet objet est au coeur de la détection de formules. On considère que les
 * algorithmes dignes d'intérêt sont ceux qui changent l'état des variables.
 */
public class Algorithme {
	/** Liste des instructions */
	private final List<InstructionGenerale> instructions = new ArrayList<>();

	/* ============
	 * CONSULTATION
	 * ============ */

	/**
	 * Donne le nombre d'instructions contenues
	 * @return Le nombre d'instructions
	 */
	public int nombreDInstructions() {
		return instructions.size();
	}

	/**
	 * Renvoie vrai si l'algorithme est vide. On défini un algorithme comme étant vide si il ne possède aucune
	 * instruction d'affectation (toutes les instructions sont soit des conditions soit des commentaires)
	 * @return Vrai si l'algorithme est vide. Faux si il a au moins une affectation.
	 */
	public boolean estVide() {
		for (InstructionGenerale instruction : instructions) {
			if (!instruction.estVide()) {
				return false;
			}
		}

		return true;
	}

	/**
	 * Compare les deux algorithmes pour voir si ils sont identiques en ignorant les affichages
	 * @param algorithme L'autre algorithme
	 * @return Vrai si cet algorithme et celui donné en paramètre sont identiques, modulo les affichages
	 */
	public boolean estIdentique(Algorithme algorithme) {
		Supplier<InstructionGenerale> itThis = new IterateurEffectif();
		Supplier<InstructionGenerale> itAutre = algorithme.new IterateurEffectif();
		return Utilitaire.comparerIterateursBoolean(itThis, itAutre, InstructionGenerale::estIdentique);
	}


	/* =======================
	 * MODIFICATION / VISITEUR
	 * ======================= */

	// Modification

	/**
	 * Ajoute une nouvelle instruction à l'algorithme
	 * @param instruction L'instruction à ajouter
	 */
	public void ajouterInstruction(InstructionGenerale instruction) {
		instructions.add(instruction);
	}

	// Affichage

	/**
	 * Donne une représentation textuelle de l'algorithme. Chaque instruction s'affiche sur une ligne et les conditions
	 * sont indentées.
	 * @return Une représentation textuelle de l'algorithme
	 */
	public String getString() {
		ListeurDInstructions listeurDInstructions = new ListeurDInstructions();
		lister(listeurDInstructions);
		return listeurDInstructions.toString();
	}

	/**
	 * Ajoute l'algorithme à la liste des instructions contenues dans le listeur
	 * @param listeurDInstructions Le listeur d'instructions
	 */
	public void lister(ListeurDInstructions listeurDInstructions) {
		for (InstructionGenerale instruction : instructions) {
			instruction.listerTextuellement(listeurDInstructions);
		}
	}

	// Visiteur

	/**
	 * Visite chaque instructions de l'algorithme
	 * @param visiteurDAlgorithme Le visiteur
	 */
	public void accept(VisiteurDAlgorithme visiteurDAlgorithme) {
		for (InstructionGenerale instruction : instructions) {
			instruction.accept(visiteurDAlgorithme);
		}
	}

	/**
	 * Visite chaque instructions de l'algorithme dans le sens inverse
	 * @param visiteurDAlgorithme Le visiteur
	 */
	public void acceptInverse(VisiteurDAlgorithme visiteurDAlgorithme) {
		for (int i = instructions.size() - 1 ; i >= 0 ; i--) {
			instructions.get(i).accept(visiteurDAlgorithme);
		}
	}


	/* ========================================
	 * CONSULTATION DES INSTRUCTIONS EFFECTIVES
	 * ======================================== */

	/**
	 * Crée un producteur d'instructions effectives qui va renvoyer à chaque appel la prochaine instructions effective.
	 * Une instruction effective est ici définie comme étant une instruction faisant soit une affectation, soit étant
	 * une condition.
	 * @return Le producteur d'instructions effectives
	 */
	public Supplier<InstructionGenerale> getListeurDInstructionsEffectives() {
		return new IterateurEffectif();
	}

	private class IterateurEffectif implements Supplier<InstructionGenerale> {
		private int i = -1;

		public InstructionGenerale get() {
			do {
				i++;
			} while(estUnAffichage());

			return extraire();
		}

		private boolean estUnAffichage() {
			return i < instructions.size() && instructions.get(i) instanceof InstructionAffichage;
		}

		private InstructionGenerale extraire() {
			return i >= instructions.size() ? null : instructions.get(i);
		}
	}
}
