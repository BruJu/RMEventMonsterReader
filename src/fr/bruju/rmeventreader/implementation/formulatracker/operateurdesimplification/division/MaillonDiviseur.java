package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division;

import java.util.ArrayList;
import java.util.List;

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

		Builder builder = new Builder();
		
		builder.setTitre("Sous-Attaque")
				.variableEnsemble(360, new int[] {68, 69})
				.appliquer(attaques)
				
				.setTitre("Volant")
				.propriete("Volant")
				.variable1Valeur(552, 83)
				.appliquer(attaques)
				
				.setTitre("Arme")
				.arme(1)
				.arme(2)
				.arme(3)
				.arme(4)
				.arme(5)
				.arme(6)
				.arme(7)
				.variableDecouverte(483)
				.variableDecouverte(484)
				.appliquer(attaques)
				
				.setTitre("Quête")
				.interrupteur(2569)
				.variableEnsemble(3057, new int[] {1,2,3,4})
				.variableEnsemble(1930, new int[] {0,9})
				.appliquer(attaques)
				
				.setTitre("Période")
				.disjonctionInterrupteurs(new int[] {8,9,10,11})
				.appliquer(attaques);
	}
	
	
	
	private class Builder {
		String nom;
		List<Diviseur> diviseurs;
		
		private Builder() {
			nom = "";
			diviseurs = new ArrayList<>();
		}
		
		private Builder appliquer(Attaques attaques) {
			attaques.appliquerDiviseur(nom, diviseurs.toArray(new Diviseur[0]));
			nom = "";
			diviseurs.clear();
			return this;
		}
		
		private Builder setTitre(String nom) {
			this.nom = nom;
			return this;
		}

		private Builder ajouterDiviseur(StrategieDeDivision strategie) {
			diviseurs.add(new Diviseur(strategie));
			return this;
		}
		
		private Builder variableEnsemble(int idVariable, int[] valeurs) {
			return ajouterDiviseur(new VariableEnsemble(idVariable, valeurs));
		}
		
		private Builder propriete(String nom) {
			return ajouterDiviseur(new Propriete(nom));
		}
		
		private Builder variable1Valeur(int idVariable, int valeur) {
			return ajouterDiviseur(new Variable1Valeur(idVariable, valeur));
		}
		
		private Builder arme(int heros) {
			return ajouterDiviseur(new Arme(heros));
		}
		
		private Builder variableDecouverte(int idVariable) {
			return ajouterDiviseur(new VariableDecouverte(idVariable));
		}

		private Builder interrupteur(int idSwitch) {
			return ajouterDiviseur(new Interrupteur(idSwitch));
		}

		private Builder disjonctionInterrupteurs(int[] idSwitch) {
			return ajouterDiviseur(new DisjonctionInterrupteurs(idSwitch));
		}
	}
}
