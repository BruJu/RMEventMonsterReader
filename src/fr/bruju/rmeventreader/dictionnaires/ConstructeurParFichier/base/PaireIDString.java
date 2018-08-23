package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base;

import java.util.function.BiConsumer;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.header.Monteur;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;

/**
 * Traitement exigeant l'arrivée d'un couple (clé valeur) dont la clé est imposée
 * @author Bruju
 *
 * @param <K>
 */
public class PaireIDString<K extends Monteur<?>> implements Traitement<K> {
	/** Clé attendue */
	private String nomChamp;
	/** Valeur lue */
	private String valeur;
	/** Opération de montage */
	private BiConsumer<K, String> operationDeMontage;
	
	/**
	 * Construit une lecture de paire clé valeur dont la clé est imposée
	 * @param nomChamp La clé
	 * @param operationDeMontage L'opération à appliquer sur le monteur avec la valeur reçue
	 */
	public PaireIDString(String nomChamp, BiConsumer<K, String> operationDeMontage) {
		this.nomChamp = nomChamp;
		this.operationDeMontage = operationDeMontage;
	}

	@Override
	public Avancement traiter(String ligne) {
		String[] split = FileReaderByLine.splitter(ligne, 2);
		
		if (split == null || !split[0].equals(nomChamp)) {
			return Avancement.Tuer;
		}
		
		valeur = split[1];
		
		return Avancement.Suivant;
	}

	@Override
	public void appliquer(K monteur) {
		operationDeMontage.accept(monteur, valeur);
	}
	
	@Override
	public String toString() {
		return "PaireIDChamp " + nomChamp;
	}
}
