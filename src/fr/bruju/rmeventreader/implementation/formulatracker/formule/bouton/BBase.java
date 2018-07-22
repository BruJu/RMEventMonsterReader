package fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton;

public class BBase implements Bouton {
	public final int numero; 
	
	public BBase(int numero) {
		this.numero = numero;
	}
	
	@Override
	public String getString() {
		return "S["+numero+"]";
	}
}
