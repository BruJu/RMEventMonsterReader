package fr.bruju.rmeventreader.dictionnaires.header;

public class Instruction {
	public final int code;
	public final String string;
	public final int[] parameters;
	
	public Instruction(int code, String string, int[] parameters) {
		this.code = code;
		this.string = string;
		this.parameters = parameters;
	}
	

	
	public String toLigne() {
		if (code == 10) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(code).append(" ");
		
		for(int v : parameters) {
			sb.append(v).append(" ");
		}
		
		sb.append("; ").append(string).append("\n");
		
		return sb.toString();
	}
}
