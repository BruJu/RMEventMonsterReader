package fr.bruju.rmeventreader.implementation.monsterlist;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Function;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.Recognizer;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

// TODO : faire une mini réorganisation de cette classe

/**
 * Objet pour remplacer des données dans un monster par d'autres données
 * 
 * @author Bruju
 *
 */
public class Correspondeur {
	/** Correspondance chaîne trouvée - chaîne à remplacer */
	private Map<String, String> map = new HashMap<>();

	/**
	 * Lit un fichier pour remplir les correspondances
	 */
	public void lireFichier(File file) throws IOException {
		String pattern = "_ _";

		FileReaderByLine.lireLeFichier(file, line -> {
			List<String> chaines = Recognizer.tryPattern(pattern, line);

			map.put(chaines.get(0), chaines.get(1));
		});
	}

	/**
	 * Remplace les chaînes des monstres en appliquant les fonctions de recherche et de transformation données
	 * @param monstres
	 * @param search
	 * @param replace
	 */
	public void searchAndReplace(Collection<Monstre> monstres, Function<Monstre, String> search,
			BiConsumer<Monstre, String> replace) {
		for (Monstre monstre : monstres) {
			String id = search.apply(monstre);

			String valeur = map.get(id);

			if (valeur != null) {
				replace.accept(monstre, valeur);
			}
		}
	}

}
