package fr.bruju.rmeventreader.implementationexec.magasin;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition.CondVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurFixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurGauche;

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
		
		
		if (valeurGauche.accept(new VisiteurValeurGauche<Boolean>() {
			@Override
			public Boolean visit(Variable variable) {
				return variable.idVariable == 894;
			}
		}) != Boolean.TRUE) {
			return;
		}
		
		
		Integer niveau = valeurDroite.accept(new VisiteurValeurDroiteVariable<Integer>() {
			@Override
			public Integer visit(ValeurFixe valeur) {
				return valeur.valeur;
			}
		});
				
		if (niveau != null)
			magasins.get(idEnCoursDELecture).setNiveauHoldup(niveau);
	}

	@Override
	public boolean Flot_si(Condition condition) {
		condition.accept(new Condition.Visiteur<Void>() {
			@Override
			public Void visit(CondVariable condition) {
				if (condition.variable == 1209) {
					idEnCoursDELecture = null;
					condition.valeurDroite.accept(new VisiteurFixeVariable<Void>() {
						@Override
						public Void visit(ValeurFixe valeur) {
							idEnCoursDELecture = valeur.valeur;
							return null;
						}
					});
				}
				return null;
			}
		});
		
		return true;
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
