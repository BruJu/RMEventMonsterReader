package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.EvenementDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.NombreObjet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Pointeur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Tous;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurAleatoire;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurDeplacable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurDivers;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.VariableHeros;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.VariablePlage;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.SujetTransition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.Vehicule;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

class Dechiffreur {
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
			throw new ArgumentInconnuException("Extraire Operateur Mathematique " + numero);
		}
	}

	public Vehicule dechiffreVehicule(int numero) {
		switch (numero) {
		case 0:
			return Vehicule.RADEAU;
		case 1:
			return Vehicule.BATEAU;
		case 2:
			return Vehicule.VAISSEAU;
		default:
			throw new ArgumentInconnuException("DechiffreVehicule " + numero);
		}
	}

	public SujetTransition sujetTransition(int id) {
		if (id < 2)
			return SujetTransition.TELEPORTATION;

		if (id < 4)
			return SujetTransition.COMBAT;

		if (id < 6)
			return SujetTransition.FINCOMBAT;

		throw new ArgumentInconnuException("sujetTransition " + id);
	}

	public void afficher(int[] parametres, int[] is, String s) {
		System.out.println("=====");
		for (int i = 0; i != parametres.length; i++) {
			if (Utilitaire.getPosition(i, is) == -1) {
				System.out.print("[" + i + "] = " + parametres[i] + " ; ");
			}
		}
		System.out.println("s");
	}

	public ArrierePlanCombat arrierePlan(int type, int equipiers, int typeTerrain, String nomPanorama) {
		switch (type) {
		case 0:
			return new ArrierePlanCombat.Aucun();
		case 1:
			return new ArrierePlanCombat.Specifique(nomPanorama, equipiers == 1);
		case 2:
			return new ArrierePlanCombat.AssocieAuTerrain(typeTerrain);
		default:
			throw new ArgumentInconnuException("ArrierePlanCombat " + type);
		}
	}

	public Condition dechiffrerCondition(int[] parametres, String s) {
		switch (parametres[0]) {
		case 0:
			return new Condition.CondObjet(parametres[1], parametres[2] == 1);
		case 1:
		{
			int idVariable = parametres[1];
			FixeVariable valeurDroite = dechiffrerFixeVariable(parametres[2], parametres[3]);
			Comparateur comp;
			switch (parametres[4]) {
			case 0:
				comp = Comparateur.IDENTIQUE;
				break;
			case 1:
				comp = Comparateur.SUPEGAL;
				break;
			case 2:
				comp = Comparateur.INFEGAL;
				break;
			case 3:
				comp = Comparateur.SUP;
				break;
			case 4:
				comp = Comparateur.INF;
				break;
			case 5:
				comp = Comparateur.DIFFERENT;
				break;
			default:
				comp = null;
			}
			return new Condition.CondVariable(idVariable, comp, valeurDroite);
		}
		case 2:
		case 10:
			return new Condition.CondChrono(parametres[0] == 2,
					parametres[2] == 0 ? Comparateur.SUPEGAL : Comparateur.INFEGAL , parametres[1]);
		case 3:
			return new Condition.CondArgent(parametres[2] == 0 ? Comparateur.SUPEGAL : Comparateur.INFEGAL , parametres[1]);
		case 9:
			return new Condition.CondMusiqueJoueePlusDUneFois();
		case 8:
			return new Condition.CondEventDemarreParAppui();
		case 7:
			return new Condition.CondVehiculeUtilise(ExecEnum.Vehicule.values()[parametres[1]]);
		case 6:
			return new Condition.CondDirection(new EvenementDeplacable(parametres[1]), ExecEnum.Direction.values()[parametres[2]]);
		case 4:
			return new Condition.CondObjet(parametres[1], parametres[2] == 0);
		case 5:
			int idHeros = parametres[1];
			switch (parametres[2]) {
			case 0:
				return new Condition.CondHerosDansLEquipe(idHeros);
			case 1:
				return new Condition.CondHerosAPourNom(idHeros, s);
			case 2:
				return new Condition.CondHerosNiveauMin(idHeros, parametres[3]);
			case 3:
				return new Condition.CondHerosAAuMoinsHp(idHeros, parametres[3]);
			case 4:
				return new Condition.CondHerosAStatut(idHeros, parametres[3]);
			case 5:
				return new Condition.CondHerosPossedeObjet(idHeros, parametres[3]);
			case 6:
				return new Condition.CondHerosPossedeSort(idHeros, parametres[3]);
			default:
				return null;
			}
		default:
			return null;
		}
	}
}
