package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

public interface Transformateur {
	public void accept(Visiteur visiteur);

	public interface Visiteur {

		public default void visit(Transformateur transformateur) {
			transformateur.accept(this);
		}

		void visit(Simplification simplification);

		void visit(Separateur separateur);
	}
}
