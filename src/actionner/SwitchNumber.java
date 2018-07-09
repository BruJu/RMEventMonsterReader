package actionner;

public class SwitchNumber {
	public final int numberDebut;
	public final int numberFin;
	public final boolean pointed;
	
	public SwitchNumber(int number, boolean pointed) {
		this.numberDebut = number;
		this.numberFin = number;
		this.pointed = pointed;
	}
	
	public SwitchNumber(int debut, int fin) {
		this.numberDebut = debut;
		this.numberFin = fin;
		this.pointed = false;
	}
}
