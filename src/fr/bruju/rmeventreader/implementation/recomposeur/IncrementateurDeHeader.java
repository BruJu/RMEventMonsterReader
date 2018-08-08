package fr.bruju.rmeventreader.implementation.recomposeur;

import fr.bruju.rmeventreader.actionmakers.composition.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.Header;

public interface IncrementateurDeHeader {

	public default Header incrementerHeader(Header header) {
		return new Header(header, recupererGroupeDeConditions());
	}
	
	
	
	public Algorithme getResultat();

	public GroupeDeConditions recupererGroupeDeConditions();

}