package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

import fr.bruju.rmeventreader.implementation.formulareader.formule.DependantDeStatistiquesEvaluation;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NonEvaluableException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.model.Personnage;
import fr.bruju.rmeventreader.implementation.formulareader.model.PersonnageUnifie;
import fr.bruju.rmeventreader.implementation.formulareader.model.Statistique;

public class ValeurStatistique implements ValeurSimple {
	private Statistique statistique;
	private Personnage personnage;

	public ValeurStatistique(Personnage personnage, Statistique statistique) {
		this.personnage = personnage;
		this.statistique = statistique;
	}

	@Override
	public String getString() {
		return personnage.getNom() + "." + statistique;
	}
	


	@Override
	public boolean estGarantiePositive() {
		return true;
	}
	

	@Override
	public int[] evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		throw new DependantDeStatistiquesEvaluation();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((personnage == null) ? 0 : personnage.hashCode());
		result = prime * result + ((statistique == null) ? 0 : statistique.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ValeurStatistique other = (ValeurStatistique) obj;
		if (personnage == null) {
			if (other.personnage != null)
				return false;
		} else if (!personnage.equals(other.personnage))
			return false;
		if (statistique != other.statistique)
			return false;
		return true;
	}

	@Override
	public boolean estSimilaire(Valeur valeurAutre) {
		if (!(valeurAutre instanceof ValeurStatistique)) {
			return false;
		}
		
		return statistique == ((ValeurStatistique) valeurAutre).statistique;
	}

	@Override
	public Valeur similariser(Valeur valeurAutre) {
		ValeurStatistique autre = (ValeurStatistique) valeurAutre;
		Personnage personnage = PersonnageUnifie.getInstance().get(this.personnage, autre.personnage);
		return new ValeurStatistique(personnage, statistique);
	}
	

}
