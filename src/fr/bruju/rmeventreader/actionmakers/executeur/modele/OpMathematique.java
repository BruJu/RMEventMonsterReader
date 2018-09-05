package fr.bruju.rmeventreader.actionmakers.executeur.modele;

public enum OpMathematique {
	/* =================
	 * OPERATEURS CONNUS
	 * ================= */
	PLUS("+", false, 0) {
		@Override
		public int calculer(int gauche, int droite) {
			return gauche + droite;
		}
	},
	MOINS("-", PLUS, false, 0) {
		@Override
		public int calculer(int gauche, int droite) {
			return gauche - droite;
		}
	},
	FOIS("x", true, 1) {
		@Override
		public int calculer(int gauche, int droite) {
			return gauche * droite;
		}
	},
	MODULO("%", FOIS, true, Integer.MAX_VALUE) {
		@Override
		public int calculer(int gauche, int droite) {
			return gauche % droite;
		}
	},
	DIVISE("/", FOIS, true, 1) {
		@Override
		public int calculer(int gauche, int droite) {
			return gauche / droite;
		}
	};

	/* ============================
	 * FONCTIONEMENT D'UN OPERATEUR
	 * ============================	*/

	/** Opposé */
	public OpMathematique oppose;

	/** Symbole d'affichage du signe */
	public String symbole;

	/** Vrai si le fait de mettre l'opérande de gauche à 0 rend le résultat égal à 0 */
	public boolean zeroAbsorbantAGauche = false;
	
	/** Element neutre */
	public int neutre;
	
	OpMathematique(String symbole, boolean zeroAbsorbantAGauche, int neutre) {
		this.symbole = symbole;
		this.zeroAbsorbantAGauche = zeroAbsorbantAGauche;
		this.neutre = neutre;
	}

	OpMathematique(String symbole, OpMathematique oppose, boolean zeroAbsorbantAGauche, int neutre) {
		this.symbole = symbole;
		this.oppose = oppose;
		oppose.oppose = this;
		this.zeroAbsorbantAGauche = zeroAbsorbantAGauche;
		this.neutre = neutre;
	}

	/* ========
	 * SERVICES
	 * ======== */
	/**
	 * Fait le calcul entre les deux valeurs données en utilisant l'opérateur courant
	 * 
	 * @param gauche La valeur de gauche
	 * @param droite La valeur de droite
	 * @return Le résultat
	 */
	public abstract int calculer(int gauche, int droite);
	
	/**
	 * Donne le signe opposé à l'opérateur actuel
	 * 
	 * @return Le signe opposé à l'opérateur
	 */
	public OpMathematique revert() {
		return this.oppose;
	}

	@Override
	public String toString() {
		return symbole;
	}
	
	/**
	 * Donne l'élément neutre
	 * @return
	 */
	public Integer getNeutre() {
		return neutre;
	}
}
