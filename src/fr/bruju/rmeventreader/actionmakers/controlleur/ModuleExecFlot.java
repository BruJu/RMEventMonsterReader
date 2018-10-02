package fr.bruju.rmeventreader.actionmakers.controlleur;

import fr.bruju.rmeventreader.actionmakers.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.modele.FixeVariable;

public interface ModuleExecFlot {
	public static final ModuleExecFlot NullFalse = new ModuleExecFlot() {};
	public static final ModuleExecFlot NullTrue = new ModuleExecFlot() {
		@Override
		public boolean Flot_si(Condition condition) {
			return true;
		}
	};

	public default void Flot_appelEvenementCarte(FixeVariable evenement, FixeVariable page) {
		
	}

	public default void Flot_appelEvenementCommun(int numero) {
		
	}

	public default void Flot_boucleDebut() {
		
	}
	
	public default void Flot_boucleFin() {
		
	}

	public default void Flot_boucleSortir() {
		
	}
	
	public default void Flot_commentaire(String message) {
		
	}

	public default void Flot_effacerCetEvenement() {
		
	}

	public default void Flot_etiquette(int numero) {
		
	}

	public default void Flot_sautEtiquette(int numero) {
		
	}

	public default boolean Flot_si(Condition condition) {
		return false;
	}
	
	public default void Flot_siFin() {
		
	}
	public default void Flot_siNon() {
	}

	public default void Flot_stopperCetEvenement() {
		
	}
	
	
}
