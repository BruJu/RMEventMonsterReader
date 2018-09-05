package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.HashSet;
import java.util.Set;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecVariables;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurGauche;

public class BaseDeRechercheVarAAOn implements BaseDeRecherche {

	private Set<Reference> referencesConnues = new HashSet<>();
	private int idSwitch;

	public BaseDeRechercheVarAAOn(int idSwitch) {
		this.idSwitch = idSwitch;
	}

	@Override
	public void afficher() {
		referencesConnues.forEach(reference -> System.out.println(reference.getString()));
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new ChercheurDeOn(ref);
	}

	
	public class ChercheurDeOn implements ExecuteurInstructions, ModuleExecVariables {
		private Reference ref;

		public ChercheurDeOn(Reference ref) {
			this.ref = ref;
		}
		
		
		@Override
		public ModuleExecVariables getExecVariables() {
			return this;
		}

		@Override
		public void Variables_changerSwitch(ValeurGauche valeurGauche, Boolean nouvelleValeur) {
			valeurGauche.appliquerG(v -> {
				if (v.idVariable == idSwitch && nouvelleValeur == Boolean.TRUE) {
					referencesConnues.add(ref);
				}
				return null;
			}, null, null);
		}
	}

}
