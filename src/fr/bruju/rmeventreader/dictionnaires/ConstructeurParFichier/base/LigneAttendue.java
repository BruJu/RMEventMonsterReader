package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.modele.Monteur;

/**
 * Traitement déclarant l'attente d'une ligne
 * @author Bruju
 *
 * @param <K> Le type de monteur
 */
public class LigneAttendue<K extends Monteur<?>> implements Traitement<K> {
	/** La ligne attendue */
	private String ligneAttendue;
	
	/**
	 * Traitement exigeant l'arrivée d'une ligne spécifique
	 * @param ligneAttendue La ligne attendue
	 */
	public LigneAttendue(String ligneAttendue) {
		this.ligneAttendue = ligneAttendue;
	}

	@Override
	public Avancement traiter(String ligne) {
		if (ligne.equals(ligneAttendue)) {
			return Avancement.Suivant;
		} else {
			return Avancement.Tuer;
		}
	}

	@Override
	public void appliquer(K monteur) {
	}
	
	@Override
	public String toString() {
		return "LA " + ligneAttendue;
	}
}
