package fr.bruju.rmeventreader.implementation.random;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.Explorateur;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecFlot;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Liste les appels à des évènements communs dans les évènements communs
 * 
 * @author Bruju
 *
 */
public class AppelsDEvenements implements Runnable {
	/** Association evenement commun - liste des evenements communs appelés et leur nombre */
	private Map<Integer, TreeMap<Integer, Integer>> appels;
	
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
	
	
	/**
	 * Exécuteur qui parcours un évènement commun et liste le nombre d'appels à chaque autre évènement commun.
	 * 
	 * @author Bruju
	 *
	 */
	public class Exec implements ExecuteurInstructionsTrue, ModuleExecFlot {
		/** ID de l'évènement */
		public final int id;
		
		/** Crée un exécuteur pour l'évènement commun donné */
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
