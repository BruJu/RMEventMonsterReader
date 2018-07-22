package fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur;


public class VBase implements Valeur {
	public final int idVariable;
	
	public VBase(Integer idVariable) {
		this.idVariable = idVariable;
		
	}

	@Override
	public String getString() {
		return "V[" + idVariable + "]";
	}

}
