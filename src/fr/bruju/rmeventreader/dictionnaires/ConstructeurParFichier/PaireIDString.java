package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

import java.util.function.BiConsumer;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;

public class PaireIDString<K extends Monteur<?>> implements Traitement<K> {
	private String nomChamp;
	private String valeur;
	private BiConsumer<K, String> operationDeMontage;
	
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
}
