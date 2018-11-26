package fr.bruju.rmeventreader.implementation.detectiondeformules.transformations;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.*;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.*;
import fr.bruju.rmeventreader.implementation.detectiondeformules.nouvellestransformations.AjouteurDeTag;
import fr.bruju.util.table.Enregistrement;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Une classe ajoutant un champ "Sorties" à une table contenant une ArrayList de Statistique.
 * <br>Cette liste contient la liste de toutes les Statistique affectées par une instruction d'affectation dans
 * l'algorithme.
 */
public class ListeurDeStatistiquesModifiees extends AjouteurDeTag {
	public ListeurDeStatistiquesModifiees() {
		super("Sorties");
	}

	@Override
	protected Object genererNouveauChamp(Enregistrement enregistrement) {
		Visiteur listeur = new Visiteur();
		listeur.visit(enregistrement.<Algorithme>get("Algorithme"));
		return new ArrayList<>(listeur.variablesDeSortie);
	}

	/**
	 * Le visiteur qui va parcourir l'algorithme et noter toutes les modifications de statistiques
	 */
	private static class Visiteur implements VisiteurDAlgorithme {
		private Set<Statistique> variablesDeSortie = new HashSet<>();

		@Override
		public void visit(BlocConditionnel blocConditionnel) {
			blocConditionnel.siVrai.accept(this);
			blocConditionnel.siFaux.accept(this);
		}

		@Override
		public void visit(InstructionAffectation instructionAffectation) {
			ExprVariable variable = instructionAffectation.variableAssignee;

			if (variable instanceof Statistique) {
				variablesDeSortie.add((Statistique) variable);
			}
		}

		@Override
		public void visit(InstructionAffichage instructionAffichage) {
		}
	}
}
