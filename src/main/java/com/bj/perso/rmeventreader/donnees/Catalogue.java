package com.bj.perso.rmeventreader.donnees;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.bj.perso.rmeventreader.instructions.InstructionIf;

import utility.Pair;

public class Catalogue {
	private List<Enregistrement> enregistrements;
	
	public Catalogue() {
		enregistrements = new ArrayList<>();
	}
	
	public Pair<Catalogue, Catalogue> split(InstructionIf instruction) {
		Catalogue catalogueIf = new Catalogue();
		Catalogue catalogueElse = new Catalogue();
		
		for (Enregistrement enregistrement : enregistrements) {
			instruction.repart(catalogueIf, catalogueElse, enregistrement);
		}
		
		return new Pair<>(catalogueIf, catalogueElse);
	}
	
	public static Catalogue fuse(Catalogue catalogueLeft, Catalogue catalogueRight) {
		Catalogue catalogue = new Catalogue();
		
		catalogue.fuseInto(catalogueLeft.enregistrements, catalogueRight.enregistrements);
		
		return catalogue;
	}

	private void fuseInto(List<Enregistrement> enrLeft, List<Enregistrement> enrRight) {
		enregistrements.addAll(enrLeft);
		enregistrements.addAll(enrRight);
		
		enrLeft.clear();
		enrRight.clear();
		
		sort();
		combineDoubles();
	}


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

	private void sort() {
		enregistrements.sort(new Comparator<Enregistrement>() {
			@Override
			public int compare(Enregistrement e1, Enregistrement e2) {
				return e1.compare(e2);
			}
		});
	}
}
