package com.bj.perso.rmeventreader.reconnaisseur;

import utility.PairList;

public enum InstructionsMaker {
	// Objets
	VOID(InstructionType.InstrVoid, "_ et _", new DataType[] {DataType.IGNORE, DataType.IGNORETWO});
	
	// Attributs
	private String pattern = null;
	private InstructionType instruction;
	private DataType[] data;

	// CONSTANTES
	private static final char CHAR_JOKER = '£';
	private static final char CHAR_FILL = '_';
	
	// Constructeur
	InstructionsMaker(InstructionType instruction, String pattern, DataType[] data) {
		this.instruction = instruction;
		this.pattern = pattern;
		this.data = data;
	}

	public PairList<DataType, String> filtrer(String data) {
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
			
			// Arret de la reconnaissance
			if (charPattern == CHAR_JOKER) {
				joker = true;
				break;
			}
			
			// Remplissage
			if (charPattern == CHAR_FILL) {
				if (builder == null) {
					if (numeroDeLargument == this.data.length)
						return null;
					
					builder = new StringBuilder();

					if (positionPattern + 1 == pattern.length()) {
						charNextPattern = null;
					} else {
						charNextPattern = pattern.charAt(positionPattern + 1);
					}
				}
				
				if (charNextPattern == null || charNextPattern == charData) {
					// Cumuler
					builder.append(charData);
				} else {
					// Décharger
					dataRead.put(this.data[numeroDeLargument], builder.toString());
					builder = null;
					
					numeroDeLargument ++;
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
			dataRead.put(this.data[numeroDeLargument], builder.toString());
		}
		
		if (!joker && numeroDeLargument != this.data.length) {
			return null;
		}
		
		
		return dataRead;
	}
	
	public InstructionType getInstruction() {
		return instruction;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public DataType[] getDataTypes() {
		return data;
	}
}
