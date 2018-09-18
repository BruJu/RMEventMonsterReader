package fr.bruju.rmeventreader.dictionnaires.modele;

import fr.bruju.lcfreader.rmobjets.RMInstruction;

public class Instruction {
	public final int code;
	public final String string;
	public final int[] parameters;
	
	public Instruction(int code, String string, int[] parameters) {
		this.code = code;
		this.string = string;
		this.parameters = parameters;
	}


	public RMInstruction getRMInstruction() {
		return new Adaptations.$Instruction(this);
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		sb.append(code)
		  .append(" ");
		
		if (parameters != null && parameters.length != 0) {
			for (int valeur : parameters) {
				sb.append(valeur).append(" ");
			}
		}
		
		sb.append("; ").append(string);
		
		return sb.toString();
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
		sb.append("; ").append(string);

		String chaine = sb.toString();
		
		return chaine.replace("\n", "").replaceAll("\r", "") + "\n";
	}

	public String toString(boolean b) {
		if (code == 10) {
			return "";
		}
		
		StringBuilder sb = new StringBuilder();
		
		sb.append(code).append(" ");
		
		for (int i = 0 ; i != parameters.length ; i++) {
			sb.append("[").append(i).append("]=").append(parameters[i]).append(" ");
		}
		
		sb.append("; ").append(string);
		
		String chaine = sb.toString();
		
		return chaine.replace("\n", "").replaceAll("\r", "").replaceAll("\r", "");
	}
}
