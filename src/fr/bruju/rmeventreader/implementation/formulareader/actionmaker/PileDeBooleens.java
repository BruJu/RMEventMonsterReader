package fr.bruju.rmeventreader.implementation.formulareader.actionmaker;

public class PileDeBooleens {
	private int pile = 0;
	private int sommet = 1;
	
	public void empiler(boolean booleen) {
		if (!booleen) {
			pile = pile + sommet;
		}
		
		sommet = sommet * 2;
	}
	
	public boolean depiler() {
		sommet = sommet / 2;
		
		if (pile >= sommet) {
			pile -= sommet;
			return false;
		} else {
			return true;
		}
	}
	
	public boolean toutAVrai() {
		return pile == 0;
	}

	public void inverseSommet() {
		empiler(!depiler());
	}
}
