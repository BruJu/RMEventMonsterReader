package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.List;

public class EvenementCommun implements ElementComposite<Instruction> {
	public final int id;
	public final String nom;
	public final int trigger;
	public final int variable;
	public final List<Instruction> instructions;
	
	public EvenementCommun(int id, String nom, int trigger, int variable) {
		this.id = id;
		this.nom = nom;
		this.trigger = trigger;
		this.variable = variable;
		this.instructions = new ArrayList<>();
	}

	@Override
	public void ajouter(Instruction t) {
		instructions.add(t);
	}
	
	public void viderCache() {
		instructions.clear();
	}
	

	public void append(StringBuilder sb) {
		sb.append("-- EVENT COMMUN --\n")
		  .append("ID ").append(id).append("\n")
		  .append("Nom ").append(nom).append("\n")
		  .append("Trigger ").append(trigger).append(" ").append(variable).append("\n")
		  .append("\n");
	}
}
