package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

public class Ignorance {
	private int codeDebut;
	private int codeFin;
	private int niveau;

	public Ignorance(int codeDebut, int codeFin) {
		this.codeDebut = codeDebut;
		this.codeFin = codeFin;
		this.niveau = 1;
	}
	
	public Ignorance appliquerCode(int code) {
		if (codeDebut == code) {
			this.niveau ++;
		} else if (codeFin == code) {
			this.niveau --;
		}
		
		return (niveau == 0) ? null : this;
	}
}
