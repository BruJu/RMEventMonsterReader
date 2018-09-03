package fr.bruju.rmeventreader.implementationexec.magasin;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecFlot;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecVariables;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition.CondVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurGauche;

public class RemplisseurDeNiveaux implements ExecuteurInstructions, ModuleExecVariables, ModuleExecFlot {

	Integer idEnCoursDELecture = null;
	private Map<Integer, Magasin> magasins;
	
	public RemplisseurDeNiveaux(Map<Integer, Magasin> magasins) {
		this.magasins = magasins;
	}
	
	

	@Override
	public ModuleExecVariables getExecVariables() {
		return this;
	}
	
	



	@Override
	public ModuleExecFlot getExecFlot() {
		return this;
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
