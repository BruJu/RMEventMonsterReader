package fr.bruju.rmeventreader.implementation.recomposeur.operations.desinjection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.Condition;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Constante;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Entree;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurConstructeur;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.ConditionAffichable;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.IncrementateurDeHeader;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Visiteur constructeur statefull
 * 
 * @author Bruju
 *
 */
public class Desinjection extends VisiteurConstructeur implements IncrementateurDeHeader {
	/* Résultats */
	private Algorithme resultat;
	private boolean desinjectionProduite;
	
	/* Traitement */
	private Map<Integer, Integer> listePaires;
	
	public Desinjection(Algorithme algorithme, Map<Integer, Integer> carte) {
		listePaires = carte;
		desinjectionProduite = false;
		this.resultat = traiter(algorithme);
		listePaires = null;
	}

	@Override
	public Iterator<Pair<GroupeDeConditions, Algorithme>> iterator() {
		return Utilitaire.toArrayList(new Pair<>(new GroupeDeConditions(new Ciblage(desinjectionProduite)), resultat)).iterator();
	}
	

	public Algorithme getResultat() {
		return this.resultat;
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
			return element;
		}
		
		desinjectionProduite = true;
		return ConditionFixe.get(evaluationDroite.equals(valeurVoulue));
	}




}
