package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

import fr.bruju.rmeventreader.dictionnaires.header.Instruction;

public class Passe implements Traitement {
	@Override
	public Avancement traiter(String ligne) {
		return Avancement.Suivant;
	}

	@Override
	public Instruction resultat() {
		return null;
	}
}
