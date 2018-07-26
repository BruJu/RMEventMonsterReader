package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.filtres;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.factorisation.ConstructeurDeRepresentationVariadique;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.factorisation.RepresentationVariadique;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.EvaluateurSimple;

/**
 * Cette classe a pour but de filtrer toutes les conditions du type HP - [VALEUR] < 1 en disant qu'elles ne sont
 * jamais vérifiées.
 * A l'inverse, les conditions du type HP - [VALEUR] > 0 sont toujours vérifiées
 * @author Bruju
 *
 */
public class FiltreHPPositifs extends ConstructeurDeComposantR implements Maillon {
	
	// TODO : rework cette classe
	
	@Override
	public void traiter(Attaques attaques) {
		
		attaques.transformerFormules(formuleDeDegats -> {
			List<Condition> conditions = formuleDeDegats.conditions;
			
			for (Condition condition : conditions) {
				boolean b = traiter(condition) != null;
				
				if (!b) {
					return new FormuleDeDegats(new ArrayList<>(), new VConstante(0));
				}
			}
			
			return new FormuleDeDegats(conditions, (Valeur) traiter(formuleDeDegats.formule));
		}
		
				
				);
		
	}
	
	
	private EvaluateurSimple eval = new EvaluateurSimple();

	@Override
	protected Composant traiter(CVariable cVariable) {
		if (cVariable.operateur == Operator.IDENTIQUE || cVariable.operateur == Operator.DIFFERENT) {
			return super.traiter(cVariable);
		}
		
		Integer droite = eval.evaluer(cVariable.droite);
		
		if (droite == null) {
			return super.traiter(cVariable);
		}
		
		boolean sens = cVariable.operateur == Operator.SUP || cVariable.operateur ==  Operator.SUPEGAL;
		
		if (sens) {
			if (droite > 1) {
				return super.traiter(cVariable);
			}
		} else {
			if (droite < -1) {
				return super.traiter(cVariable);
			}
		}
		
		
		if (cVariable.gauche instanceof VStatistique) {
			VStatistique vStat = (VStatistique) cVariable.gauche;
			if (vStat.statistique.nom.equals("HP")) {
				return CFixe.get(sens);
			}
		}
		
		if (cVariable.gauche instanceof VCalcul) {
			VCalcul vCalc = (VCalcul) cVariable.gauche;
			
			ConstructeurDeRepresentationVariadique cdrv = new ConstructeurDeRepresentationVariadique();
			
			if (vCalc.operateur != Operator.PLUS && vCalc.operateur != Operator.MINUS) {
				return super.traiter(cVariable);
			}
			
			RepresentationVariadique rep = cdrv.creerRepresentationVariadique(vCalc, Operator.PLUS);
			
			if (rep.possedeHP()) {
				return CFixe.get(sens);
			}
			
			
		}
		
		return super.traiter(cVariable);
	}

}
