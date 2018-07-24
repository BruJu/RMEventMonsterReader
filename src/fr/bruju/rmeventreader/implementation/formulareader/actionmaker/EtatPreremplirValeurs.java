package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.ActionOnLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.filereader.Recognizer;
import fr.bruju.rmeventreader.implementation.formulareader.formule.NewValeur;
import fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.Valeur;
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

		if (args.getRight().equals("DMG") || args.getRight().equals("HEAL")) {
			sorties.add(idVar);
		} else if (args.getRight().equals("ON")) {
			interrupteurs.put(idVar, NewValeur.True());
		} else if (args.getRight().equals("OFF")) {
			interrupteurs.put(idVar, NewValeur.False());
		} else if (args.getRight().charAt(0) == '@') {
			interrupteurs.put(idVar, NewValeur.Nommee(idVar, args.getRight()));
		} else if (args.getRight().charAt(0) == '$') {
			variables.put(idVar, NewValeur.Nommee(idVar, args.getRight()));
		} else {
			int valFixee = Integer.parseInt(args.getRight());
			variables.put(idVar, NewValeur.Numerique(valFixee));
		}
	}

}
