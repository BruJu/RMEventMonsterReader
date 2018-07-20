package test.formulareader.condition;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.formulareader.formule.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNommee;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple.ValeurNumerique;
import fr.bruju.rmeventreader.utilitaire.Pair;

@SuppressWarnings({ "unchecked", "rawtypes" })
public class Inclusion {
	
	
	public Pair make(Valeur variable, Operator operateur, int valeurNumerique, int resultatAttendu) {
		return new Pair(new ConditionVariable(variable, operateur, new ValeurNumerique(valeurNumerique)), resultatAttendu);
	}
	
	public void verifierTableau(Condition conditionAInclure, Iterable<Pair> valeurs_testees) {
		for (Pair<Condition, Integer> paire : valeurs_testees) {
			Condition base = paire.getLeft();
			Integer resAttendu = paire.getRight();
			
			Condition inclusion = base.integrerCondition(conditionAInclure);
			Condition attendu;
			
			if (resAttendu == 1) {
				attendu = ConditionFixe.VRAI;
			} else if (resAttendu == 0) {
				attendu = ConditionFixe.FAUX;
			} else {
				attendu = base;
			}
			
			System.out.println();
			System.out.println("Connaissance = " + conditionAInclure.getString());
			System.out.println("Condition = " + base.getString());
			System.out.println("Resultat = " + inclusion.getString());
			System.out.println("Attendu = " + attendu.getString());
			assertEquals(inclusion, attendu);
		}
	}
	@Test
	public void global() {
		// On teste chaque opérateur avec 0 99 100 101 et 150
		
		Map<Operator, Integer[]> associations = new HashMap<>();
		
		// Identique
		associations.put(Operator.IDENTIQUE, new Integer[] {
				 0,   0,   1,   0,   0,	// Identique
				 1,   1,   0,   1,   1,	// Différent
				 1,   1,   0,   0,   0, // Inférieur
				 1,   1,   1,   0,   0, // Inférieur ou égal
				 0,   0,   0,   1,   1, // Supérieur
				 0,   0,   1,   1,   1, // Supérieur ou égal
		});
		

		
		// tests
		associations.forEach((operateur, carte) -> testerCarte(operateur, carte) );
	}
	
	private void testerCarte(Operator operateur, Integer[] carte) {
		Valeur x = new ValeurNommee(0, "x");
		
		List<Pair> conditions = new ArrayList<>();
		
		
		Operator[] operateursATester = new Operator[] {Operator.IDENTIQUE, Operator.DIFFERENT, Operator.INF, Operator.INFEGAL, Operator.SUP, Operator.SUPEGAL};
		int[] valeurs = new int[] {50, 99, 100, 101, 150};
		
		for (int i = 0 ; i != operateursATester.length ; i++) {
			for (int j = 0 ; j != 5 ; j++) {
				conditions.add(make(x, operateursATester[i], valeurs[j], carte[i * 5 + j]));
			}
		}
		
		
		verifierTableau(new ConditionVariable(x, operateur, new ValeurNumerique(100)), conditions);
	}


}
