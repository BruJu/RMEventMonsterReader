package fr.bruju.rmeventreader.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReaderByLine {
	private static final String COMMENTAIRE_STARTS_WITH = "//";
	
	public static void lireLeFichier(File file, ActionOnLine actionOnLine) throws IOException {
		FileReader fileReader = new FileReader(file);
		BufferedReader buffer = new BufferedReader(fileReader);
		String line;

		while (true) {
			line = buffer.readLine();

			if (line == null) {
				break;
			}

			if (!line.equals("")) {
				actionOnLine.read(line);
			}
		}

		buffer.close();
	}
	

	public static void lireLeFichierSansCommentaires(String chemin, ActionOnLine actionOnLine) throws IOException {
		FileReader fileReader = new FileReader(new File(chemin));
		BufferedReader buffer = new BufferedReader(fileReader);
		String line;

		while (true) {
			line = buffer.readLine();

			if (line == null) {
				break;
			}

			if (!line.equals("") && !line.startsWith(COMMENTAIRE_STARTS_WITH)) {
				actionOnLine.read(line);
			}
		}

		buffer.close();
	}
	
	
	public static List<String[]> lireFichier(String chemin, int nbArguments) throws IOException {
		List<String[]> valeursLues = new ArrayList<>();
		
		lireLeFichierSansCommentaires(chemin, donnee -> {
			String[] split = donnee.split(" ");
			
			if (split.length != nbArguments) {
				throw new LigneNonReconnueException("Fichier non valide " + donnee);
			}
			
			valeursLues.add(split);
		});
		
		return valeursLues;
	}
}
