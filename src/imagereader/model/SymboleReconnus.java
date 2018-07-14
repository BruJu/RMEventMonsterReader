package imagereader.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Cette classe récupère la liste des motifs à reconnaître.
 */
public class SymboleReconnus {
	/**
	 * Renvoie la liste des motifs déjà connus, qui sont dans metaressources/ocr/motifsconnus.txt
	 */
	public List<Motif> getMotifs() {
		FileReader fileReader;
		try {
			fileReader = new FileReader("metaressources/ocr/motifsconnus.txt");
			BufferedReader buffer = new BufferedReader(fileReader);
			
			List<Motif> motifs = new ArrayList<>();
			
			Scanner scanner;
			
			while (true) {
				String line = buffer.readLine();
				
				if (line == null) {
					break;
				}
				
				scanner = new Scanner(line);
				
				String chaine = scanner.next();
				
				
				List<Integer> valeursMotif = new ArrayList<>();
				
				while (scanner.hasNextInt()) {
					valeursMotif.add(scanner.nextInt());
				}
				
				int[] valeurs = new int[valeursMotif.size()];
				for (int i = 0 ; i != valeursMotif.size(); i++) {
					valeurs[i] = valeursMotif.get(i);
				}
				
				
				motifs.add(new Motif(chaine, valeurs));
			}
			
			buffer.close();
			return motifs;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}
