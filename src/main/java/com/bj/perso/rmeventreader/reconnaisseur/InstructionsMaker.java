package com.bj.perso.rmeventreader.reconnaisseur;

public enum InstructionsMaker {
	// Objets
	VOID(InstructionType.InstrVoid, "_ et _", new DataType[] {DataType.IGNORE, DataType.IGNORETWO});
	
	// Attributs
	private String pattern = null;
	private InstructionType instruction;
	private DataType[] data;

	
	// Constructeur
	InstructionsMaker(InstructionType instruction, String pattern, DataType[] data) {
		this.instruction = instruction;
		this.pattern = pattern;
		this.data = data;
	}
	
	public InstructionType getInstruction() {
		return instruction;
	}
	
	public String getPattern() {
		return pattern;
	}
	
	public DataType[] getDataTypes() {
		return data;
	}
}
