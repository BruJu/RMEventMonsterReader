package fr.bruju.rmeventreader.dictionnaires.modele;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.ConvertisseurLigneVersObjet;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.AssociationStringInt;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.Instr;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.LigneAttendue;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.TableauInt;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.decorateur.BoucleTraitement;
import fr.bruju.rmeventreader.rmobjets.RMPage;

public class Page implements ElementComposite<Instruction> {
	public final int id;
	public final LinkedHashMap<String, int[]> conditions;
	public final List<Instruction> instructions;

	public Page(int id, LinkedHashMap<String, int[]> conditions) {
		this.id = id;
		this.conditions = conditions;
		this.instructions = new ArrayList<>();
	}
	
	private Page(int id) {
		this.id = id;
		this.conditions = new LinkedHashMap<>();
		this.instructions = new ArrayList<>();
	}
	
	public RMPage getRMPage() {
		return new Adaptations.$Page(this);
	}

	
	public static Page creerPageSimple() {
		return new Page(1);
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
	
	@SuppressWarnings("unchecked")
	public static ConvertisseurLigneVersObjet<Page, Builder> sousObjet() {
		return new ConvertisseurLigneVersObjet<>(new Builder(), new Traitement[] {
				new LigneAttendue<>("= Page ="),
				new TableauInt<Builder>("id", (m, t) -> m.setId(t[0])),
				new BoucleTraitement<Builder>(() ->
					new AssociationStringInt<Builder>((m, cle, valeur) ->
						m.ajouterCond(cle, valeur)), "- Instructions -"),
				
				new LigneAttendue<>("- Instructions -"),
				new BoucleTraitement<Builder>(() ->
					new Instr<Builder>((m, i) -> m.ajouterInstruction(i)), "= Page =")
		});
	}

	
	public static class Builder implements Monteur<Page> {
		private Page page;
		
		@Override
		public Page build() {
			return page;
		}

		public Builder ajouterInstruction(Instruction i) {
			page.instructions.add(i);
			return this;
		}

		public Builder ajouterCond(String cle, int[] valeur) {
			page.conditions.put(cle, valeur);
			return this;
		}

		public Builder setId(int id) {
			page = new Page(id, new LinkedHashMap<>());
			return this;
		}
		
		
		
	}
}

