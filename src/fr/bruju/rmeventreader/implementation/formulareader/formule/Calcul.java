package fr.bruju.rmeventreader.implementation.formulareader.formule;

/**
 * Valeur qui est un calcul entre deux valeurs
 * 
 * @author Bruju
 *
 */
public class Calcul implements Valeur {
	/** Opérande de gauche */
	private Valeur gauche;
	/** Opérateur */
	private String operateur;
	/** Opérande de droite */
	private Valeur droite;
	/** Priorité du calcul pour le parenthésage */
	private int priorite;
	
	/* ============
	 * Constructeur
	 * ============ */
	
	/**
	 * Construit un calcul à partir de deux valeurs et un opératuer
	 * @param gauche Valeur de gauche
	 * @param operateur Un opérateur dans +, -, *, /, %
	 * @param droite Valeur de droite
	 */
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
	
	/**
	 * Détermine la priorité selon l'opérateur
	 * @param operateur L'opérateur
	 * @return La priorité
	 */
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


	/* ======
	 * Valeur
	 * ====== */
		
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
	public boolean estGarantiePositive() {
		if (operateur.equals("-")) {
			return false;
		}
		
		return gauche.estGarantiePositive() && droite.estGarantiePositive();
	}

	@Override
	public boolean estGarantieDeLaFormeMPMoinsConstante() {
		return gauche.concerneLesMP() && operateur.equals("-") && droite.estConstant();
	}
}
