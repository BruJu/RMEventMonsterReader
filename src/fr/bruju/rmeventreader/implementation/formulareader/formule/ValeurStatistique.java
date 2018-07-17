package fr.bruju.rmeventreader.implementation.formulareader.formule;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.Statistique;

public class ValeurStatistique implements Valeur {
	private Statistique statistique;
	private Personnage personnage;

	ValeurStatistique(Personnage personnage, Statistique statistique) {
		this.personnage = personnage;
		this.statistique = statistique;
	}

	@Override
	public String getString() {
		return personnage.getNom() + "." + statistique;
	}
	

	
	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public int evaluer() throws DependantDeStatistiquesEvaluation {
		throw new DependantDeStatistiquesEvaluation();
	}

	@Override
	public boolean estGarantiePositive() {
		return true;
	}
	
	
	@Override
	public boolean concerneLesMP() {
		return statistique == Statistique.MP;
	}
	
	@Override
	public List<Valeur> splash() {
		List<Valeur> list = new ArrayList<>();
		list.add(this);
		return list;
	}
}
