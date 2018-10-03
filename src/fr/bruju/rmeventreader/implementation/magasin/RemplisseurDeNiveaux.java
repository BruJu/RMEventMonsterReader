package fr.bruju.rmeventreader.implementation.magasin;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.modele.Condition.CondVariable;

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
	public boolean Flot_si(Condition condition) {
		condition.appliquerVariable(this::condVariable);
		
		return true;
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
	
	
	

}
