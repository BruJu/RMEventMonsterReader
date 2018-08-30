package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.Objects;

/**
 * Référence à un évènement commun
 * @author Bruju
 *
 */
public class ReferenceEC implements Reference {
	/** Numéro de l'évènement commun référencé */
	public final int eventCommun;

	/**
	 * Crée une référence à un évènement commun
	 * @param eventCommun Numéro de l'évènement commun
	 */
	public ReferenceEC(int eventCommun) {
		this.eventCommun = eventCommun;
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
	public long numero() {
		return -10000 + eventCommun;
	}

	@Override
	public String getString() {
		return "Evenement Commun " + this.eventCommun;
	}
}