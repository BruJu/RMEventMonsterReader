package fr.bruju.rmeventreader.actionmakers.modele;

public class EvenementDeplacable {
	public final static int ID_HEROS = 10001;
	public final static int ID_RADEAU = 10002;
	public final static int ID_BATEAU = 10003;
	public final static int ID_VAISCEAU = 10004;
	public final static int ID_THIS = 10005;
	
	public final int id;

	public EvenementDeplacable(int id) {
		this.id = id;
	}
	
	public boolean estNumero() {
		return id < 10000;
	}
	
	public boolean estHeros() {
		return this.id == ID_HEROS;
	}
	
	public boolean estRadeau() {
		return this.id == ID_RADEAU;
	}
	
	public boolean estVaisceau() {
		return this.id == ID_VAISCEAU;
	}
	
	public boolean estBateau() {
		return this.id == ID_BATEAU;
	}
	
	public boolean estCetEvenement() {
		return this.id == ID_THIS;
	}
}
