package fr.bruju.rmeventreader.implementation.detectiondeformules._personnages;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.Ressources;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

public interface VariablesAssociees {
	public static interface StatistiqueExtraite<T extends VariablesAssociees> {
		public void appliquer(Individu<T> personnage, String nomStatistique, int numero, boolean estUnInterrupteur);
	}
	
	public static <T extends VariablesAssociees> Map<String, Individu<T>>
		remplirStatistiques(StatistiqueExtraite<T> fonctionAppliquee,
							Function<Individu<T>, T> fonctionDInstanciation
				) {
		Map<String, Individu<T>> individus = new HashMap<>();
		
		LecteurDeFichiersLigneParLigne.lectureFichierRessources(Ressources.STATISTIQUES, ligne -> {
			String[] donnees = ligne.split(" ", 3);
			
			String nomPersonnage = donnees[0];
			String nomStatistique = donnees[1];
			boolean estPropriete = donnees[2].charAt(0) == 'S';
			Integer numeroVariable = Integer.decode((estPropriete ? donnees[2].substring(1) : donnees[2]));
			
			Individu<T> individu = individus.get(nomPersonnage);
			
			if (individu == null) {
				individu = new Individu<>(nomPersonnage, fonctionDInstanciation);
				individus.put(nomPersonnage, individu);
			}
			
			fonctionAppliquee.appliquer(individu, nomStatistique, numeroVariable, estPropriete);
		});
		
		return individus;
	}
}
