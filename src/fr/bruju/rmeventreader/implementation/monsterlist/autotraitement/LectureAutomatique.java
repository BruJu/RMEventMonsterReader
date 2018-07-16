package fr.bruju.rmeventreader.implementation.monsterlist.autotraitement;

import java.io.File;
import java.io.IOException;

import fr.bruju.rmeventreader.filereader.ActionOnLine;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;

public class LectureAutomatique implements ActionAutomatique {
	private ActionOnLine correcteur; 
	private String filename;
	
	public LectureAutomatique(ActionOnLine correcteur, String filename) {
		this.correcteur = correcteur;
		this.filename = filename;
	}

	@Override
	public void faire() {
		try {
			FileReaderByLine.lireLeFichier(new File(filename), correcteur);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
