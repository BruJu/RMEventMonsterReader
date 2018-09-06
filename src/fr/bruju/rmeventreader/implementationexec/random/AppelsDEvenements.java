package fr.bruju.rmeventreader.implementationexec.random;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Explorateur;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecFlot;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class AppelsDEvenements implements Runnable {
	Map<Integer, TreeMap<Integer, Integer>> appels;
	
	@Override
	public void run() {
		appels = new HashMap<>();
		
		Explorateur.explorer(ec -> Explorateur.executer(new Exec(ec.id), ec.instructions), null);
		
		System.out.println("Evenement Commun ; Nombre d'appels à 277 Check Elem ; Nombre d'appels à 232 Check Relation");
		appels.entrySet()
			  .stream()
			  .sorted((entry1, entry2) -> Integer.compare(entry1.getKey(), entry2.getKey()))
			  .filter(entry -> entry.getValue().get(277) != null || entry.getValue().get(232) != null)
			  .map(entry -> entry.getKey()
					  + " ; " + entry.getValue().getOrDefault(277, 0)
					  + " ; " + entry.getValue().getOrDefault(232, 0))
			  .forEach(System.out::println);
	}
	
	
	public class Exec implements ExecuteurInstructionsTrue, ModuleExecFlot {
		public final int id;
		
		public Exec(int id) {
			this.id = id;
		}

		@Override
		public void Flot_appelEvenementCommun(int numero) {
			Utilitaire.Maps
				.getX(appels, id, () -> new TreeMap<>())
				.compute(numero, (cle, ex) -> ex == null ? 1 : ex + 1);
		}

		@Override
		public boolean Flot_si(Condition condition) {
			if (condition instanceof Condition.CondObjet) {
				if (((Condition.CondObjet) condition).idObjet == 1) {
					return false;
				}
			}
			
			return true;
		}

		@Override
		public ModuleExecFlot getExecFlot() {
			return this;
		}
	}
	
}
