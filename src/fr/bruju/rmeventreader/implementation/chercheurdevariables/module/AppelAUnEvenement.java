package fr.bruju.rmeventreader.implementation.chercheurdevariables.module;

import java.util.TreeSet;
import java.util.Set;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.reference.Reference;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.BaseDeRecherche;


public class AppelAUnEvenement implements BaseDeRecherche {
	
	private Set<Reference> referencesConnues = new TreeSet<>();
	
	private final int numeroEvenement;

	
	public AppelAUnEvenement(int numeroEvenement) {
		this.numeroEvenement = numeroEvenement;
	}

	@Override
	public void afficher() {
		referencesConnues.forEach(reference -> System.out.println(reference.getString()));
	}

	@Override
	public ExecuteurInstructions getExecuteur(Reference ref) {
		return new Chercheur(ref);
	}

	
	public class Chercheur implements ExecuteurInstructions {
		private Reference reference;

		public Chercheur(Reference reference) {
			this.reference = reference;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		@Override
		public void Flot_appelEvenementCommun(int numero) {
			if (numero == numeroEvenement) {
				referencesConnues.add(reference);
			}
		}
	}
}
