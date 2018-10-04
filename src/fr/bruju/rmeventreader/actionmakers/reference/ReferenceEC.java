package fr.bruju.rmeventreader.actionmakers.reference;

import java.util.Objects;

import fr.bruju.lcfreader.rmobjets.RMEvenementCommun;

/**
 * Référence à un évènement commun
 * @author Bruju
 *
 */
public class ReferenceEC implements Reference {
	/** Numéro de l'évènement commun référencé */
	public final int eventCommun;
	/** Nom de l'évènement commun */
	public final String nom;

	/**
	 * Crée une référence à un évènement commun
	 * @param eventCommun L'évènement commun
	 */
	public ReferenceEC(RMEvenementCommun evenementCommun) {
		this.eventCommun = evenementCommun.id();
		this.nom = evenementCommun.nom();
	}

	@Override
	public int hashCode() {
		return Objects.hashCode(eventCommun);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ReferenceEC) {
			ReferenceEC that = (ReferenceEC) object;
			return this.eventCommun == that.eventCommun;
		}
		return false;
	}


	@Override
	public String getString() {
		return "Evenement Commun " + this.eventCommun + " " + this.nom;
	}

	@Override
	public int idCarte() {
		return 0;
	}

	@Override
	public int idEvenement() {
		return eventCommun;
	}
	
	@Override
	public int idPage() {
		return 0;
	}
}
