package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.division;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.Encyclopedie;
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
		Encyclopedie encyclopedie = new Encyclopedie();
		
		
		Builder builder = new Builder();
		
		builder.setTitre("Sous-Attaque")
				.variableEnsemble(360, new int[] {68, 69}, v -> "Attaque " + (char) ('A' + v - 68))
				.appliquer(attaques)
				
				.setTitre("Volant")
				.propriete("Volant")
				.variable1Valeur(552, 83, b -> b ? "Némiéry" : "Autre")
				.appliquer(attaques)
				
				.setTitre("Arme")
				.arme(1, a -> a == 0 ? "Autre" : encyclopedie.getSansSymbole("OBJET", a-1))
				.arme(2, a -> a == 0 ? "Autre" : encyclopedie.getSansSymbole("OBJET", a-1))
				.arme(3, a -> a == 0 ? "Autre" : encyclopedie.getSansSymbole("OBJET", a-1))
				.arme(4, a -> a == 0 ? "Autre" : encyclopedie.getSansSymbole("OBJET", a-1))
				.arme(5, a -> a == 0 ? "Autre" : encyclopedie.getSansSymbole("OBJET", a-1))
				.arme(6, a -> a == 0 ? "Autre" : encyclopedie.getSansSymbole("OBJET", a-1))
				.arme(7, a -> a == 0 ? "Autre" : encyclopedie.getSansSymbole("OBJET", a-1))
				.variableDecouverte(483, a -> a == null ? "Autre" : encyclopedie.getSansSymbole("OBJET", a-1))
				.variableDecouverte(484, a -> a == null ? "Autre" : encyclopedie.getSansSymbole("OBJET", a-1))
				.appliquer(attaques)
				
				.setTitre("Quête")
				.interrupteur(2569, b -> b ? "Statue" : "Initial")
				.variableEnsemble(3057, new int[] {0,1,2,3}, v -> "Niveau " + v)
				.variableEnsemble(1930, new int[] {0,9}, v -> v == 9 ? "Désinhibé" : "Initial")
				.appliquer(attaques)
				
				.setTitre("Période")
				.disjonctionInterrupteurs(new int[] {8,9,10,11}, v -> encyclopedie.get("SWITCH", v-1))
				.appliquer(attaques);
	}
	
	
	
	class Builder {
		String nom;
		List<Diviseur> diviseurs;
		
		public Builder() {
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
		
		private Builder variableEnsemble(int idVariable, int[] valeurs, Function<Integer, String> func) {
			return ajouterDiviseur(new VariableEnsemble(idVariable, valeurs, func));
		}
		
		private Builder propriete(String nom) {
			return ajouterDiviseur(new Propriete(nom));
		}
		
		private Builder variable1Valeur(int idVariable, int valeur, Function<Boolean, String> func) {
			return ajouterDiviseur(new Variable1Valeur(idVariable, valeur, func));
		}
		
		private Builder arme(int heros, Function<Integer, String> func) {
			return ajouterDiviseur(new Arme(heros, func));
		}
		
		private Builder variableDecouverte(int idVariable, Function<Integer, String> func) {
			return ajouterDiviseur(new VariableDecouverte(idVariable, func));
		}

		private Builder interrupteur(int idSwitch, Function<Boolean, String> func) {
			return ajouterDiviseur(new Interrupteur(idSwitch, func));
		}

		private Builder disjonctionInterrupteurs(int[] idSwitch, Function<Integer, String> func) {
			return ajouterDiviseur(new DisjonctionInterrupteurs(idSwitch, func));
		}
	}
}
