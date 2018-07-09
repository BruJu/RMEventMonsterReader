package decrypter;

import java.util.ArrayList;
import java.util.List;

import decrypter.convertisseurs.Action;

public class Recognizer {
	private static boolean d = false;
	
	private static final char CHAR_FILL = '_';
	private static final char CHAR_JOKER = '£';
	
	private static List<Action> patterns = AssociationChaineInstruction.bookMaker();

	public ElementDecrypte recognize(String line) {
		if (d) System.out.println("###### " + line);
		
		for (Action pattern : patterns) {
			if (d) System.out.print(pattern.getPattern() + " : ");
			
			List<String> argumentsReconnus = tryPattern(pattern.getPattern(), line);
			
			if (argumentsReconnus != null) {
				if (d) System.out.println("succes");
				return new ElementDecrypte(pattern, argumentsReconnus);
			}
			
			if (d) System.out.println("echec");
		}
		
		return null;
	}

	public List<String> tryPattern(String pattern, String data) {
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
		
		// Analyse comparative
		while (positionData != data.length() && positionPattern != pattern.length()) {
			charPattern = pattern.charAt(positionPattern);
			charData = data.charAt(positionData);
						
			// Arret de la reconnaissance
			if (charPattern == CHAR_JOKER) {
				break;
			}
			
			// Remplissage
			if (charPattern == CHAR_FILL) {
				if (builder == null) {
					builder = new StringBuilder();

					if (positionPattern + 1 == pattern.length()) {
						charNextPattern = null;
					} else {
						charNextPattern = pattern.charAt(positionPattern + 1);
					}
				}
				
				if (charNextPattern == null || charNextPattern != charData) {
					// Cumuler
					builder.append(charData);
				} else {
					// Décharger
					arguments.add(builder.toString());
					builder = null;
					positionPattern ++;

					continue;
				}
			} else {
				// Comparaison
				if (charPattern != charData) {
					return null;
				}
				
				positionPattern ++;
			}
			
			positionData ++;
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
