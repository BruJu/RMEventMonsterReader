package fr.bruju.rmeventreader.actionmakers.executeur.modele.calcul;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;

public enum Comparateur {
	/* =================
	 * OPERATEURS CONNUS
	 * ================= */
	
	/** Identique */
	IDENTIQUE("=") {
		@Override
		public boolean test(int gauche, int droite) {
			return gauche == droite;
		}
	},
	/** Différent */
	DIFFERENT("!=", IDENTIQUE) {
		@Override
		public boolean test(int gauche, int droite) {
			return gauche != droite;
		}
	},
	/** Supérieur ou égal */
	SUPEGAL(">=") {
		@Override
		public boolean test(int gauche, int droite) {
			return gauche >= droite;
		}
	},
	/** Inférieur ou égal */
	INFEGAL("<=") {
		@Override
		public boolean test(int gauche, int droite) {
			return gauche <= droite;
		}
	},
	/** Supérieur */
	SUP(">", INFEGAL) {
		@Override
		public boolean test(int gauche, int droite) {
			return gauche > droite;
		}
	},
	/** Inférieur */
	INF("<", SUPEGAL) {
		@Override
		public boolean test(int gauche, int droite) {
			return gauche < droite;
		}
	};
	
	/* ============================
	 * FONCTIONEMENT D'UN OPERATEUR
	 * ============================	*/
	
	/** Opposé */
	public Comparateur oppose;
	
	/** Symbole d'affichage du signe */
	public String symbole;
	
	/**
	 * Construit un opérateur de test
	 * @param fonctionTest La fonction qui teste l'opérateur à partir de deux entiers
	 */
	Comparateur(String symbole) {
		this.symbole = symbole;
	}
	
	/**
	 * Construit un opérateur de test ayant un opposé
	 * 
	 * @param oppose Opérateur opposé à cet opérateur
	 * @param tstFunc Fonction permettant de tester l'opérateur sur deux opérandes
	 */
	Comparateur(String symbole, Comparateur oppose) {
		this.symbole = symbole;
		this.oppose = oppose;
		oppose.oppose = this;
	}
	
	
	/* ========
	 * SERVICES
	 * ======== */
	
	/**
	 * Teste la comparaison avec les deux valeurs données
	 * 
	 * @param gauche Valeur gauche
	 * @param droite Valeur droite
	 * @return Vrai si la comparaison entre les deux valeurs est vraie
	 */
	public abstract boolean test(int gauche, int droite);
	
	/**
	 * Donne le signe opposé à l'opérateur actuel
	 * 
	 * @return Le signe opposé à l'opérateur
	 */
	public Comparateur revert() {
		return this.oppose;
	}
	
	@Override
	public String toString() {
		return symbole;
	}
	
	
	

	
	/* ==================
	 * RETROCOMPATIBILITE
	 * ================== */
	
	public static Operator toOperator(Comparateur comparateur) {
		switch (comparateur) {
		case DIFFERENT:
			return Operator.DIFFERENT;
		case IDENTIQUE:
			return Operator.IDENTIQUE;
		case INF:
			return Operator.INF;
		case INFEGAL:
			return Operator.INFEGAL;
		case SUP:
			return Operator.SUP;
		case SUPEGAL:
			return Operator.SUPEGAL;
		default:
			return null;
		}
	}
}
