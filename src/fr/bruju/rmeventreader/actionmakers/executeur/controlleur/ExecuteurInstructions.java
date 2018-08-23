package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.calcul.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.ChoixQCM;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.Position;

public interface ExecuteurInstructions {

	public default void Messages_afficherMessage(String chaine) {
	}

	public default void Messages_afficherSuiteMessage(String chaine) {
	}

	public default void Messages_modifierOptions(boolean transparent, Position position,
			boolean positionnementAuto, boolean bloquant) {	
	}

	public default boolean SaisieMessages_initierQCM(ChoixQCM choixLorsDeLAnnulation) {
		return false;
	}

	public default boolean SaisieMessages_choixQCM(String texte, ChoixQCM numero) {
		return false;
	}

	public default void SaisieMessages_finQCM() {
	}

	public default void SaisieMessages_saisieNombre(int idVariable, int nombreDeChiffres) {
	}
	

	public default void Variables_changerSwitch(ValeurGauche valeurGauche, Boolean nouvelleValeur) {
	}

	public default void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
	}

	public default void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur,
			ValeurDroiteVariable valeurDroite) {
	}
	
	
	
	
}
