package com.bj.perso.rmeventreader.reconnaisseur;

import java.util.regex.Pattern;

public enum InstructionsMaker {
	// Objets
	VOID("_", "InstructionVoid");
	
	// TODO : chaîne, [enum de paramètres avec un nom], [enum de noms d'instructions]
	// utiliser InstructionFactory qui prend cette enum, le tableau renvoyer par PatternMatcher
	// et qui intancie avec les bons paramètres
	
	// Attributs
	private Pattern pattern = null;
	@SuppressWarnings("rawtypes")
	private Class classe = null;
	
	// Constructeur
	InstructionsMaker(String pattern, String instruction) {
		this.pattern = Pattern.compile(filtrer(pattern));
		try {
			this.classe = Class.forName("com.bj.perso.rmeventreader.instructions." + instruction);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e.getMessage());	// TODO : faire une exception dédiée
		}
	}

	private String filtrer(String pattern2) {
		// TODO : enlever toute trace de regex dans la string et permettre au symbole _ de capturer les chaines
		return pattern2;
	}
}
