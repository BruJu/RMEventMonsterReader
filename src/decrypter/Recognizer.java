package decrypter;

import java.util.ArrayList;
import java.util.List;

public class Recognizer {
	private static final char CHAR_FILL = '_';
	private static final char CHAR_JOKER = '£';
	
	private static List<AssociationChaineInstruction> patterns = AssociationChaineInstruction.bookMaker();

	public ElementDecrypte recognize(String line) {
		
		for (AssociationChaineInstruction pattern : patterns) {
			List<String> argumentsReconnus = tryPattern(pattern.pattern, line);
			
			if (argumentsReconnus != null) {
				return new ElementDecrypte(pattern.instruction, argumentsReconnus);
			}
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
		
		if (positionPattern != pattern.length() && pattern.charAt(positionPattern) != CHAR_JOKER && pattern.charAt(positionPattern) != CHAR_FILL) {
			return null;
		}
		
		if (builder != null) {
			arguments.add(builder.toString());
		}
		
		return arguments;
	}

}
