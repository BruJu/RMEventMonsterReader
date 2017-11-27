package com.bj.perso.rmeventreader.reconnaisseur;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import utility.Pair;

public enum InstructionsMaker {
	// Objets
	VOID(InstructionType.InstrVoid, "_", new DataType[] {DataType.IGNORE});
	
	
	// Attributs
	private Pattern pattern = null;
	private InstructionType instruction;
	private DataType[] data;
	
	// Constructeur
	InstructionsMaker(InstructionType instruction, String pattern, DataType[] data) {
		this.instruction = instruction;
		this.pattern = Pattern.compile(filtrer(pattern));
		this.data = data;
	}

	private String filtrer(String pattern2) {
		// TODO : enlever toute trace de regex dans la string et permettre au symbole _ de capturer les chaines
		return pattern2;
	}
	
	public InstructionType getInstruction() {
		return instruction;
	}
	
	public Pattern getPattern() {
		return pattern;
	}
	
	public DataType[] getDataTypes() {
		return data;
	}
	
	public List<Pair<DataType, String>> getValues(List<String> data) {
		// Renvoie une paire parce qu'il est ridicule de faire un HashMap d'au pire 10 éléments
		return null;
		
		
		
	}
}
