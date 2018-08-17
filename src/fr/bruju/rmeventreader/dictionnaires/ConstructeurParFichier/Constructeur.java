package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

import java.io.IOException;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;

public class Constructeur {
	public static <T, K extends Monteur<T>> T construire(String chemin, SousObject<T, ?> ss) {
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(chemin, ss);
		} catch (IOException | LigneNonReconnueException e) {
			return null;
		}

		return ss.build();
	}
	
	
	public static <K extends Monteur<?>, T> void throwAway(K m, T t) {
	}
}
