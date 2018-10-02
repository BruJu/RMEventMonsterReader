package fr.bruju.rmeventreader.implementation.chercheurdevariables.reference;

import java.util.Objects;

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
	 * @param eventCommun Numéro de l'évènement commun
	 */
	public ReferenceEC(int eventCommun, String nom) {
		this.eventCommun = eventCommun;
		this.nom = nom;
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
