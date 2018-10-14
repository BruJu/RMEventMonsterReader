package fr.bruju.rmeventreader.implementation.magasin;

import java.util.Map;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.Condition;
import fr.bruju.rmdechiffreur.modele.ValeurDroiteVariable;
import fr.bruju.rmdechiffreur.modele.ValeurGauche;
import fr.bruju.rmdechiffreur.modele.Condition.CondVariable;

public class RemplisseurDeNiveaux implements ExecuteurInstructions {

	Integer idEnCoursDELecture = null;
	private Map<Integer, Magasin> magasins;
	
	public RemplisseurDeNiveaux(Map<Integer, Magasin> magasins) {
		this.magasins = magasins;
	}


	@Override
	public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
		if (idEnCoursDELecture == null || !magasins.containsKey(idEnCoursDELecture))
			return;
		
		if (valeurGauche.appliquerG(v -> v.idVariable == 894, null, null) != Boolean.TRUE) {
			return;
		}
		
		Integer niveau = valeurDroite.appliquerDroite(v -> v.valeur, null, null);
		
		if (niveau != null) {
			magasins.get(idEnCoursDELecture).setNiveauHoldup(niveau);
		}
	}

	@Override
	public int Flot_si(Condition condition) {
		condition.appliquerVariable(this::condVariable);
		
		return 0;
	}
	
	public Void condVariable(CondVariable c) {
		if (c.variable == 1209) {
			idEnCoursDELecture = c.valeurDroite.appliquerFV(v -> v.valeur, null);
		}
		return null;
	}
	

	@Override
	public void Flot_siNon() {
		idEnCoursDELecture = null;
	}

	@Override
	public void Flot_siFin() {
		idEnCoursDELecture = null;
	}


	@Override
	public boolean getBooleenParDefaut() {
		return false;
	}
	
	
	

}
