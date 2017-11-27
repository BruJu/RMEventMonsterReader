package com.bj.perso.rmeventreader.reconnaisseur;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import utility.PairList;

public class PatternMatcher {
	// CONSTANTES
	private static final char CHAR_JOKER = '£';
	private static final char CHAR_FILL = '_';
	

	public PairList<DataType, String> filtrer(InstructionsMaker instruction, String data) {
		return filtrer(instruction.getPattern(), instruction.getDataTypes(), data);
	}
	
	public PairList<DataType, String> filtrer(String pattern, DataType[] dataTypes, String data) {
		/*
		 * On réimplémente le pattern maching afin d'avoir une utilisation user friendly
		 * des regex.
		 */
		int positionPattern = 0;
		int positionData = 0;
		
		char charPattern;
		char charData;
		Character charNextPattern = null;
		
		StringBuilder builder = null;
		int numeroDeLargument = 0;
		
		boolean joker = false;
		
		PairList<DataType, String> dataRead = new PairList<>();
		
		while (positionData != data.length()) {
			charPattern = pattern.charAt(positionPattern);
			charData = data.charAt(positionData);
			
			System.out.println(charPattern + " / " + charData);
			
			// Arret de la reconnaissance
			if (charPattern == CHAR_JOKER) {
				joker = true;
				break;
			}
			
			// Remplissage
			if (charPattern == CHAR_FILL) {
				if (builder == null) {
					if (numeroDeLargument == dataTypes.length)
						return null;
					
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
					dataRead.put(dataTypes[numeroDeLargument], builder.toString());
					System.out.println("["+ dataTypes[numeroDeLargument] + "-" + builder.toString() + "]");
					builder = null;
					
					numeroDeLargument ++;
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
		
		if (builder != null) {
			dataRead.put(dataTypes[numeroDeLargument], builder.toString());
			System.out.println("["+ dataTypes[numeroDeLargument] + "-" + builder.toString() + "]");
			numeroDeLargument++;
		}
		
		if (!joker && numeroDeLargument != dataTypes.length) {
			return null;
		}
		
		
		return dataRead;
	}
	
	/* VANILLA */
	
	public String[] recognize(String ligne, List<Pattern> patterns) {
		String[] result = null;
		
		for (Pattern pattern : patterns) {
			result = recognize(ligne, pattern);
			
			if (result != null)
				return result;
		}
		
		return null;
	}
	

	private String[] recognize(String ligne, Pattern p) { 
		Matcher matcher = p.matcher(ligne);
		
		if (!matcher.find())
			return null;
		
		ArrayList<String> strings = new ArrayList<>();
		
		for (int i = 0 ; i <= matcher.groupCount() ; i++) {
			strings.add(matcher.group(i));
		}
		
		return (String[]) strings.toArray();
	}
}

