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
	
	
	public static class Builder implements Monteur<EvenementCommun> {
		public int id;
		public String nom;
		public int trigger;
		public int variable;
		
		private EvenementCommun evenement;
		
		public Builder setId(int id) {
			this.id = id;
			return this;
		}

		public Builder setNom(String nom) {
			this.nom = nom;
			return this;
		}
		
		public Builder setTrigger(int trigger) {
			this.trigger = trigger;
			return this;
		}
		
		public Builder setVariable(int variable) {
			this.variable = variable;
			return this;
		}
		
		public Builder ajouterInstruction(Instruction instruction) {
			if (evenement == null) {
				evenement = new EvenementCommun(id, nom, trigger, variable);
			}
			
			evenement.ajouter(instruction);
			
			return this;
		}

		@Override
		public EvenementCommun build() {
			return evenement;
		}
	}
}
