package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

/**
 * Un transformateur effectue un traitement sur des algorithmes étiquetés
 */
public interface Transformateur {
	/**
	 * Accepte le visiteur
	 * @param visiteur Le visiteur
	 */
	public void accept(Visiteur visiteur);

	/**
	 * Visiteur de transformateur pour connaître son type
	 */
	public interface Visiteur {
		public default void visit(Transformateur transformateur) {
			transformateur.accept(this);
		}

		void visit(Simplification simplification);
		void visit(Separateur separateur);
		void visit(Unificateur unificateur);
		void visit(ManipulateurDeListe manipulateur);
	}
}
