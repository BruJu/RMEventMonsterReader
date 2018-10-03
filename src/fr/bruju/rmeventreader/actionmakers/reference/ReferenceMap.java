package fr.bruju.rmeventreader.actionmakers.reference;

import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.rmobjets.RMPage;

/**
 * Référence à un évènement sur une carte
 * @author Bruju
 *
 */
public class ReferenceMap implements Reference {
	/** Numéro de la carte */
	public final int numeroMap;
	/** Numéro de l'évènement */
	public final int numeroEvent;
	/** Numéro de la page */
	public final int numeroPage;

	/** Nom de la map */
	public final String nomMap;
	/** Nom de l'évènement */
	public final String nomEvent;
	

	/**
	 * Crée une référence à une page d'un évènement sur une carte
	 * @param map La carte
	 * @param event L'évènement
	 * @param page La page
	 */
	public ReferenceMap(RMMap map, RMEvenement event, RMPage page) {
		numeroMap = map.id();
		numeroEvent = event.id();
		numeroPage = page.id();
		
		nomMap = map.nom();
		nomEvent = "[" + event.x() + ", " + event.y() + "] " + event.nom();
	}

	@Override
	public String getString() {
		return "(" + numeroMap + ", " + numeroEvent + ", " + numeroPage + ") " + nomMap + " : " + nomEvent;
	}

	@Override
	public int idCarte() {
		return numeroMap;
	}

	@Override
	public int idEvenement() {
		return numeroEvent;
	}

	@Override
	public int idPage() {
		return numeroPage;
	}
}
