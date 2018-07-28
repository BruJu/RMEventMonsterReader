package fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;

public interface ComposantEtendu extends Composant {

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
	public void visit(E_NouveauComposantEtendu composant) {
		this.composant = traiter(composant);
	}

	protected Intermediaire traiter(E_NouveauComposantEtendu composant) {
		return composantEtenduNonGere(composant);
	}
	
-> permet à traiter(Composant composant) de récupérer le résultat et de le renvoyer
-> Permet d'appeler le traitement du composant normalisé


== CONSTRUCTEUR DE COMPOSANTS RECURSIF ==

	protected Composant modifier(E_NouveauComposantEtendu composant) {
		return composant;
	}

	@Override
	protected final Composant traiter(E_NouveauComposantEtendu composant) {
		return transformerElementCompose(
				c -> new Composant[]{liste des fils},
				tableau -> new E_Entre(nouveaux fils renvoyés dans le même ordre),
				composant,
				this::modifier);
	}

	La méthode modifier est mise de manière à ce que les visiteurs visitent bien ce nouveau composant. Le traitement
par défaut de ce nouveau composant étant de ne rien faire.
	La méthode traiter est redéfinie pour que l'on traite les fils.






*/