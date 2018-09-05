package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.ChoixQCM;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.Position;

public interface ModuleExecMessages {
	public static final ModuleExecMessages NullFalse = new ModuleExecMessages() {};
	public static final ModuleExecMessages NullTrue = new ModuleExecMessages() {
		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}
	};

	
	
	public default void Messages_afficherMessage(String chaine) {
	}

	public default void Messages_afficherSuiteMessage(String chaine) {
	}

	public default void Messages_modifierOptions(boolean transparent, Position position,
			boolean positionnementAuto, boolean bloquant) {	
	}

	public default boolean SaisieMessages_initierQCM(ChoixQCM choixLorsDeLAnnulation) {
		return getBooleenParDefaut();
	}

	public default boolean SaisieMessages_choixQCM(String texte, ChoixQCM numero) {
		return getBooleenParDefaut();
	}

	public default void SaisieMessages_finQCM() {
	}

	public default void SaisieMessages_saisieNombre(int idVariable, int nombreDeChiffres) {
	}

	public default void SaisieMessages_SaisieNom(int idHeros, boolean lettres, boolean afficherNomParDefaut) {
	}

	public default void Messages_appuiTouche(int numeroVariable, boolean bloquant, int enregistrementTempsMis, boolean haut,
			boolean droite, boolean bas, boolean gauche, boolean entree, boolean annuler, boolean maj, boolean chiffres,
			boolean symboles) {
		
	}
	
	public default boolean getBooleenParDefaut() {
		return false;
	}

}
