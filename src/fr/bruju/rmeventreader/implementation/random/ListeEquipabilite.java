package fr.bruju.rmeventreader.implementation.random;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.util.MapsUtils;

import java.util.*;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

public class ListeEquipabilite implements ExecuteurInstructions, ExtChangeVariable {
	public static Map<Integer, Set<Integer>> chercherObjetsEquipables() {
		ListeEquipabilite equipabilite = new ListeEquipabilite();

		equipabilite.changerHeros(1); // Ir
		PROJET.lireEvenement(equipabilite, 67, 10, 1);

		equipabilite.changerHeros(2); // As
		PROJET.lireEvenement(equipabilite, 70, 18, 1);

		equipabilite.changerHeros(3); // Ai
		PROJET.lireEvenement(equipabilite, 52, 3, 1);

		equipabilite.changerHeros(4); // Ye
		PROJET.lireEvenement(equipabilite, 71, 19, 1);

		equipabilite.changerHeros(5); // Ma
		PROJET.lireEvenement(equipabilite, 69, 3, 1);

		equipabilite.changerHeros(6); // Ar
		PROJET.lireEvenement(equipabilite, 68, 17, 1);

		equipabilite.changerHeros(7); // Uf
		PROJET.lireEvenement(equipabilite, 73, 20, 1);

		return equipabilite.getObjetsEquipables();
	}


	private Map<Integer, Set<Integer>> objetsEquipables = new HashMap<>();

	private int herosEnCours;

	public static int VARIABLE_ID_EQUIP1 = 2556;
	public static int VARIABLE_ID_EQUIP2 = 2557;
	public static int VARIABLE_ID_EQUIP3 = 2558;
	public static int VARIABLE_ID_EQUIP4 = 2559;

	public void changerHeros(int heros) {
		this.herosEnCours = heros;
	}

	public Map<Integer, Set<Integer>> getObjetsEquipables() {
		return objetsEquipables;
	}

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		int idVariable = valeurGauche.idVariable;

		if (idVariable == VARIABLE_ID_EQUIP1 || idVariable == VARIABLE_ID_EQUIP2
				|| idVariable == VARIABLE_ID_EQUIP3 || idVariable == VARIABLE_ID_EQUIP4) {

			MapsUtils.ajouterElementDansSet(objetsEquipables, valeurDroite.valeur, herosEnCours);
		}
	}
}
