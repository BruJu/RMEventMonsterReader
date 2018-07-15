package fr.bruju.rmeventreader.formule.operations;

import fr.bruju.rmeventreader.formule.Valeur;

public class Calcul implements Valeur {
	private Valeur gauche;
	private String operateur;
	private Valeur droite;
	private int priorite;
	
	private static int setPriorite(String operateur) {
		switch (operateur) {
		case "+":
		case "-":
			return 2;
		case "*":
		case "/":
		case "%":
			return 1;
		default:
			throw new RuntimeException("Opérateur inconnu");
		}
	}

	public Calcul(Valeur gauche, String operateur, Valeur droite) {
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = droite;
		this.priorite = setPriorite(operateur);
	}

	@Override
	public String getStringMin() {
		String gaucheStr = gauche.getStringMin();
		String droiteStr = droite.getStringMin();
		
		if (gauche.getPriorite() > this.getPriorite()) {
			gaucheStr = "(" + gaucheStr + ")";
		}

		if (droite.getPriorite() > this.getPriorite()) {
			droiteStr = "(" + droiteStr + ")";
		}
		
		return gaucheStr + " " + operateur + " " + droiteStr;
	}

	@Override
	public String getStringMax() {
		String gaucheStr = gauche.getStringMax();
		String droiteStr = droite.getStringMax();
		
		if (gauche.getPriorite() > this.getPriorite()) {
			gaucheStr = "(" + gaucheStr + ")";
		}

		if (droite.getPriorite() > this.getPriorite()) {
			droiteStr = "(" + droiteStr + ")";
		}
		
		return gaucheStr + " " + operateur + " " + droiteStr;
	}

	
	@Override
	public int getPriorite() {
		return priorite;
	}
}
