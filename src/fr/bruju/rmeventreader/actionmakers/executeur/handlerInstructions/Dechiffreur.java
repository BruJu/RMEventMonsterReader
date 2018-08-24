package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.calcul.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.EvenementDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.NombreObjet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Tous;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDivers;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariableHeros;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.VariablePlage;

public class Dechiffreur {
	private static Dechiffreur instance;

	private Dechiffreur() {
	}

	public static Dechiffreur getInstance() {
		if (null == instance) {
			instance = new Dechiffreur();
		}
		return instance;
	}
	
	public VariableHeros.Caracteristique caracAugmentable(int valeur) {
		switch (valeur) {
		case 0:
			return VariableHeros.Caracteristique.HPMAX;
		case 1:
			return VariableHeros.Caracteristique.MPMAX;
		case 2:
			return VariableHeros.Caracteristique.ATTAQUE;
		case 3:
			return VariableHeros.Caracteristique.DEFENSE;
		case 4:
			return VariableHeros.Caracteristique.INTELLIGENCE;
		case 5:
			return VariableHeros.Caracteristique.AGILITE;
		default:
			throw new ArgumentInconnuException("Carac augmentable " + valeur);
		}
	}
	

	public ValeurMembre dechiffrerMembreCible(int type, int valeur) {
		switch (type) {
		case 0:
			return Tous.getInstance();
		case 1:
			return new ValeurFixe(valeur);
		case 2:
			return new Variable(valeur);
		default:
			throw new ArgumentInconnuException("Membre cible Type " + type);
		}
	}
	

	public FixeVariable dechiffrerFixeVariable(int type, int nombre) {
		if (type == 0) {
			return new ValeurFixe(nombre);
		} else if (type == 1) {
			return new Variable(nombre);
		} else {
			throw new ArgumentInconnuException("FixeVariable Type  " + type);
		}
	}
	
	public ValeurDroiteVariable dechiffrerValeurDroiteVariable(int type, int valeur1, int valeur2) {
		switch (type) {
		case 0:
			return new ValeurFixe(valeur1);
		case 1:
			return new Variable(valeur1);
		case 2:
			return new Pointeur(valeur1);
		case 3:
			return new ValeurAleatoire(valeur1, valeur2);
		case 4:
			return new NombreObjet(valeur1, valeur2 == 1);
		case 5:
			return new VariableHeros(valeur2, VariableHeros.Caracteristique.values()[valeur1]);
		case 6:
			return new ValeurDeplacable(new EvenementDeplacable(valeur1),
					ValeurDeplacable.Caracteristique.values()[valeur2]);
		case 7:
			return ValeurDivers.values()[valeur1];
		default:
			throw new ArgumentInconnuException("VDVariable Type  " + type);
		}
	}


	public Boolean dechiffrerBooleen(int action) {
		switch (action) {
		case 0:
			return Boolean.TRUE;
		case 1:
			return Boolean.FALSE;
		case 2:
			return null;
		default:
			throw new ArgumentInconnuException("ChangerSwitch Action " + action);
		}
	}


	public ValeurGauche dechiffrerValeurGauche(int type, int valeur1, int valeur2) {
		switch (type) {
		case 0:
			return new Variable(valeur1);
		case 1:
			return new VariablePlage(valeur1, valeur2);
		case 2:
			return new Pointeur(valeur1);
		default:
			throw new ArgumentInconnuException("ChangerSwitch Variable type " + type);
		}
	}
	

	
	public OpMathematique extraireOperateurMaths(int numero) {
		switch (numero) {
		case 0:
			return null;
		case 1:
			return OpMathematique.PLUS;
		case 2:
			return OpMathematique.MOINS;
		case 3:
			return OpMathematique.FOIS;
		case 4:
			return OpMathematique.DIVISE;
		case 5:
			return OpMathematique.MODULO;
		default:
			throw new ArgumentInconnuException("Extraire Operateur Methmatique " + numero);
		}
	}
	
}
