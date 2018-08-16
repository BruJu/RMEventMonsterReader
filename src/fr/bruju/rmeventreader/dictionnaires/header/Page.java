package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import org.w3c.dom.Node;

import fr.bruju.rmeventreader.dictionnaires.ExtractionXML;
import fr.bruju.rmeventreader.dictionnaires.UtilXML;

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

	public static Page instancier(Node node) {
		int id = UtilXML.getId(node);
		LinkedHashMap<String, int[]> map = new LinkedHashMap<>();

		Node condition = ExtractionXML.chercherFils(node, "condition");
		condition = ExtractionXML.chercherFils(condition, "EventPageCondition");

		Node flags = ExtractionXML.chercherFils(condition, "flags");
		flags = ExtractionXML.chercherFils(flags, "EventPageCondition_Flags");

		extraireID(map, condition, flags, new String[] { "switch_a", "switch_b", "item", "actor" }, "_id");
		extraireID(map, condition, flags, new String[] { "timer", "timer2" }, "_sec");
		
		if (ExtractionXML.extraireFils(flags, "variable").equals("T")) {
			map.put("variable", new int[] { Integer.parseInt(ExtractionXML.extraireFils(condition, "variable_id")),
					Integer.parseInt(ExtractionXML.extraireFils(condition, "variable_value")),
					Integer.parseInt(ExtractionXML.extraireFils(condition, "compare_operator"))
			});
		}
		
		return new Page(id, map);
	}

	private static void extraireID(LinkedHashMap<String, int[]> map, Node condition, Node flags, String[] cles,
			String suffixe) {
		for (String cle : cles) {
			if (ExtractionXML.extraireFils(flags, cle).equals("T")) {
				map.put(cle, new int[] { Integer.parseInt(ExtractionXML.extraireFils(condition, cle + suffixe)) });
			}
		}
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

