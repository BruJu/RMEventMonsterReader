package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;

import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.Personnage;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.Statistique;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.VisiteurDeComposants;
import java.util.Objects;

public class VStatistique implements Valeur {
	public final Personnage possesseur;
	public final String statistique;
	public final int idVariable;
	
	public VStatistique(Personnage personnage, String nomStat, int idVar) {
		this.possesseur = personnage;
		this.statistique = nomStat;
		this.idVariable = idVar;
	}

	public VStatistique(Statistique stat) {
		this(stat.getPossesseur(), stat.getNom(), stat.getPosition());
	}

	@Override
	public String getString() {
		return possesseur.getNom() + "." + statistique;
	}


	@Override
	public void accept(VisiteurDeComposants visiteurDeComposant) {
		visiteurDeComposant.visit(this);
	}

	@Override
	public boolean equals(final Object other) {
		if (!(other instanceof VStatistique)) {
			return false;
		}
		VStatistique castOther = (VStatistique) other;
		return Objects.equals(idVariable, castOther.idVariable);
	}

	@Override
	public int hashCode() {
		return Objects.hash(idVariable) * 10133;
	}
	
	
}
