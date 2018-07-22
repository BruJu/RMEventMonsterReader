package fr.bruju.rmeventreader.implementation.formulatracker.actionmaker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;

public class EtatMemoire {
	/* ==================
	 * ETAT DE LA MEMOIRE
	 * ================== */
	
	private EtatMemoire pere;
	
	private Map<Integer, Valeur> variables;
	private Map<Integer, Bouton> interrupteurs;
	
	private Condition condition = null;
	private EtatMemoire filsGauche = null;
	private EtatMemoire filsDroit = null;
	
	public EtatMemoire(Map<Integer, Valeur> variablesExistantes, Map<Integer, Bouton> interrupteursExistants) {
		this.variables = variablesExistantes;
		this.interrupteurs = interrupteursExistants;
		this.pere = null;
	}

	public EtatMemoire(EtatMemoire pere) {
		this.pere = pere;
		this.variables = new HashMap<>();
		this.interrupteurs = new HashMap<>();
	}


	/* =================
	 * STRUCTURE D'ARBRE
	 * ================= */
	
	public EtatMemoire creerFils(Condition condition) {
		this.condition = condition;
		filsGauche = new EtatMemoire(this);
		filsDroit = new EtatMemoire(this);
		
		return filsGauche;
	}
	
	/**
	 * Permet d'accéder au petit frère (fils droit du père) de cet état.
	 * 
	 * @return this.pere.filsDroit
	 */
	public EtatMemoire getPetitFrere() {
		return this.pere.filsDroit;
	}
	
	
	
	/* ==================
	 * ACCES A LA MEMOIRE
	 * ================== */

	public Valeur getVariable(Integer idVariable) {
		return extraireDonnee(this, idVariable, etat -> etat.variables, numero -> new VBase(numero));
	}

	public Bouton getInterrupteur(int idSwitch) {
		return extraireDonnee(this, idSwitch, etat -> etat.interrupteurs, numero -> new BBase(numero));
	}
	
	private static <T> T extraireDonnee(EtatMemoire courant, Integer numero,
			Function<EtatMemoire, Map<Integer, T>> fonctionDacces,
			Function<Integer, T> fonctionDeCreation) {
		T donnee;
		while (true) {
			donnee = fonctionDacces.apply(courant).get(numero);
			
			if (donnee == null) {
				if (courant.pere == null) {
					fonctionDacces.apply(courant).put(numero, fonctionDeCreation.apply(numero));
				} else {
					courant = courant.pere;
				}
			} else {
				return donnee;
			}
		}
	}

	public EtatMemoire revenirAuPere() {
		return null;
	}

	public List<Condition> construireListeDeConditions() {
		return null;
	}

	public void affecterVariable(Integer variable, Operator operator, Valeur vDroite) {
		
	}




	
	
	
	
}
