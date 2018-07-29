package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;

public class ExtracteurDeConditions implements VisiteurDeComposants {
	private List<VBase> variableTrackees;
	
	private Set<Condition> conditionsConnues;
	
	
	public List<Condition> extraireSousConditions(FormuleDeDegats formuleDeDegats, List<Composant> composants) {
		conditionsConnues = new HashSet<>();
		remplirTracking(composants);
		
		formuleDeDegats.conditions.forEach(this::visit);
		visit(formuleDeDegats.formule);
		
		return new ArrayList<>(conditionsConnues);
	}

	private void remplirTracking(List<Composant> composants) {
		variableTrackees = composants.stream()
									.filter(v -> v instanceof VBase)
									.map(v -> (VBase) v)
									.collect(Collectors.toList());
	}

	@Override
	public void visit(CArme composant) {
	}

	@Override
	public void visit(CSwitch composant) {
	}

	@Override
	public void visit(CVariable composant) {
		if (variableTrackees.contains(composant.gauche)) {
			conditionsConnues.add(composant);
		} else if (variableTrackees.contains(composant.droite)) {
			conditionsConnues.add(composant);
		}
		
		
	}

	/* ===============
	 * VISITE DES FILS
	 * =============== */

	@Override
	public void visit(BTernaire composant) {
		visit(composant.condition);
		visit(composant.siVrai);
		visit(composant.siFaux);
	}

	@Override
	public void visit(VTernaire composant) {
		visit(composant.condition);
		visit(composant.siVrai);
		visit(composant.siFaux);
	}

	@Override
	public void visit(VCalcul composant) {
		visit(composant.gauche);
		visit(composant.droite);
	}
	
	/* =============
	 * AUCUNE ACTION
	 * ============= */

	@Override
	public void visit(BBase composant) {
	}

	@Override
	public void visit(BConstant composant) {
	}

	@Override
	public void visit(BStatistique composant) {
	}
	
	@Override
	public void visit(VAleatoire composant) {
	}

	@Override
	public void visit(VBase composant) {
	}

	@Override
	public void visit(VConstante composant) {
	}

	@Override
	public void visit(VStatistique composant) {
	}

}
