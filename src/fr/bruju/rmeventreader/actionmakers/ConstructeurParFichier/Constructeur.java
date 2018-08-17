package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

import java.io.IOException;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.utilitaire.Container;

public class Constructeur {
	public static <T, K extends Monteur<T>> T construire(String chemin, K monteur, Traitement<K>[] traitements) {
		Container<Integer> i = new Container<>();
		i.item = 0;
		
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(chemin, ligne -> {
				if (i.item == traitements.length) {
					throw new LigneNonReconnueException("Fichier non conforme");
				}
				
				Avancement avancement;
				
				while (true) {
					avancement = traitements[i.item].traiter(ligne);
					
					if (avancement == Avancement.Tuer) {

						System.out.println("4 " + i.item);
						throw new LigneNonReconnueException("Fichier non conforme");
					}
					
					if (avancement != Avancement.SuivantDirect) {
						break;
					}
				}
				
				if (avancement == Avancement.Suivant) {
					i.item++;
				}
			});
		} catch (IOException | LigneNonReconnueException e) {
			return null;
		}
		
		if (i.item + 1 != traitements.length) {
			return null;
		}

		for (Traitement<K> traitement : traitements) {
			traitement.appliquer(monteur);
		}
		
		return monteur.build();
	}
}
