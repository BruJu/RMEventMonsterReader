package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurRetourneur;

public class EvaluateurSimple extends VisiteurRetourneur<Integer> {
	public Integer evaluer(Valeur valeur) {
		return traiter(valeur);
	}

	@Override
	protected Integer traiter(VCalcul variableCalcul) {
		Integer droite = traiter(variableCalcul.gauche);
		Integer gauche = traiter(variableCalcul.droite);
		
		if (gauche == null || droite == null) {
			return null;
		} else {
			return variableCalcul.operateur.compute(gauche, droite);
		}
	}

	@Override
	protected Integer traiter(VConstante variableConstante) {
		return variableConstante.valeur;
	}

	@Override
	protected Integer comportementParDefaut(Composant composant) {
		return null;
	}
}
