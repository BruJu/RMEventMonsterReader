package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;
import fr.bruju.rmeventreader.filereader.ActionOnLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;

public class SousObject<R, M extends Monteur<R>> implements ActionOnLine {
	private M monteur;
	private Traitement<M>[] traitements;
	private int numeroTraitement;

	public SousObject(M monteur, Traitement<M>[] traitements) {
		this.monteur = monteur;
		this.traitements = traitements;
		this.numeroTraitement = 0;
	}
	
	public Avancement traiter(String ligne) {
		if (numeroTraitement == traitements.length) {
			throw new LigneNonReconnueException("Fichier non conforme " + numeroTraitement + " " + ligne);
		}
		
		Avancement avancement = traitements[numeroTraitement].traiter(ligne);
		
		switch (avancement) {
		case Suivant:
			return avancer();
		case SuivantDirect:
		case FinTraitement:
			return avancerDirect(ligne, avancement);
		case Rester:
		case Tuer:
			return avancement;
		}

		throw new LigneNonReconnueException("Avancement non attendu " + avancement);
	}

	private Avancement avancerDirect(String ligne, Avancement avancement) {
		numeroTraitement++;
		
		return numeroTraitement == traitements.length ? avancement : traiter(ligne);
	}

	private Avancement avancer() {
		numeroTraitement++;
		
		return numeroTraitement == traitements.length ? Avancement.Suivant : Avancement.Rester;
	}

	@Override
	public void read(String line) {
		if (monteur != null && traiter(line) == Avancement.Tuer) {
			monteur = null;
		}
	}

	public R build() {
		if (monteur == null)
			return null;
		
		while (this.numeroTraitement != traitements.length) {
			if (!traitements[numeroTraitement].skippable())
				return null;
			
			numeroTraitement++;
		}

		for (Traitement<M> traitement : traitements) {
			traitement.appliquer(monteur);
		}
		
		return monteur.build();
	}

	@Override
	public String toString() {
		String s = "";
		
		for (Traitement<?> t : traitements) {
			s = s + ";" + t.toString();
		}
		
		return s;
	}
}
