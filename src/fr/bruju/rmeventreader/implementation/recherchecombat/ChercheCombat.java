package fr.bruju.rmeventreader.implementation.recherchecombat;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.modele.ExecEnum;
import fr.bruju.rmdechiffreur.modele.ValeurAleatoire;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;

import java.util.Set;
import java.util.TreeSet;

/**
 * Un exécuteur qui stocke dans un ensemble la liste des id de rencontre qu'il trouve.
 * <br>Ne gère que les rencontres uniques (ie si l'id du combat est pris dans une plage, ça n'est pas pris en compte)
 */
public class ChercheCombat implements ExecuteurInstructions, ExtChangeVariable {
	/** Variable modifiée pour définir l'ID du combat */
	private static final int VARIABLE_ID_RENCONTRE = 435;
	/** Carte codant les combats */
	private static final int MAP_COMBAT = 53;

	/** Dernier ID de combat lu (valeur minimale) */
	private int dernierIDRencontreLuMin = 0;
	/** Dernier ID de combat lu (valeur maximale) */
	private int dernierIDRencontreLuMax = 0;

	/** Liste des id de combats vers lesquels on a pu être téléporté */
	private Set<Integer> rencontresTrouvees = new TreeSet<>();

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		// Determine le combat qui sera déclenché à la prochaine téléportation
		if (valeurGauche.idVariable == VARIABLE_ID_RENCONTRE) {
			dernierIDRencontreLuMin = valeurDroite.valeur;
			dernierIDRencontreLuMax = valeurDroite.valeur;
		}
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurAleatoire valeurDroite) {
		// Determine la plage de combats qu'il est possible d'avoir à la prochaine téléportation
		if (valeurGauche.idVariable == VARIABLE_ID_RENCONTRE) {
			dernierIDRencontreLuMin = valeurDroite.valeurMin;
			dernierIDRencontreLuMax = valeurDroite.valeurMax;
		}
	}

	@Override
	public void Jeu_teleporter(int idMap, int x, int y, ExecEnum.Direction direction) {
		// Si on se téléporte sur la carte de combat, alors on sauvegarde les id de combats qu'on a lu
		if (idMap == MAP_COMBAT && dernierIDRencontreLuMin != 0) {
			for (int i = dernierIDRencontreLuMin ; i <= dernierIDRencontreLuMax ; i++) {
				rencontresTrouvees.add(i);
			}
		}
	}

	/**
	 * Donne la liste des id de combats trouvés
	 * @return La liste des combats trouvés
	 */
	public Set<Integer> getIdTrouves() {
		return rencontresTrouvees;
	}

	/**
	 * Vide la variable de dernière rencontre lue. (Permet de réutiliser le même objet pour plusieurs évènements si on
	 * souhaite enregistrer dans le même set les combats rencontrés).
	 */
	public void viderRencontre() {
		dernierIDRencontreLuMin = 0;
		dernierIDRencontreLuMax = 0;
	}
}
