package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

public class SonParam {
	/** Entre 0 et 100 */
	public final int volume;
	/** Entre 50 et 150 */
	public final int tempo;
	/** 0 = Gauche, 50 = Milieu, 100 = Droite */
	public final int balance;
	
	public SonParam(int volume, int tempo, int balance) {
		this.volume = volume;
		this.tempo = tempo;
		this.balance = balance;
	}
}
