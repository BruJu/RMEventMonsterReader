package fr.bruju.rmeventreader.implementation.recomposeur.formulededegats;

import java.util.Objects;

import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.PersonnageReel;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;

public class Header {
	public final PersonnageReel lanceur;
	public final String nomAttaque;
	public final Statistique statistiqueTouchee;

	public Header(String nomLanceur, String nomAttaque, BaseDeVariables base) {
		this.lanceur = base.getPersonnage(nomLanceur);
		this.nomAttaque = nomAttaque;
		this.statistiqueTouchee = null;
	}

	public Header(Header head, Statistique statistiqueTouchee) {
		this.lanceur = head.lanceur;
		this.nomAttaque = head.nomAttaque;
		this.statistiqueTouchee = statistiqueTouchee;
	}

	@Override
	public String toString() {
		return lanceur.getNom() + "." + nomAttaque + " • " + statistiqueTouchee.toString();
	}
	public int hashUnifiable() {
		return Objects.hash(lanceur, nomAttaque, statistiqueTouchee.possesseur);
	}

	public boolean estUnifiable(Header left) {
		return Objects.equals(lanceur, left.lanceur)
				&& Objects.equals(statistiqueTouchee.possesseur, left.statistiqueTouchee.possesseur)
				&& Objects.equals(nomAttaque, left.nomAttaque);
	}

}
