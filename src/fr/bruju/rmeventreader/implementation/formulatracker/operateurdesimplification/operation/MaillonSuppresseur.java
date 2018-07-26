package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

/**
 * Le suppresseur est un maillon-visiteur qui se charge de supprimer les conditions dont un des membres concerne
 * uniquement une variable dont le numéro est dans un fichier.
 * 
 * @author Bruju
 *
 */
public class MaillonSuppresseur extends ConstructeurDeComposantR implements Maillon {
	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//  - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON -

	@Override
	public void traiter(Attaques attaques) {
		extraireValeursASupprimer("ressources/formulatracker/Suppresseur.txt");
		
		attaques.apply(formule -> new FormuleDeDegats(
				formule.conditions.stream()
						.map(this::traiter)
						.map(composant -> (Condition) composant)
						.filter(c -> !(c.equals(CFixe.get(true)))).collect(Collectors.toList()),
				formule.formule));
	}
	
	/**
	 * Lit le fichier donné pour remplir la liste des valeurs à retirer
	 * @param filename Le nom du fichier à lire
	 * @return Vrai si la lecture a réussi
	 */
	private boolean extraireValeursASupprimer(String filename) {
		List<String[]> valeurs;

		try {
			valeurs = FileReaderByLine.lireFichier(filename, 1);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

		valeursRetirees = valeurs.stream().map(v -> v[0]).map(v -> new VBase(Integer.parseInt(v)))
				.collect(Collectors.toList());

		return true;
	}

	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//              - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT -

	/**
	 * Valeurs à retirer
	 */
	private Collection<? extends Valeur> valeursRetirees;

	@Override
	protected Composant traiter(CVariable cVariable) {
		for (Valeur v : valeursRetirees) {
			if (cVariable.gauche.equals(v)) {
				return CFixe.get(true);
			}

			if (cVariable.droite.equals(v)) {
				return CFixe.get(true);
			}
		}

		return cVariable;
	}
}
