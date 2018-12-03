package fr.bruju.rmeventreader.implementation.chercheurdevariables;

import fr.bruju.rmdechiffreur.reference.Reference;

import java.util.Set;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Une base de recherche qui peut renvoyer les références qu'elle a trouvé et lancer la recherche toute seule.
 */
public interface BaseDeRechercheReferenceuse extends BaseDeRecherche {
	public Set<Reference> getReferences();

	public default void chercher() {
		PROJET.referencerEvenementsCommuns(this::getExecuteur);
		PROJET.referencerCartes(this::getExecuteur);
	}
}
