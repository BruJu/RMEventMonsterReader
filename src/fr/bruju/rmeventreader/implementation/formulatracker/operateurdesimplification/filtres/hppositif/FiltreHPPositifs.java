package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.filtres.hppositif;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.EvaluateurSimple;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.factoriseur.ConstructeurDeRepresentationVariadique;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.factoriseur.RepresentationVariadique;

/**
 * Cette classe a pour but de filtrer toutes les conditions du type HP - [VALEUR] < 1 en disant qu'elles ne sont
 * jamais vérifiées.
 * A l'inverse, les conditions du type HP - [VALEUR] > 0 sont toujours vérifiées
 * @author Bruju
 *
 */
public class FiltreHPPositifs extends ConstructeurDeComposantR {
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
				return this.getComposant(sens);
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
				return this.getComposant(sens);
			}
			
			
		}
		
		return super.traiter(cVariable);
	}

}
