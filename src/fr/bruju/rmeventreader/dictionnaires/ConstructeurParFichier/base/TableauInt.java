package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base;

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
public class TableauInt<K extends Monteur<?>> implements Traitement<K> {
	/** Clé si elle existe */
	private String nomChamp;
	/** Valeurs lues */
	private int[] valeur;
	/** Opération de montage */
	private BiConsumer<K, int[]> operationDeMontage;
	
	/**
	 * Lit un tableau de valeurs avec une clé si elle est précisée
	 * @param nomChamp null si il n'y a pas de clé, la clé sinon
	 * @param operationDeMontage L'opération à appliquer une fois le tableau lu
	 */
	public TableauInt(String nomChamp, BiConsumer<K, int[]> operationDeMontage) {
		this.nomChamp = nomChamp;
		this.operationDeMontage = operationDeMontage;
	}
	
	@Override
	public Avancement traiter(String ligne) {
		String[] split = ligne.split(" ");
		
		int debut = 0;
		
		if (nomChamp != null) {
			if (split.length == 0 || !split[0].equals(nomChamp))
				return Avancement.Tuer;
			
			debut = 1;
		}
		
		valeur = new int[split.length - debut];
		
		for (int i = debut ; i != split.length ; i++) {
			valeur[i - debut] = Integer.parseInt(split[i]);
		}
		
		return Avancement.Suivant;
	}

	@Override
	public void appliquer(K monteur) {
		operationDeMontage.accept(monteur, valeur);
	}
	
	@Override
	public String toString() {
		return "TInt " + nomChamp;
	}
}
