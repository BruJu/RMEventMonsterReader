package fr.bruju.rmeventreader.implementationexec.magasin;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecFlot;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecVariables;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition.CondVariable;

public class RemplisseurDObjets implements ExecuteurInstructions, ModuleExecVariables, ModuleExecFlot {

	Integer idEnCoursDELecture = null;
	private Map<Integer, Magasin> magasins;
	
	public RemplisseurDObjets(Map<Integer, Magasin> magasins) {
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
		
		if (valeurGauche.appliquerG(this::idVariableEstValide, null, null) != Boolean.TRUE) {
			return;
		}
		
		Integer idObjet = valeurDroite.appliquerDroite(v -> v.valeur, null, null);
				
		if (idObjet != null)
			magasins.get(idEnCoursDELecture).ajouterObjet(new Objet(idObjet));
	}

	private Boolean idVariableEstValide(Variable variable) {
		return (variable.idVariable >= 1241 && variable.idVariable <= 1262)
				|| (variable.idVariable >= 1831 && variable.idVariable <= 1838);
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
