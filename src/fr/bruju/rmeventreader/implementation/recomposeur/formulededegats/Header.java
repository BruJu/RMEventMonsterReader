package fr.bruju.rmeventreader.implementation.recomposeur.formulededegats;

import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.BaseDeVariables;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.PersonnageReel;

public class Header {
	public final PersonnageReel lanceur;
	public final String nomAttaque;

	public Header(String nomLanceur, String nomAttaque, BaseDeVariables base) {
		this.lanceur = base.getPersonnage(nomLanceur);
		this.nomAttaque = nomAttaque;
	}

	@Override
	public String toString() {
		return lanceur.getNom() + "." + nomAttaque;
	}
	
	
}
