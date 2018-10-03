package fr.bruju.rmeventreader.actionmakers.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.modele.VariableHeros;

class GestionEquipe implements Remplisseur {

	private Dechiffreur d = Dechiffreur.getInstance();

	@Override
	public void remplirMap(Map<Integer, TraiteurSansRetour> handlers, Map<Integer, Traiteur> classe2) {
		handlers.put(10330, this::modifierEquipe);
		handlers.put(10410, (e, p, c) -> modifierExperience(e, p, VariableHeros.Caracteristique.EXPERIENCE));
		handlers.put(10420, (e, p, c) -> modifierExperience(e, p, VariableHeros.Caracteristique.NIVEAU));
		handlers.put(10430, this::modifierStatistiqueStable);
		handlers.put(10440, this::modifierCompetence);

		handlers.put(10450, this::modifierEquipement);
		handlers.put(10460, this::modifierHP);
		handlers.put(10470, this::modifierMP);
		handlers.put(10480, this::modifierStatut);
		handlers.put(10490, this::soignerCompletement);

	}

	private void soignerCompletement(ExecuteurInstructions executeur, int[] parametres, String s) {
		ValeurMembre cible = d.dechiffrerMembreCible(parametres[0], parametres[1]);
		executeur.Equipe_soignerCompletement(cible);
	}

	private void modifierStatut(ExecuteurInstructions executeur, int[] parametres, String s) {
		ValeurMembre cible = d.dechiffrerMembreCible(parametres[0], parametres[1]);
		boolean infliger = parametres[2] == 0;
		int numeroStatut = parametres[3];

		executeur.Equipe_modifierStatut(cible, infliger, numeroStatut);
	}

	private void modifierMP(ExecuteurInstructions executeur, int[] parametres, String s) {
		ValeurMembre cible = d.dechiffrerMembreCible(parametres[0], parametres[1]);
		boolean ajouter = parametres[2] == 0;
		FixeVariable quantite = d.dechiffrerFixeVariable(parametres[3], parametres[4]);

		executeur.Equipe_modifierStatistique(cible, VariableHeros.Caracteristique.MPACTUEL, ajouter, quantite);
	}

	private void modifierHP(ExecuteurInstructions executeur, int[] parametres, String s) {
		ValeurMembre cible = d.dechiffrerMembreCible(parametres[0], parametres[1]);
		boolean ajouter = parametres[2] == 0;
		FixeVariable quantite = d.dechiffrerFixeVariable(parametres[3], parametres[4]);
		boolean peutTuer = parametres[5] == 1;

		executeur.Equipe_modifierHP(cible, ajouter, quantite, peutTuer);
	}

	private void modifierEquipement(ExecuteurInstructions executeur, int[] parametres, String s) {
		ValeurMembre cible = d.dechiffrerMembreCible(parametres[0], parametres[1]);
		if (parametres[2] == 0) {
			FixeVariable objet = d.dechiffrerFixeVariable(parametres[3], parametres[4]);
			executeur.Equipe_equiper(cible, objet);
		} else {
			if (parametres[3] == 0) {
				executeur.Equipe_desequiper(cible);
			} else {
				executeur.Equipe_desequiper(cible, VariableHeros.Caracteristique
						.values()[VariableHeros.Caracteristique.ARME.ordinal() + parametres[3]]);
			}
		}
	}

	private void modifierCompetence(ExecuteurInstructions executeur, int[] parametres, String s) {
		ValeurMembre cible = d.dechiffrerMembreCible(parametres[0], parametres[1]);
		boolean ajouter = parametres[2] == 0;
		FixeVariable sort = d.dechiffrerFixeVariable(parametres[3], parametres[4]);

		executeur.Equipe_modifierCompetence(cible, ajouter, sort);
	}

	private void modifierStatistiqueStable(ExecuteurInstructions executeur, int[] parametres, String s) {
		boolean ajouter = parametres[2] == 0;
		ValeurMembre cible = d.dechiffrerMembreCible(parametres[0], parametres[1]);
		VariableHeros.Caracteristique stat = d.caracAugmentable(parametres[3]);
		FixeVariable quantite = d.dechiffrerFixeVariable(parametres[4], parametres[5]);

		executeur.Equipe_modifierStatistique(cible, stat, ajouter, quantite);
	}

	private void modifierExperience(ExecuteurInstructions executeur, int[] parametres,
			VariableHeros.Caracteristique stat) {
		boolean ajouter = parametres[2] == 0;
		ValeurMembre cible = d.dechiffrerMembreCible(parametres[0], parametres[1]);
		FixeVariable quantite = d.dechiffrerFixeVariable(parametres[3], parametres[4]);
		boolean verbose = parametres[5] == 1;

		executeur.Equipe_modifierExperience(cible, stat, ajouter, quantite, verbose);
	}

	private void modifierEquipe(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		boolean ajouter = parametres[0] == 0;
		FixeVariable personnage = d.dechiffrerFixeVariable(parametres[1], parametres[2]);

		executeur.Equipe_modifierEquipe(ajouter, personnage);
	}

}
