package fr.bruju.rmeventreader.implementation.recomposeur.formulededegats;

import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.PersonnageReel;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;

public class Header implements Comparable<Header> {
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
		return lanceur.getNom() + "." + nomAttaque;
	}

	@Override
	public int compareTo(Header arg0) {
		return 0;
	}
	
	
}
