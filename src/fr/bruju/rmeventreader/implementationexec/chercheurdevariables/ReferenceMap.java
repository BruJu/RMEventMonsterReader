package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

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
	 * Crée une référence à une map
	 * @param numeroMap Numéro de la carte
	 * @param numeroEvent Numéro de l'évènement
	 * @param numeroPage Numéro de la page
	 * @param nomMap Nom de la map
	 * @param nomEvent Nom de l'évènement
	 */
	public ReferenceMap(int numeroMap, int numeroEvent, int numeroPage, String nomMap, String nomEvent) {
		this.numeroMap = numeroMap;
		this.numeroEvent = numeroEvent;
		this.numeroPage = numeroPage;
		this.nomMap = nomMap;
		this.nomEvent = nomEvent;
	}




	@Override
	public long numero() {
		return 5000 * numeroMap + numeroEvent * 20 + numeroPage;
	}




	@Override
	public String getString() {
		return "(" + numeroMap + ", " + numeroEvent + ", " + numeroPage + ") " + nomMap + " : " + nomEvent;
	}


}
