package fr.bruju.rmeventreader.actionmakers.modele;

import java.util.function.Function;

public class Tous implements ValeurMembre {
	private static Tous instance;
	
	private Tous() {
	}
	
	public static Tous getInstance() {
		if (null == instance) {
			instance = new Tous();
		}
		return instance;
	}

	@Override
	public <T> T appliquerMembre(Function<Tous, T> fonctionTous, Function<ValeurFixe, T> fonctionFixe,
			Function<Variable, T> fonctionVariable) {
		return fonctionTous == null ? null : fonctionTous.apply(this);
	}
}
