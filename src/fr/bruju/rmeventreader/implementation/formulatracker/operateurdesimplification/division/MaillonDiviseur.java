package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies.Arme;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies.DisjonctionInterrupteurs;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies.Interrupteur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies.Propriete;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies.Variable1Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies.VariableDecouverte;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.strategies.VariableEnsemble;

/**
 * Maillon divisant les données pour faciliter la compréhension de l'affichage.
 * <p>
 * Ce maillon se repose exclusivement sur des connaissances métier implémentées en dur.
 * 
 * @author Bruju
 *
 */
public class MaillonDiviseur implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		// Variable 360 (choix d'une sous attaque)
		attaques.appliquerDiviseur("Sous-Attaque",
			new Diviseur[] {
				new Diviseur(new VariableEnsemble(360, new int[] {68, 69}))
		});
		
		// Monstres volants
		attaques.appliquerDiviseur("Volant",
			new Diviseur[] {
				new Diviseur(new Propriete("Volant")),
				new Diviseur(new Variable1Valeur(552, 83))
		});
		
		// Armes portées
		attaques.appliquerDiviseur("Arme",
			new Diviseur[] {
				new Diviseur(new Arme(1)),
				new Diviseur(new Arme(2)),
				new Diviseur(new Arme(3)),
				new Diviseur(new Arme(4)),
				new Diviseur(new Arme(5)),
				new Diviseur(new Arme(6)),
				new Diviseur(new Arme(7)),
				new Diviseur(new VariableDecouverte(483)),
				new Diviseur(new VariableDecouverte(484))
		});
		
		// Bonus liés aux quêtes
		attaques.appliquerDiviseur("Quête",
			new Diviseur[] {
				new Diviseur(new Interrupteur(2569)),
				new Diviseur(new VariableEnsemble(3057, new int[] {1,2,3,4})),
				new Diviseur(new VariableEnsemble(1930, new int[] {0,9}))
		});
		
		// Périodes de la journée
		attaques.appliquerDiviseur("Période",
			new Diviseur[] {
				new Diviseur(new DisjonctionInterrupteurs(new int[] {8,9,10,11}))
		});
	}
}
