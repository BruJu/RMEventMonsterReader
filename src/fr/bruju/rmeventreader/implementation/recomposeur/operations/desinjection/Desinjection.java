package fr.bruju.rmeventreader.implementation.recomposeur.operations.desinjection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.Condition;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.actionmakers.composition.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Constante;
import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Entree;
import fr.bruju.rmeventreader.actionmakers.composition.visiteur.template.VisiteurConstructeur;
import fr.bruju.rmeventreader.implementation.recomposeur.Parametres;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Personnage;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.ConditionAffichable;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.Header;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.IncrementateurDeHeader;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.StructureDInjectionDeHeader;

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
	private Header nouveauHeader;
	
	public Desinjection(Header header, Algorithme algorithme, Map<Integer, Integer> carte) {
		listePaires = carte;
		
		this.resultat = traiter(algorithme);
		this.nouveauHeader = new Header(header, recupererGroupeDeConditions());
	}
	
	@Override
	public Algorithme getResultat() {
		return resultat;
	}
	
	private GroupeDeConditions recupererGroupeDeConditions() {
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
	
	
	


	@Override
	public Header getHeader() {
		return nouveauHeader;
	}
}
