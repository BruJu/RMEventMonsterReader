package fr.bruju.rmeventreader.actionmakers.projet;

import java.util.List;

public class DictionnaireNonSymbolique extends Dictionnaire {
	public DictionnaireNonSymbolique(List<String> listeDeNoms) {
		super(listeDeNoms);
	}

	/**
	 * Renvoie " " si l'index est 0
	 * <br>
	 * Sinon renvoie la chaîne à la valeur index - 1
	 * <br>
	 * Si il y a un symbole (chaîne commencant par $ et suivi d'un caractère), il est retiré
	 * @param index La ligne voulue du dictionnaire
	 * @return Une représentation de la donnée à la ligne index
	 */
	@Override
	public String extraire(int index) {
		if (index == 0)
			return " ";
		
		String valeur = super.extraire(index);
		
		if (valeur.startsWith("$") && valeur.length() >= 2)
			valeur = valeur.substring(2);
		
		return valeur;
	}
}
