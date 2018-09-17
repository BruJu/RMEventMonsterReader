package fr.bruju.rmeventreader.dictionnaires.modele;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.ConvertisseurLigneVersObjet;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.Instr;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.LigneAttendue;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.PaireIDString;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.TableauInt;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.decorateur.BoucleTraitement;
import fr.bruju.rmeventreader.rmobjets.RMEvenementCommun;

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
	
	
	public RMEvenementCommun getRMEvenementCommun() {
		return new Adaptations.$EvenementCommun(this);
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
	
	@SuppressWarnings("unchecked")
	public static ConvertisseurLigneVersObjet<EvenementCommun, Builder> sousObjet() {
		return new ConvertisseurLigneVersObjet<>(new Builder(), new Traitement[] {
				new LigneAttendue<>("-- EVENT COMMUN --"),
				new TableauInt<EvenementCommun.Builder>("ID", (m, t) -> m.setId(t[0])),
				new PaireIDString<EvenementCommun.Builder>("Nom", (m, s) -> m.setNom(s)),
				new TableauInt<EvenementCommun.Builder>("Trigger", (m, t) -> m.setTrigger(t[0]).setVariable(t[1])),
				new LigneAttendue<>("- Instructions -"),
				new BoucleTraitement<EvenementCommun.Builder>(() -> new Instr<>((m, i) -> m.ajouterInstruction(i)))
			});
	}
	
	public static class Builder implements Monteur<EvenementCommun> {
		private int id;
		private String nom;
		private int trigger;
		private int variable;
		
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
			if (evenement == null) {
				evenement = new EvenementCommun(id, nom, trigger, variable);
			}
			
			return evenement;
		}
	}
}
