package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.supressiondeconditions;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;

class Supresseur extends ConstructeurDeComposantR {
	private Collection<? extends Valeur> valeursRetirees;
	private String fileNameLu = null;

	private boolean extraireValeursASupprimer(String filename) {
		if (Objects.equals(fileNameLu, filename)) {
			return true;
		}
		
		fileNameLu = filename;
		List<String[]> valeurs;
		
		try {
			valeurs = FileReaderByLine.lireFichier(filename, 1);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		
		valeursRetirees = valeurs.stream()
				.map(v -> v[0])
				.map(v -> new VBase(Integer.parseInt(v)))
				.collect(Collectors.toList());
		
		return true;
	}

	public Condition soumettre(Condition c, String filename) {
		if (!extraireValeursASupprimer(filename))
			return null;
		
		return (Condition) traiter(c);
	}

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
