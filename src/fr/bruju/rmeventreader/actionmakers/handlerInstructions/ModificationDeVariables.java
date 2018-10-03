package fr.bruju.rmeventreader.actionmakers.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.modele.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurGauche;

class ModificationDeVariables implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();

	@Override
	public void remplirMap(Map<Integer, TraiteurSansRetour> handlers, Map<Integer, Traiteur> classe2) {
		// Modification d'interrupteurs
		handlers.put(10210, this::changerSwitch);
		// Modification de variable
		handlers.put(10220, this::changerVariable);
		// Chrono
		handlers.put(10230, this::modifierChrono);
		// Modifier argent
		handlers.put(10310, this::modifierArgent);
		// Modifier objets
		handlers.put(10320, this::modifierObjets);
	}
	
	private void modifierObjets(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		boolean ajouter = parametres[0] == 0;
		FixeVariable objet = d.dechiffrerFixeVariable(parametres[1], parametres[2]);
		FixeVariable quantite = d.dechiffrerFixeVariable(parametres[3], parametres[4]);
		
		executeur.Variables_modifierObjets(ajouter, objet, quantite);
	}
	
	private void modifierArgent(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		FixeVariable quantite = d.dechiffrerFixeVariable(parametres[1], parametres[2]);
		boolean ajouter = parametres[0] == 0;
		
		executeur.Variables_modifierArgent(ajouter, quantite);
	}
	
	private void modifierChrono(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		int numeroChrono = parametres[5] + 1;
		
		switch (parametres[0]) {
		case 0:
			executeur.Chrono_modifier(numeroChrono, d.dechiffrerFixeVariable(parametres[1], parametres[2]));
			return;
		case 1:
			boolean afficherChrono = parametres[3] == 1;
			boolean continuerPendantCombat = parametres[4] == 1;
			executeur.Chrono_lancer(numeroChrono, afficherChrono, continuerPendantCombat);
			return;
		case 2:
			executeur.Chrono_arreter(numeroChrono);
			return;
		default:
			throw new ArgumentInconnuException("ModifierChrono Type " + parametres[0]);
		}
	}
	
	private void changerSwitch(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		try {
			ValeurGauche valeurGauche = d.dechiffrerValeurGauche (parametres[0], parametres[1], parametres[2]);
			Boolean nouvelleValeur = d.dechiffrerBooleen(parametres[3]);
			executeur.Variables_changerSwitch(valeurGauche, nouvelleValeur);
		} catch (ArgumentInconnuException e) {
			// Aë : instruction sans effet et connue 
			if (parametres[0] == 29968776) {
				return;
			}
			
			System.out.print("ChangeVariable - ");
			d.afficher(parametres, new int[0], chaine);
		}
	}
	
	private void changerVariable(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		ValeurGauche valeurGauche = d.dechiffrerValeurGauche(parametres[0], parametres[1], parametres[2]);
		OpMathematique operateur = d.extraireOperateurMaths(parametres[3]);
		ValeurDroiteVariable valeurDroite = d.dechiffrerValeurDroiteVariable(parametres[4], parametres[5], parametres[6]);
		
		if (operateur == null) {
			executeur.Variables_affecterVariable(valeurGauche, valeurDroite);
		} else {
			executeur.Variables_changerVariable(valeurGauche, operateur, valeurDroite);
		}
	}

}
