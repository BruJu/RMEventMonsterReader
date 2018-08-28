package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

import java.util.Objects;

public class ReferenceEC implements Reference {
	public final int eventCommun;

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
