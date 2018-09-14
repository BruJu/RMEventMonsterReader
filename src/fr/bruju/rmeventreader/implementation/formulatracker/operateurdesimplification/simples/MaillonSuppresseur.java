package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.simples;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.FormuleDeDegats;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

/**
 * Le suppresseur est un maillon-visiteur qui se charge de supprimer les conditions dont un des membres concerne
 * uniquement une variable dont le numéro est dans un fichier.
 * 
 * @author Bruju
 *
 */
public class MaillonSuppresseur extends ConstructeurDeComposantsRecursif implements Maillon {
	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//  - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON -

	@Override
	public void traiter(Attaques attaques) {
		extraireValeursASupprimer("ressources/formulatracker/Suppresseur.txt");
		
		attaques.transformerFormules(formule -> new FormuleDeDegats(
				formule
						.conditions.stream()
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
		valeursRetirees = new ArrayList<>();
		
		return FileReaderByLine.lectureFichierRessources(filename, ligne -> {
			valeursRetirees.add(new VBase(Integer.parseInt(ligne)));
		});
	}

	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//              - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT -

	/**
	 * Valeurs à retirer
	 */
	private List<Valeur> valeursRetirees;

	@Override
	protected Composant modifier(CVariable cVariable) {
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
