package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class Page implements ElementComposite<Instruction> {
	public final int id;
	public final LinkedHashMap<String, int[]> conditions;
	public final List<Instruction> instructions;

	public Page(int id, LinkedHashMap<String, int[]> conditions) {
		this.id = id;
		this.conditions = conditions;
		this.instructions = new ArrayList<>();
	}

	@Override
	public void ajouter(Instruction t) {
		this.instructions.add(t);
	}

	
	public void append(StringBuilder sb) {
		sb.append("= Page =\n");
		sb.append("id ").append(id).append("\n");
		conditions.forEach((nom, tableau) -> {
			sb.append(nom);

			for (int valeur : tableau) {
				sb.append(" ").append(valeur);
			}
			sb.append("\n");
		});
		sb.append("\n");
		sb.append("- Instructions -\n");
		instructions.forEach(instruction -> sb.append(instruction.toLigne()));
		sb.append("\n\n");
	}

}

