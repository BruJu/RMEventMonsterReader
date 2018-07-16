package fr.bruju.rmeventreader.implementation.formulareader.formule;

public class ValeurVariable implements Valeur {
	private int idVariable;
	
	public ValeurVariable (int idVariable) {
		this.idVariable = idVariable;
	}
	
	@Override
	public String getStringMin() {
		return "V[" + idVariable + "]";
	}

	@Override
	public String getStringMax() {
		return "V[" + idVariable + "]";
	}

	@Override
	public int getPriorite() {
		return 0;
	}

	@Override
	public String getStringUnique() {
		return "V[" + idVariable + "]";
	}

}
