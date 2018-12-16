package fr.bruju.rmeventreader.implementation.monsterlist;

import fr.bruju.util.reconnaissancedimage.ChercheurDeMotifs;
import fr.bruju.util.reconnaissancedimage.MatricePixels;
import fr.bruju.util.reconnaissancedimage.Motif;
import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringJoiner;
import java.util.stream.Collectors;

/**
 * L'objectif de cette classe est de prendre une liste de monstres et de tenter de reconnaître leurs noms.
 * <p>
 * Pour cela, on suppose que le nom actuel du monstre est le nom de l'image où est écrit son nom.
 * <p>
 * <p>
 * Usage à partir d'une MonsterDatabase nommée db pour obtenir le fichier avec la liste des noms des monstres
 * <p>
 * <code>
 * List<String> nomDesImages = new ArrayList<>();
 * db.extractMonsters().forEach(m -> nomDesImages.add(m.getNom()));
 * BuildingMotifs chercheurDeMotifs = new BuildingMotifs(nomDesImages);
 * chercheurDeMotifs.lancer();
 * chercheurDeMotifs.getMap().forEach( (cle, valeur) -> System.out.println(cle + " " + valeur));
 * </code>
 */
public class ReconnaisseurDImages {
	/** Extension des fichiers image */
	private static final String CHEMIN_SUFFIXE = ".PNG";
	
	/** Chaines reconnues */
	private Map<String, String> chainesReconnues;
	
	/** Chaînes non reconnues */
	private StringJoiner fichiersIntrouvables = new StringJoiner(", ");
	private StringJoiner fichiersIOException = new StringJoiner(", ");
	private List<String> fichiersSymbolesManquants = new ArrayList<>();
	
	/** Dossier contenant les images */
	private final String dossier;
	
	/** Motifs connus */
	List<Motif> motifs;
	
	/** Liste des motifs non reconnus */
	List<Motif> motifsInconnus = new ArrayList<>();
	
	public ReconnaisseurDImages(String dossier) {
		this.dossier = dossier;
		chainesReconnues = new HashMap<>();
		motifs = listerLesMotifs();
	}

	/**
	 * Tente d'identifier les lettres écrites sur l'image
	 * @param nomImage Le nom de l'image
	 */
	public void identifier(String nomImage) {
		if (chainesReconnues.containsKey(nomImage)) {
			return;
		}
		
		String chemin = dossier + nomImage + CHEMIN_SUFFIXE;
		String nomIdentifie = null;
		
		MatricePixels matrice;

		try {
			matrice = MatricePixels.lireImage(chemin);
			nomIdentifie = new ChercheurDeMotifs(matrice, motifs, motifsInconnus).reconnaitre();
			
			if (nomIdentifie == null) {
				fichiersSymbolesManquants.add(matrice.getString());
			}
		} catch (FileNotFoundException e) {
			fichiersIntrouvables.add(nomImage);
		} catch (IOException e) {
			fichiersIOException.add(nomImage);
			
			e.printStackTrace();
		}

		chainesReconnues.put(nomImage, nomIdentifie);
	}
	
	/**
	 * Donne la liste de tous les noms identifiés avec le nom qui a été lu
	 * @return Une liste de toutes les images identifiés avec la chaîne lue
	 */
	public String getNomsIdentifies() {
		return chainesReconnues.entrySet()
							   .stream()
							   .filter(entree -> entree.getValue() != null)
							   .map(entree -> entree.getKey() + " " + entree.getValue())
							   .collect(Collectors.joining("\n"));
	}
	
	/**
	 * Donne une chaîne lisant toutes les erreurs lors de la lecture
	 * @return Une chaîne avec la liste des fichiers manquants, des fichiers en erreur et les fichiers dont il manque
	 * des symboles pour les reconnaître
	 */
	public String listeLesErreurs() {
		StringBuilder sb = new StringBuilder();
		
		if (fichiersIntrouvables.length() != 0) {
			sb.append("Fichiers Manquants : ").append(fichiersIntrouvables.toString());
		}

		if (fichiersIOException.length() != 0) {
			sb.append("Fichiers ayant jeté une exception : ").append(fichiersIOException.toString());
		}

		if (fichiersSymbolesManquants.size() != 0) {
			sb.append("Fichiers avec des symboles inconnus :\n");
			fichiersSymbolesManquants.forEach(image -> sb.append(image).append("\n"));
			
		}
		
		return sb.toString();
	}
	
	public List<Motif> getMotifsInconnus() {
		return motifsInconnus;
	}

	/* =================================
	 * IDENTIFICATION DE TOUS LES MOTIFS
	 * ================================= */

	/** Chemin vers la liste des motifs connus */
	private static final String CHEMIN_MOTIFS_CONNUS = "metaressources/ocr/motifsconnus.txt";

	/**
	 * Renvoie la liste des motifs déjà connus, qui sont dans metaressources/ocr/motifsconnus.txt
	 */
	static List<Motif> listerLesMotifs() {
		List<Motif> motifs = LecteurDeFichiersLigneParLigne.listerRessources(CHEMIN_MOTIFS_CONNUS, Motif::new);
		return motifs == null ? new ArrayList<>() : motifs;
	}

}
