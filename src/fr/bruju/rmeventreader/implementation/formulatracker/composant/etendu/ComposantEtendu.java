package fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

public interface ComposantEtendu extends Composant {

	/* ========
	 * VISITEUR
	 * ======== */

	@Override
	public default void accept(VisiteurDeComposants visiteurDeComposants) {
		getComposantNormal().accept(visiteurDeComposants);
	}
	

	/* =========
	 * EXTENSION
	 * ========= */

	public Composant getComposantNormal();
}

/*
• En cas d'ajout d'un composant étendu •

Le but de cette implémentation est de pouvoir conserver les anciens visiteurs sans les modifier lorsqu'un nouveau
composant étendu est ajouté.
getComposantNormal() donne une forme dite normale du composant qui est constuite avec uniquement des composants non
étendus. Lorsqu'un visiteur visite un composant étendu dont il n'a pas prévu le comportement, il demane la forme
normale et applique son traitement dessus.

 == VISITEUR DE COMPOSANTS ==
 Ajouter
 
 	default void visit(E_NouveauComposantEtendu composant) {
		visiterComposantNormal(composant);
	}

-> Ajoute un visiteur pour ce composant étendu afin que les anciens visiteurs puissent le manipuler enr retombant sur
la méthode par défaut.

== VISITEUR RETOURNEUR ==

	@Override
	public void visit(E_BorneSuperieure composant) {
		this.composant = traiter(composant);
	}

	protected Intermediaire traiter(E_BorneSuperieure composant) {
		return composantEtenduNonGere(composant);
	}
	
-> permet à traiter(Composant composant) de récupérer le résultat et de le renvoyer
-> Permet d'appeler le traitement du composant normalisé


== CONSTRUCTEUR DE COMPOSANT R ==


Rien à modifier

Par défaut, traiter() renvoie une valeur, que le constructeur compare avec la forme normalisée du composant appelant.
Si la forme normalisée et la valeur de retour sont identiques, la forme étendue est conservée.
Sinon elle est remplacée.








*/