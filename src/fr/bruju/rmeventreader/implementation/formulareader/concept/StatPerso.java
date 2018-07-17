package fr.bruju.rmeventreader.implementation.formulareader.concept;

import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.Statistique;

public class StatPerso implements Concept {

	private Statistique statistique;
	private Personnage personnage;

	public StatPerso(Personnage personnage, Statistique statistique) {
		this.personnage = personnage;
		this.statistique = statistique;
	}

	@Override
	public String getNom() {
		return personnage.getNom() + "." + statistique.toString();
	}

	@Override
	public boolean estSimilaire(Concept autre) {
		if (!(autre instanceof StatPerso))
			return false;

		StatPerso autrePerso = (StatPerso) autre;

		return this.statistique == autrePerso.statistique;
	}

	@Override
	public boolean estIdentique(Concept autre) {
		if (!(autre instanceof StatPerso))
			return false;

		StatPerso autrePerso = (StatPerso) autre;

		return this.personnage.equals(autrePerso.personnage) && this.statistique == autrePerso.statistique;
	}

}
