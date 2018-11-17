package fr.bruju.rmeventreader.implementation.random;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.Condition;
import fr.bruju.util.MapsUtils;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Liste les appels à des évènements communs dans les évènements communs
 * 
 * @author Bruju
 *
 */
public class AppelsDEvenements implements Runnable {
	public static final int EC_CHECK_ELEM = 277;
	public static final int EC_CHECK_RELATIONS = 232;
	/** Association evenement commun - liste des evenements communs appelés et leur nombre */
	private Map<Integer, TreeMap<Integer, Integer>> appels;
	
	@Override
	public void run() {
		appels = new HashMap<>();
		
		PROJET.explorerEvenementsCommuns(ec -> PROJET.executer(new Exec(ec.id()), ec.instructions()));
		
		System.out.println("Evenement Commun ; Nombre d'appels à 277 Check Elem ; Nombre d'appels à 232 Check Relation");
		appels.entrySet()
			  .stream()
			  .sorted(Comparator.comparingInt(Map.Entry::getKey))
			  .filter(entry -> entry.getValue().get(EC_CHECK_ELEM) != null
					  || entry.getValue().get(EC_CHECK_RELATIONS) != null)
			  .map(entry -> entry.getKey()
					  + " ; " + entry.getValue().getOrDefault(EC_CHECK_ELEM, 0)
					  + " ; " + entry.getValue().getOrDefault(EC_CHECK_RELATIONS, 0))
			  .forEach(System.out::println);
	}
	
	
	/**
	 * Exécuteur qui parcours un évènement commun et liste le nombre d'appels à chaque autre évènement commun.
	 * 
	 * @author Bruju
	 *
	 */
	private class Exec implements ExecuteurInstructions {
		/** ID de l'évènement */
		public final int id;
		
		/** Crée un exécuteur pour l'évènement commun donné */
		public Exec(int id) {
			this.id = id;
		}

		@Override
		public void Flot_appelEvenementCommun(int numero) {
			MapsUtils
				.getX(appels, id, TreeMap::new)
				.compute(numero, (cle, ex) -> ex == null ? 1 : ex + 1);
		}

		@Override
		public int Flot_si(Condition condition) {
			if (condition instanceof Condition.CondObjet) {
				if (((Condition.CondObjet) condition).idObjet == 1) {
					return 3;
				}
			}
			
			return 0;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}
	}
	
}
