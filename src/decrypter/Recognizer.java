package decrypter;

import java.util.ArrayList;
import java.util.List;

import decrypter.convertisseurs.Action;

public class Recognizer {
	private static final char CHAR_FILL = '_';
	private static final char CHAR_JOKER = '£';
	
	private static List<Action> patterns = AssociationChaineInstruction.bookMaker();

	public ElementDecrypte recognize(String line) {
		//System.out.println(line);
		
		for (Action pattern : patterns) {
			//System.out.print(pattern.getPattern() + " : ");
			
			List<String> argumentsReconnus = tryPattern(pattern.getPattern(), line);
			
			if (argumentsReconnus != null) {
				//System.out.println("succes");
				return new ElementDecrypte(pattern, argumentsReconnus);
			}
			
			//System.out.println("echec");
		}
		
		return null;
	}

	private List<String> tryPattern(String pattern, String data) {
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
		
		if (positionPattern != pattern.length() && pattern.charAt(positionPattern) != CHAR_JOKER) {
			
			if (pattern.charAt(positionPattern) == CHAR_FILL && positionPattern + 1 == pattern.length()) {
				
			} else {
				return null;
			}
		}
		
		if (builder != null) {
			arguments.add(builder.toString());
		}
		
		return arguments;
	}

}
