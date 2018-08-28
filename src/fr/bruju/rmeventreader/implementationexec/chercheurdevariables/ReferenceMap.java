package fr.bruju.rmeventreader.implementationexec.chercheurdevariables;

public class ReferenceMap implements Reference {
	
	public final int numeroMap;
	public final int numeroEvent;
	public final int numeroPage;

	public final String nomMap;
	public final String nomEvent;
	
	
	
	
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
