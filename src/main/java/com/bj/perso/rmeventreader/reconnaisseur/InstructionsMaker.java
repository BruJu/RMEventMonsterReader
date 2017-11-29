package com.bj.perso.rmeventreader.reconnaisseur;

public enum InstructionsMaker {
	// Objets
	VOID(
			InstructionType.Void,
			"_ et _",
			new DataType[] {DataType.IGNORE, DataType.IGNORETWO}),
	CHANGESWITCH(
			InstructionType.ChgSwitch,
			"<> Change Switch: [_] = _",
			new DataType[] {DataType.ID, DataType.VALUE}),
	CHANGEVARIABLE(
			InstructionType.ChgVariable,
			"<> Change Variable: [_] _ _\"",
			new DataType[] {DataType.ID, DataType.OPERATOR, DataType.VALUE}),
	CHANGEITEM(
			InstructionType.ChgItem,
			"<> Change Items: _ _ piece of item #_",
			new DataType[] {DataType.VERBACTION, DataType.VALUE, DataType.ID}),
	LABEL(
			InstructionType.Label,
			"<> Label: _",
			new DataType[] {DataType.LABELNAME}),
	JUMPTOLABEL(
			InstructionType.Jump,
			"Jump To Label: _",
			new DataType[] {DataType.LABELNAME}),
	FORK(
			InstructionType.Fork,
			"<> Fork Condition: If _ [_] _ _ _ £", // TODO
			new DataType[] {DataType.ID, DataType.OPERATION, DataType.IGNORE, DataType.IGNORE, DataType.IGNORE}),
	LOOP(
			InstructionType.Loop,
			"<> Loop",
			new DataType[] {}),
	BREAKLOOP(
			InstructionType.BreakLoop,
			"<> Break Loop",
			new DataType[] {}),
	SHOWPIC(
			InstructionType.ShowPicture,
			"<> Show Picture: #_, _, £",
			new DataType[] {DataType.TOBEFILLED, DataType.TOBEFILLED}),
	ENDFORK(
			InstructionType.EndFork,
			": End of fork",
			new DataType[] {}),
	ENDLOOP(
			InstructionType.EndLoop,
			": End of loop",
			new DataType[] {}),
	ELSE(
			InstructionType.Else,
			": Else ...",
			new DataType[] {}),
	EMPTY(
			InstructionType.Void,
			"<>",
			new DataType[] {}),
	SETEVENTLOCATION(
			InstructionType.Void,
			"<> Set Event Location£",
			new DataType[] {}),
	CHANGEPANORAMA(
			InstructionType.Void,
			"<> Change Panorama£",
			new DataType[] {}),
	PLAYBGM(
			InstructionType.Void,
			"<> Play BGM:£",
			new DataType[] {}),
	ERASEPICTURE(
			InstructionType.Void,
			"<> ErasePicture:£",
			new DataType[] {DataType.TOBEFILLED, DataType.TOBEFILLED}),
	SETSCREENTONE(
			InstructionType.Void,
			"<> Set Screen Tone:£",
			new DataType[] {})
	;
	
	
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
