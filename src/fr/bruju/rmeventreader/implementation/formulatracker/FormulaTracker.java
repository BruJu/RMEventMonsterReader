package fr.bruju.rmeventreader.implementation.formulatracker;


import fr.bruju.rmeventreader.implementation.formulatracker.actionmaker.MaillonActionMaker;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage.MaillonSetAffichagegetString;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage.MaillonSystemOut;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division.MaillonDiviseur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.extension.Borne;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.extension.Encadrer;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.factorisation.Factorisation;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.MaillonIntegration;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage.MaillonEcritureFichier;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage.MaillonSetAffichageCSV;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.simples.MaillonDesinjecteur;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.simples.MaillonEvaluationPartielle;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.simples.MaillonRetirerSiInutile;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.simples.MaillonUnificateur;

public class FormulaTracker implements Runnable {
	public void run() {
		Maillon[] maillons = new Maillon[] {
				new MaillonActionMaker(),
				new MaillonEvaluationPartielle(),
				new MaillonIntegration(),
				new MaillonDesinjecteur(),						// A faire aprés l'inclusion
				new MaillonUnificateur(),						// A faire aprés désinjection
				new Factorisation(),
				new MaillonRetirerSiInutile(),
				new Borne(),
				new Encadrer(),
				new MaillonSetAffichagegetString(),
				new MaillonSystemOut(),
				new MaillonDiviseur(),
				new MaillonSetAffichageCSV(),
				new MaillonSystemOut(),
				new MaillonEcritureFichier()
		};
		
		Attaques attaques = new Attaques();
		
		for (Maillon maillon : maillons) {
			maillon.traiter(attaques);
		}
	}

}
