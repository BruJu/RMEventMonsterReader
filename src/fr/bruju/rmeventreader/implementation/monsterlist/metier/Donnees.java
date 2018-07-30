package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Donnees<T> {
	//private Monstre monstre;
	private LinkedHashMap<String, T> donnees;
	private Function<T, String> fonctionDAffichage;
	
	public Donnees(Monstre monstre, List<String> noms, T valeurDeBase, Function<T, String> fonctionDAffichage) {
		//this.monstre = monstre;
		this.donnees = new LinkedHashMap<>(noms.size());
		this.fonctionDAffichage = fonctionDAffichage;
		
		noms.forEach(nom -> donnees.put(nom, valeurDeBase));
	}
	
	
	public T get(String nom) {
		return donnees.get(nom);
	}
	
	public void set(String nom, T nouvelleValeur) {
		donnees.put(nom, nouvelleValeur);
	}
	
	public void compute(String nom, T nouvelleValeur, T a) {
		
	}
	
	
	/* =========
	 * AFFICHAGE
	 * ======== */
	
	public String getHeader() {
		return donnees.keySet().stream().collect(Collectors.joining(";"));
	}
	
	public String getCSV() {
		return donnees.values().stream().map(fonctionDAffichage).collect(Collectors.joining(";"));
	}
}
