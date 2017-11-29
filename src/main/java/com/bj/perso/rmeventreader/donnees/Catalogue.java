package com.bj.perso.rmeventreader.donnees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.bj.perso.rmeventreader.instructions.InstructionIf;

import utility.Pair;

/**
 * Classe stoquant une liste d'enregistrements
 */
public class Catalogue {
	/**
	 * La liste des enregistrements
	 */
	private List<Enregistrement> enregistrements;
	
	/**
	 * Crée un nouveau catalogue vierge
	 */
	public Catalogue() {
		enregistrements = new ArrayList<>();
	}
	
	/**
	 * Sépare le catalogue en deux catalogues.
	 * L'un vérifie la condition, l'autre non.
	 * @param L'instruction condition qui doit être testée
	 * @return Une paire de deux catalogues
	 */
	public Pair<Catalogue, Catalogue> split(InstructionIf instruction) {
		Catalogue catalogueIf = new Catalogue();
		Catalogue catalogueElse = new Catalogue();
		
		for (Enregistrement enregistrement : enregistrements) {
			instruction.repart(catalogueIf, catalogueElse, enregistrement);
		}
		
		return new Pair<>(catalogueIf, catalogueElse);
	}
	
	/**
	 * Crée un nouveau catalogue à partir des deux catalogues donnés
	 * @param catalogueLeft Un catalogue
	 * @param catalogueRight Un catalogue
	 * @return Un catalogue contenant les entrées des deux catalogues
	 */
	public static Catalogue fuse(Catalogue catalogueLeft, Catalogue catalogueRight) {
		Catalogue catalogue = new Catalogue();
		
		catalogue.fuseInto(catalogueLeft.enregistrements, catalogueRight.enregistrements);
		
		return catalogue;
	}

	/**
	 * Assemble les deux liste d'enregitrements dans ce catalogue
	 * @param enrLeft Une liste d'enregistrements
	 * @param enrRight Une liste d'enregistrements
	 */
	private void fuseInto(List<Enregistrement> enrLeft, List<Enregistrement> enrRight) {
		enregistrements.addAll(enrLeft);
		enregistrements.addAll(enrRight);
		
		enrLeft.clear();
		enrRight.clear();
		
		sort();
		combineDoubles();
	}


	/**
	 * Combine les enregistremetns dont l'id apparait deux fois dans le catalogue
	 */
	private void combineDoubles() {
		if (enregistrements.size() < 2)
			return;
		
		Enregistrement actuel = enregistrements.get(0);
		Enregistrement suivant;
		
		int i = 0;
		
		while (i != enregistrements.size() - 1) {
			suivant = enregistrements.get(i);
			
			if (actuel.fuse(suivant)) {
				enregistrements.remove(suivant);
			} else {
				actuel = suivant;
				i++;
			}
		}
	}

	/**
	 * Trie les enregistrements du catalogue par ID
	 */
	private void sort() {
		enregistrements.sort(new Comparator<Enregistrement>() {
			@Override
			public int compare(Enregistrement e1, Enregistrement e2) {
				return e1.compare(e2);
			}
		});
	}
}
