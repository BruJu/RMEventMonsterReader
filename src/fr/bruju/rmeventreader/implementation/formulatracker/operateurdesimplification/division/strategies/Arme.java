package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.Extracteur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.StrategieDeDivision;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireArmeStrict;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;

/**
 * Stratégie de division se reposant sur le fait qu'un personnage possède une arme ou non
 * 
 * @author Bruju
 *
 */
public class Arme implements StrategieDeDivision {
	/** Numéro du personnage possédant l'arme */
	private int numeroPersonnage;
	
	/**
	 * Crée une stratégie de division par rapport à l'arme que le personnage donné a équipé
	 * @param numeroPersonnage Le personnage
	 */
	public Arme(int numeroPersonnage) {
		this.numeroPersonnage = numeroPersonnage;
	}

	@Override
	public List<GestionnaireDeCondition> getGestionnaires(Condition condition, Set<Condition> conditions) {
		List<GestionnaireDeCondition> gestionnaires = new ArrayList<>();
		
		gestionnaires.add(new GestionnaireArmeStrict((CArme) condition));
		
		return gestionnaires;
	}

	@Override
	public Extracteur getExtracteur() {
		return new ExtracteurD();
	}
	
	/**
	 * Extracteur de conditions portant sur des armes
	 */
	public class ExtracteurD extends Extracteur {
		@Override
		public void visit(CArme composant) {
			if (composant.heros == numeroPersonnage) {
				conditions.add(composant);
				conditions.add(new CArme(numeroPersonnage, 0));
			}
		}
	}
}
