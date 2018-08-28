package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

public interface Reference extends Comparable<Reference> {
	
	public long numero();
	
	@Override
	public default int compareTo(Reference arg0) {
		return Long.compare(this.numero(), arg0.numero());
	}

	public String getString();
}
