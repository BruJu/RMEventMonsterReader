package fr.bruju.rmeventreader.implementation.formulatracker.modifmodifstat.diviseurs;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonDiviseur implements Maillon {

	@Override
	public void traiter(Attaques attaques) {
		List<Diviseur> liste = new ArrayList<>();
		
		// Variable 360
		liste.add(new Diviseur(new DiviseurVariableEnsemble(360, new int[] {68, 69})));

		// Monstres volants
		liste = new ArrayList<>();

		liste.add(new Diviseur(new DiviseurPropriete("Volant")));
		liste.add(new Diviseur(new DiviseurVariable1Valeur(552, 83)));
		

		attaques.appliquerDiviseur(liste);
		// Par arme portée
		liste = new ArrayList<>();
		liste.add(new Diviseur(new DiviseurArme(1)));
		liste.add(new Diviseur(new DiviseurArme(2)));
		liste.add(new Diviseur(new DiviseurArme(3)));
		liste.add(new Diviseur(new DiviseurArme(4)));
		liste.add(new Diviseur(new DiviseurArme(5)));
		liste.add(new Diviseur(new DiviseurArme(6)));
		liste.add(new Diviseur(new DiviseurArme(7)));
		liste.add(new Diviseur(new DiviseurVariable(483)));
		liste.add(new Diviseur(new DiviseurVariable(484)));
		
		attaques.appliquerDiviseur(liste);
		
		
		// Par bonus particuliers
		liste = new ArrayList<>();
		liste.add(new Diviseur(new DiviseurInterrupteur(2569)));
		liste.add(new Diviseur(new DiviseurVariableEnsemble(3057, new int[] {1,2,3,4})));
		liste.add(new Diviseur(new DiviseurVariableEnsemble(1930, new int[] {0,9})));
		
		
		
		attaques.appliquerDiviseur(liste);
		
		
		
	}

}
