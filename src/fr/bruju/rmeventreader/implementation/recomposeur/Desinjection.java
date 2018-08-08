package fr.bruju.rmeventreader.implementation.recomposeur;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.Condition;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Constante;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Entree;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurConstructeur;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Personnage;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.ConditionAffichable;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;

/**
 * Visiteur constructeur statefull
 * 
 * @author Bruju
 *
 */
public class Desinjection extends VisiteurConstructeur implements IncrementateurDeHeader {
	private Algorithme resultat;
	
	private boolean desinjectionProduite = false;
	private Map<Integer, Integer> listePaires;
	
	public Desinjection(Personnage personnageCible, Map<Integer, Integer> conditionsVerifiees, Algorithme algorithme) {
		listePaires = conditionsVerifiees;
		this.resultat = traiter(algorithme);
	}
	
	@Override
	public Algorithme getResultat() {
		return resultat;
	}
	
	@Override
	public GroupeDeConditions recupererGroupeDeConditions() {
		List<ConditionAffichable> liste = new ArrayList<>(1);
		liste.add(new Ciblage(desinjectionProduite));
		return new GroupeDeConditions(liste);
	}

	@Override
	protected Condition modifier(ConditionValeur element) {
		Integer variableGauche = Entree.extraire(element.gauche);
		Integer evaluationDroite = Constante.evaluer(element.droite);
		
		if (variableGauche == null || evaluationDroite == null) {
			return element;
		}
		
		Integer valeurVoulue = listePaires.get(variableGauche);
		
		if (valeurVoulue == null) {
			return null;
		}
		
		desinjectionProduite = true;
		return ConditionFixe.get(evaluationDroite.equals(valeurVoulue));
	}
	
	
	
	public static class Ciblage implements ConditionAffichable {
		public final boolean estCible;

		public Ciblage(boolean estCible) {
			this.estCible = estCible;
		}

		@Override
		public String getString() {
			return estCible ? "Ciblé" : "AoE";
		}
	}
}
