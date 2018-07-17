package fr.bruju.rmeventreader.implementation.formulareader.concept;

import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;

public class MetaStatPerso implements Concept {
	private Personnage personnage;
	private MetaStat metaStat;

	public MetaStatPerso(Personnage personnage, MetaStat metaStat) {
		this.personnage = personnage;
		this.metaStat = metaStat;
	}

	@Override
	public String getNom() {
		return personnage.getNom() + "." + metaStat.toString();
	}

	@Override
	public boolean estSimilaire(Concept autre) {
		if (!(autre instanceof MetaStatPerso))
			return false;

		MetaStatPerso autrePerso = (MetaStatPerso) autre;

		return this.metaStat == autrePerso.metaStat;
	}

	@Override
	public boolean estIdentique(Concept autre) {
		if (!(autre instanceof MetaStatPerso))
			return false;

		MetaStatPerso autrePerso = (MetaStatPerso) autre;

		return this.personnage.equals(autrePerso.personnage) && this.metaStat == autrePerso.metaStat;
	}

}
