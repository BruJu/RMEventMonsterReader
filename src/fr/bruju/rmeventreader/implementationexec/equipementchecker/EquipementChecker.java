package fr.bruju.rmeventreader.implementationexec.equipementchecker;

import java.util.TreeMap;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Comparateur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition.CondHerosPossedeObjet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition.CondInterrupteur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition.CondVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.OpMathematique;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurGauche;

public class EquipementChecker implements ExecuteurInstructions {
	/** Numéro du héros */
	private int idHeros;

	/** Associations numéro d'objet - variables modifiées */
	private TreeMap<Integer, EquipementData> equipements = new TreeMap<>();

	/** Equipement en cours de lecture */
	private int idEquipementEnCours = -1;
	/** Données en cours de construction */
	private EquipementData equipementEnCours = null;

	/**
	 * Construit un EquipementChecker pour le héros donné
	 * 
	 * @param idHeros Le numéro du héros
	 */
	public EquipementChecker(int idPerso) {
		this.idHeros = idPerso;
	}

	/**
	 * Donne une liste d'association objet - variables modifiées
	 */
	public TreeMap<Integer, EquipementData> getEquipements() {
		return equipements;
	}

	/* ================================
	 * MONTEUR D'ENSEMBLE D'EQUIPEMENTS
	 * ================================ */

	/**
	 * Détermine que l'objet en cours de lecture est trop complexe pour être stockée dans le système très simple mis en
	 * place.
	 */
	private void tropComplique() {
		if (equipementEnCours == null)
			return;

		equipementEnCours.setComplexe();
		decharger();
	}

	/**
	 * Enregisre pour l'objet en cours de lecture les statistiques modifiées
	 */
	private void decharger() {
		if (equipementEnCours == null)
			return;

		equipements.put(idEquipementEnCours, equipementEnCours);
		equipementEnCours = null;
	}

	/* =========
	 * EXECUTEUR
	 * ========= */

	@Override
	public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
		tropComplique();
	}

	@Override
	public void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur,
			ValeurDroiteVariable valeurDroite) {
		if (equipementEnCours == null)
			return;

		Integer idVariable = valeurGauche.appliquerG(v -> v.idVariable, null, null);
		Integer quantite = valeurDroite.appliquerDroite(v -> v.valeur, null, null);

		if (idVariable == null || quantite == null) {
			tropComplique();
			return;
		}

		if (operateur == OpMathematique.PLUS) {
			equipementEnCours.ajouterModification(idVariable, quantite);
		} else if (operateur == OpMathematique.MOINS) {
			equipementEnCours.ajouterModification(idVariable, -quantite);
		} else {
			tropComplique();
		}
	}

	@Override
	public void Flot_siNon() {
		tropComplique();
	}

	@Override
	public void Flot_siFin() {
		decharger();
	}

	@Override
	public boolean Flot_si(Condition condition) {
		condition.appliquer(this::conditionInterrupteur, this::conditionVariable, null, null, null, null, null, null,
				null, null, null, null, null, null, this::conditionObjetEquipe, null);

		return true;
	}

	public Void conditionInterrupteur(CondInterrupteur cond) {
		if (equipementEnCours != null) {
			tropComplique();
		}
		
		return null;
	}

	public Void conditionVariable(CondVariable cond) {
		if (equipementEnCours != null) {
			tropComplique();
			return null;
		}
		
		int var = cond.variable;
		
		if ((var == 828 || var == 483 || var == 484) && cond.comparateur == Comparateur.IDENTIQUE) {
			
			idEquipementEnCours = cond.valeurDroite.appliquerFV(v -> v.valeur, a -> -1);
			
			if (idEquipementEnCours != -1)
				equipementEnCours = new EquipementData();
		} else {
			tropComplique();
		}
		
		return null;
	}

	public Void conditionObjetEquipe(CondHerosPossedeObjet cond) {
		if (equipementEnCours != null) {
			tropComplique();
			return null;
		}
		
		if (cond.idHeros != this.idHeros) {
			System.out.println("heroId " + cond.idHeros + " rencontré");
		} else {
			idEquipementEnCours = cond.idObjet;
			equipementEnCours = new EquipementData();
		}
		return null;
	}
}
