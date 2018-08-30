package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base;

import java.util.Arrays;
import java.util.function.BiConsumer;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.header.Monteur;

/**
 * Lit un tableau de valeurs éventuellement précédé d'une clé
 * 
 * @author Bruju
 *
 * @param <K>
 */
public class Chemin<K extends Monteur<?>> implements Traitement<K> {
	/** Clé si elle existe */
	private String nomChamp;
	/** Valeurs lues */
	private String[] valeur;
	/** Opération de montage */
	private BiConsumer<K, String[]> operationDeMontage;
	
	/**
	 * Lit un tableau de valeurs avec une clé si elle est précisée
	 * @param nomChamp null si il n'y a pas de clé, la clé sinon
	 * @param operationDeMontage L'opération à appliquer une fois le tableau lu
	 */
	public Chemin(String nomChamp, BiConsumer<K, String[]> operationDeMontage) {
		this.nomChamp = nomChamp;
		this.operationDeMontage = operationDeMontage;
	}
	
	@Override
	public Avancement traiter(String ligne) {
		String[] split = ligne.split(">");
		
		valeur = Arrays.copyOfRange(split, 1, split.length);
		
		return Avancement.Suivant;
	}

	@Override
	public void appliquer(K monteur) {
		operationDeMontage.accept(monteur, valeur);
	}
	
	@Override
	public String toString() {
		return "Chemin " + nomChamp;
	}
}
