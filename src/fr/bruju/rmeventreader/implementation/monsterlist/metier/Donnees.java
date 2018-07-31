package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.Objects;

/**
 * 
 * @author Bruju
 *
 * @param <T> Le type des données. Doivent être immutables
 */
public class Donnees<T> {
	//private Monstre monstre;
	private LinkedHashMap<String, T> donnees;
	private Function<T, String> fonctionDAffichage;
	
	/**
	 * Crée un groupement de données
	 * @param monstre Le monstre
	 * @param noms Le nom des différentes données contenues
	 * @param valeurDeBase La valeur de base
	 * @param fonctionDAffichage 
	 */
	public Donnees(Monstre monstre, Collection<String> noms, T valeurDeBase, Function<T, String> fonctionDAffichage) {
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
	
	public void compute(String nom, BiFunction<String, T, T> mergeFunction) {
		donnees.compute(nom, mergeFunction);
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

	/* =================
	 * HASHCODE / EQUALS 
	 * ================= */
	

	@Override
	public int hashCode() {
		return Objects.hashCode(donnees);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object object) {
		if (object instanceof Donnees) {
			Donnees that = (Donnees) object;
			return Objects.equals(this.donnees, that.donnees);
		}
		return false;
	}
}
