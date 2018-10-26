package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation;

public interface Transformateur {
	public void accept(Visiteur visiteur);

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
