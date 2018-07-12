package decrypter;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe permettant de reconnaitre des cha�nes avec le pattern suivant :
 * Tous les symboles comptent
 * 
 * 
 * Lorsqu'un _ est lu, le reconnaisseur lit la valeur jusqu'� trouver le symbole apr�s le _
 * Si un � est lu, le reconnaisseur arr�te son travail et annonce qu'il a reconnu la cha�ne
 *
 */
public class Recognizer {
	/*
	 * 1/
	 * On n'utilise pas de classe avec des regex pr�d�finis car les regex sont assez compliqu�s
	 * � d�chiffrer quand on n'a pas l'habitude.
	 * La puissance des regex n'est pas pertinente par rapport � l'objectif de lisiblit� recherch�.
	 * 
	 * 2/ Une meilleur impl�mentation serait de faire une interface Recognizer, et de permettre
	 * aux classes de choisir leur impl�mentation.
	 * Cela permettrait d'int�grer la possibilit� d'utiliser des regex lorsque leur puissance
	 * se justifie.
	 */
	
	/**
	 * Caract�re symbolisant une reconnaissance de valeur
	 */
	private static final char CHAR_FILL = '_';
	
	/**
	 * Caract�re symbolisant un pattern qui accepte n'importe quoi � la fin
	 */
	private static final char CHAR_JOKER = '�';
	
	/**
	 * La m�thode tryPattern �tant static, on ne peut pas impl�menter cette classe
	 */
	private Recognizer() {}
	
	/**
	 * Renvoie la liste des param�tres reconnus si le pattern correspond � la donn�e.
	 * Renvoie null sinon
	 * @param pattern Le pattern � reconna�tre
	 * @param data La ligne � reconna�tre
	 * @return Une liste de param�tres reconnus si le pattern correspond, null sinon
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
					// D�charger
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
		
		// D�charge du builder
		if (builder != null) {
			arguments.add(builder.toString());
		}
		
		// Cas 1 : On est sur un joker
		if (pattern.length() != positionPattern && pattern.charAt(positionPattern) == CHAR_JOKER) {
			return arguments;
		}
		
		// Cas 2 : on est arriv�e � la fin des donn�es � lire
		if (positionData == data.length()) {
			if (positionPattern == pattern.length()) {
				return arguments;
			} else {
				// Le pattern n'est pas respect� sauf si on est sur un _ final
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
