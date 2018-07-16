package fr.bruju.rmeventreader.implementation.formulareader.formule;

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
		if (gauche == null)
			throw new RuntimeException("Gauche null");
		if (operateur == null)
			throw new RuntimeException("Op null");
		if (droite == null)
			throw new RuntimeException("Droite null");
		
		this.gauche = gauche;
		this.operateur = operateur;
		this.droite = droite;
		this.priorite = setPriorite(operateur);
	}


	
	@Override
	public int getPriorite() {
		return priorite;
	}

	@Override
	public String getString() {
		String gaucheStr = gauche.getString();
		String droiteStr = droite.getString();
		
		if (gauche.getPriorite() > this.getPriorite()) {
			gaucheStr = "(" + gaucheStr + ")";
		}
	
		if (droite.getPriorite() > this.getPriorite()) {
			droiteStr = "(" + droiteStr + ")";
		}
		
		return gaucheStr + " " + operateur + " " + droiteStr;
	}

	@Override
	public int evaluer() throws NonEvaluableException, DependantDeStatistiquesEvaluation {
		int evalG;
		int evalD;
		
		try {
			evalG = gauche.evaluer();
			evalD = droite.evaluer();
		} catch (NonEvaluableException e) {
			evalD = droite.evaluer();
			throw e;
		}
		
		switch (operateur) {
		case "+":
			return evalG + evalD;
		case "-":
			return evalG - evalD;
		case "*":
			return evalG * evalD;
		case "/":
			return evalG / evalD;
		case "%":
			return evalG % evalD;
		default:
			throw new RuntimeException("Opérateur inconnu");
		}
	}

	@Override
	public boolean estPositif() {
		if (operateur.equals("-")) {
			return false;
		}
		
		return gauche.estPositif() && droite.estPositif();
	}

	@Override
	public boolean estDeLaFormeMPMoinsConstante() {
		return gauche.concerneLesMP() && operateur.equals("-") && droite.estConstant();
	}
}
