package fr.bruju.rmeventreader.dictionnaires.modele;

import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.dictionnaires.liblcfreader.LecteurDeCache;
import fr.bruju.rmeventreader.rmobjets.RMEvenement;
import fr.bruju.rmeventreader.rmobjets.RMEvenementCommun;
import fr.bruju.rmeventreader.rmobjets.RMInstruction;
import fr.bruju.rmeventreader.rmobjets.RMMap;
import fr.bruju.rmeventreader.rmobjets.RMPage;

/**
 * Classe contenant tous les adaptateurs entre les modeles construits par la lecture du cache xml et des abstractions
 * plus génériques.
 * <br>Les sous classes sont préfixées d'un $ car c'est la solution la plus simple pour avoir des noms différents, et
 * dans la mesure où ces classes ne sont utilisées qu'en temps que classes qu'elles implémentent, avoir un nom
 * fantaisiste ne pose pas de difficulté à l'utilisation.
 *
 */
class Adaptations {
	// TODO : les objets manipulés sont censés être immutabls. On pourrait utiliser cette propriété pour accélérer
	// certaines méthodes. Néanmoins, ces classes étant vouées à disparaître, ce n'est surement pas rentable de
	// le faire
	
	private Adaptations() {
	}
	
	static class $Map implements RMMap {
		private MapGeneral map;
		private List<RMEvenement> evenements;

		$Map(MapGeneral mapGeneral) {
			this.map = mapGeneral;
			definirEvenements();
		}
		
		private void definirEvenements() {
			evenements = map.evenements
							.stream()
							.map(idEvenement -> LecteurDeCache.getEvenement(map.map.id, idEvenement))
							.map(ev -> ev.getRMEvenement())
							.collect(Collectors.toList());
		}

		@Override
		public int id() {
			return map.map.id;
		}

		@Override
		public String nom() {
			return map.map.getNom();
		}

		@Override
		public List<RMEvenement> evenements() {
			return evenements;
		}
	}
	
	static class $EvenementCommun implements RMEvenementCommun {
		private EvenementCommun evenementCommun;

		$EvenementCommun(EvenementCommun evenementCommun) {
			this.evenementCommun = evenementCommun;
		}
		
		@Override
		public int id() {
			return evenementCommun.id;
		}

		@Override
		public String nom() {
			return evenementCommun.nom;
		}

		@Override
		public List<RMInstruction> instructions() {
			return evenementCommun.instructions
					.stream()
					.map(Instruction::getRMInstruction)
					.collect(Collectors.toList());
		}
	}
	
	
	static class $Page implements RMPage {
		private Page page;

		$Page(Page page) {
			this.page = page;
		}
		
		@Override
		public int id() {
			return page.id;
		}

		@Override
		public List<RMInstruction> instructions() {
			return page.instructions.stream().map(Instruction::getRMInstruction).collect(Collectors.toList());
		}
	}
	
	static class $Instruction implements RMInstruction {
		private Instruction instruction;

		$Instruction(Instruction instruction) {
			this.instruction = instruction;
		}

		@Override
		public int code() {
			return instruction.code;
		}

		@Override
		public String argument () {
			return instruction.string;
		}

		@Override
		public int[] parametres () {
			return instruction.parameters;
		}
	}
	
	static class $Evenement implements RMEvenement {
		private Evenement evenement;

		$Evenement(Evenement evenement) {
			this.evenement = evenement;
		}
		
		@Override
		public int id() {
			return evenement.id;
		}

		@Override
		public String nom() {
			return evenement.nom;
		}

		@Override
		public int x() {
			return evenement.x;
		}

		@Override
		public int y() {
			return evenement.y;
		}

		@Override
		public List<RMPage> pages() {
			return evenement.pages.stream().map(Page::getRMPage).collect(Collectors.toList());
		}
	}
	
}
