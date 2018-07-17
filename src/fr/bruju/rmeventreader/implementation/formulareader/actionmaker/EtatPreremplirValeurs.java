package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.decrypter.Recognizer;
import fr.bruju.rmeventreader.filereader.ActionOnLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.implementation.formulareader.formule.Valeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.ValeurNumerique;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class EtatPreremplirValeurs implements ActionOnLine {
	private Map<Integer, Valeur> variables;
	private Map<Integer, Valeur> interrupteurs;
	private List<Integer> sorties;

	public EtatPreremplirValeurs(Map<Integer, Valeur> variables, Map<Integer, Valeur> interrupteurs,
			List<Integer> valeursSorties) {
		this.variables = variables;
		this.interrupteurs = interrupteurs;
		this.sorties = valeursSorties;
	}

	@Override
	public void read(String ligne) {
		if (ligne.startsWith("//")) {
			return;
		}

		Pair<String, String> args = Recognizer.extractValues(ligne);

		if (args == null) {
			throw new LigneNonReconnueException(ligne);
		}

		int idVar = Integer.parseInt(args.getLeft());

		if (args.getRight().equals("DMG")) {
			sorties.add(idVar);
		} else if (args.getRight().equals("ON")) {
			interrupteurs.put(idVar, new ValeurNumerique(1));
		} else if (args.getRight().equals("OFF")) {
			interrupteurs.put(idVar, new ValeurNumerique(0));
		} else {
			int valFixee = Integer.parseInt(args.getRight());
			variables.put(idVar, new ValeurNumerique(valFixee));
		}
	}

}
