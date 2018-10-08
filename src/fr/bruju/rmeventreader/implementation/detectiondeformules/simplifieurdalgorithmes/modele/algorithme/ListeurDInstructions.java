package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme;

public class ListeurDInstructions {
	private static final String TABULATION = "  ";
	private static final char LN = '\n';
	private StringBuilder stringBuilder = new StringBuilder();
	private int tabulations = 0;
	private boolean nouvelleLigne = true;
	
	public ListeurDInstructions append(String chaine) {
		insererTabulations();
		stringBuilder.append(chaine);
		return this;
	}
	
	public ListeurDInstructions ln() {
		stringBuilder.append(LN);
		nouvelleLigne = true;
		return this;
	}
	
	public ListeurDInstructions tab() {
		tabulations++;
		return this;
	}
	
	public ListeurDInstructions retrait() {
		tabulations--;
		return this;
	}
	

	private void insererTabulations() {
		if (nouvelleLigne) {
			nouvelleLigne = false;
			
			for (int i = 0 ; i != tabulations ; i++) {
				stringBuilder.append(TABULATION);
			}
		}
	}
	
	public String toString() {
		return stringBuilder.toString();
	}
}
