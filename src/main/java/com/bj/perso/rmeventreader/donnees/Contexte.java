package com.bj.perso.rmeventreader.donnees;

import java.util.ArrayList;
import java.util.List;

public class Contexte {
	public static final int CONST_VAR_GLOBAL = 5000;
	public static final int CONST_NB_GROUPE = 3;
	
	private static Contexte contexte = null;
	
	public int[] varGlobales;
	
	private List<Integer> enregistrementsId;
	private ArrayList<List<Integer>> sousenresgistrementsId;
	
	private Contexte() {
		varGlobales = new int[CONST_VAR_GLOBAL];
		enregistrementsId = new ArrayList<>();
		sousenresgistrementsId = new ArrayList<>(CONST_NB_GROUPE);
		
		for (int i = 0 ; i != CONST_NB_GROUPE ; i++) {
			sousenresgistrementsId.add(new ArrayList<>());
		}
	}
	
	public Contexte getInstance() {
		if (contexte == null)
			contexte = new Contexte();
		
		return contexte;
	}
	
	
	
}
