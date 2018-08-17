package fr.bruju.rmeventreader.dictionnaires.lecteurs;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.header.EvenementCommun;
import fr.bruju.rmeventreader.dictionnaires.header.Instruction;
import fr.bruju.rmeventreader.filereader.ActionOnLine;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class LecteurEC implements ActionOnLine {	
	private ActionOnLine etat = this::base;
	
	private int id;
	private String nom;
	private int trigger;
	private int triggerVariable;
	
	private EvenementCommun ec;
	
	public EvenementCommun get() {
		return ec;
	}
	
	
	@Override
	public void read(String ligne) {
		try {
			etat.read(ligne);
		} catch (LigneNonReconnueException e) {
			ec = null;
			etat = this::dead;
		}
	}
	
	private void dead(String ligne) {
	}
	
	private void base(String ligne) {
		if (ligne.equals("-- EVENT COMMUN --")) {
			etat = this::lireId;
		} else {
			throw new LigneNonReconnueException("");
		}
	}
	

	private void lireId(String ligne) {
		String[] data = FileReaderByLine.splitter(ligne, 2);
		
		if (!data[0].equals("ID"))
			throw new LigneNonReconnueException("");
		
		id = Integer.parseInt(data[1]);
		etat = this::lireNom;
	}
	

	private void lireNom(String ligne) {
		String[] data = FileReaderByLine.splitter(ligne, 2);
		
		if (!data[0].equals("Nom"))
			throw new LigneNonReconnueException("");
		
		nom = data[1];
		etat = this::lireTrigger;
	}

	private void lireTrigger(String ligne) {
		String[] data = FileReaderByLine.splitter(ligne, 3);
		
		if (!data[0].equals("Trigger"))
			throw new LigneNonReconnueException("");

		trigger = Integer.parseInt(data[1]);
		triggerVariable = Integer.parseInt(data[2]);
		etat = this::lireInstructions;
	}
	

	private void lireInstructions(String ligne) {
		if (ligne.equals("- Instructions -")) {
			this.ec = new EvenementCommun(id, nom, trigger, triggerVariable);
			etat = this::lireInstruction;
		} else {
			throw new LigneNonReconnueException("");
		}
	}
	

	private void lireInstruction(String ligne) {
		String[] d = ligne.split(" ");
		
		int code = Integer.parseInt(d[0]);
		String string = ligne.substring(ligne.indexOf(";") + 2 , ligne.length());
		
		List<Integer> params = new ArrayList<>();
		
		for (int i = 1 ; i != d.length ; i++) {
			String s = d[i];
			
			if (s.equals(";"))
				break;
			
			params.add(Integer.parseInt(s));
		}
		
		Instruction ins = new Instruction(code, string, Utilitaire.toArrayInt(params));
		ec.ajouter(ins);
	}
}