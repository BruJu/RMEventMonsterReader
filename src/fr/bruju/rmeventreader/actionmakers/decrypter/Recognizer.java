package fr.bruju.rmeventreader.actionmakers.decrypter;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Classe permettant de reconnaitre des chaînes avec le pattern suivant : Tous les symboles comptent
 * 
 * 
 * Lorsqu'un _ est lu, le reconnaisseur lit la valeur jusqu'à trouver le symbole aprés le _ Si un £ est lu, le
 * reconnaisseur arrête son travail et annonce qu'il a reconnu la chaîne
 *
 */
public class Recognizer {
	/*
	 * 1/
	 * On n'utilise pas de classe avec des regex prédéfinis car les regex sont assez compliqués à déchiffrer quand on
	 * n'a pas l'habitude.
	 * La puissance des regex n'est pas pertinente par rapport à l'objectif de lisiblité recherché.
	 * 
	 * 2/ Une meilleur implémentation serait de faire une interface Recognizer, et de permettre aux classes de choisir
	 * leur implémentation.
	 * Cela permettrait d'intégrer la possibilité d'utiliser des regex lorsque leur puissance se justifie.
	 */

	/**
	 * Caractère symbolisant une reconnaissance de valeur
	 */
	private static final char CHAR_FILL = '_';

	/**
	 * Caractère symbolisant une reconnaissance de valeur ne pouvant être vide
	 */
	private static final char CHAR_FILL_NOT_EMPTY = 'µ';

	/**
	 * Caractère symbolisant un pattern qui accepte n'importe quoi à la fin
	 */
	private static final char CHAR_JOKER = '£';
	
	/**
	 * Pattern représentant une ligne de deux valeur distinctes 
	 */
	private static final String PATTERN_DOUBLEVALUE = "_ _";

	/**
	 * La méthode tryPattern étant static, on ne peut pas instancier cette classe
	 */
	private Recognizer() {
	}

	/**
	 * Extrait de la ligne deux valeurs séparées par un espace
	 * @param data La ligne contenant les données
	 * @return Une paire avec les deux valeurs
	 */
	public static Pair<String, String> extractValues(String data) {
		List<String> str = tryPattern(PATTERN_DOUBLEVALUE, data);
		
		if (str == null)
			return null;
		
		if (str.size() == 1) {
			str.add("");
		}
		
		return new Pair<>(str.get(0), str.get(1));
	}
	
	/**
	 * Renvoie la liste des paramètres reconnus si le pattern correspond à la donnée. Renvoie null sinon
	 * 
	 * @param pattern Le pattern à reconnaître
	 * @param data La ligne à reconnaître
	 * @return Une liste de paramètres reconnus si le pattern correspond, null sinon
	 */
	public static List<String> tryPattern(String pattern, String data) {
		int positionPattern = 0;
		int positionData = 0;

		char charPattern;
		char charData;
		Character charNextPattern = null;

		StringBuilder builder = null;

		List<String> arguments = new ArrayList<>();

		// Passer les espaces
		while (positionData != data.length() && data.charAt(positionData) == ' ') {
			positionData++;
		}

		boolean builderDejaRempli = false;

		// Analyse comparative
		while (positionData != data.length() && positionPattern != pattern.length()) {
			charPattern = pattern.charAt(positionPattern);
			charData = data.charAt(positionData);

			// Arret de la reconnaissance
			if (charPattern == CHAR_JOKER) {
				break;
			}

			// Remplissage
			if (charPattern == CHAR_FILL || charPattern == CHAR_FILL_NOT_EMPTY) {
				if (builder == null) {
					builder = new StringBuilder();
					builderDejaRempli = false;

					if (positionPattern + 1 == pattern.length()) {
						charNextPattern = null;
					} else {
						charNextPattern = pattern.charAt(positionPattern + 1);
					}
				}

				if (charNextPattern == null || charNextPattern != charData
						|| (charPattern == CHAR_FILL_NOT_EMPTY && !builderDejaRempli)) {
					// Cumuler
					builder.append(charData);
					builderDejaRempli = true;
				} else {
					// Décharger
					arguments.add(builder.toString());
					builder = null;
					positionPattern++;

					continue;
				}
			} else {
				// Comparaison
				if (charPattern != charData) {
					return null;
				}

				positionPattern++;
			}

			positionData++;
		}

		// Décharge du builder
		if (builder != null) {
			arguments.add(builder.toString());
		}

		// Cas 1 : On est sur un joker
		if (pattern.length() != positionPattern && pattern.charAt(positionPattern) == CHAR_JOKER) {
			return arguments;
		}

		// Cas 2 : on est arrivée à la fin des données à lire
		if (positionData == data.length()) {
			if (positionPattern == pattern.length()) {
				return arguments;
			} else {
				// Le pattern n'est pas respecté sauf si on est sur un _ final
				if (pattern.charAt(positionPattern) == CHAR_FILL && positionPattern + 1 == pattern.length()) {
					return arguments;
				} else {
					return null;
				}

			}
		} else {
			return null;
		}
	}

}
