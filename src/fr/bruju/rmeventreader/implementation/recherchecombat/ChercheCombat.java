package fr.bruju.rmeventreader.implementation.recherchecombat;

import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.modele.ExecEnum;
import fr.bruju.rmdechiffreur.modele.ValeurAleatoire;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;

import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/**
 * Un exécuteur qui stocke dans un ensemble la liste des id de rencontre qu'il trouve.
 * <br>Ne gère que les rencontres uniques (ie si l'id du combat est pris dans une plage, ça n'est pas pris en compte)
 */
public class ChercheCombat implements ExecuteurInstructions, ExtChangeVariable {
	private static final int VARIABLE_ID_RENCONTRE = 435;
	private static final int MAP_COMBAT = 53;

	private int dernierIDRencontreLuMin = 0;
	private int dernierIDRencontreLuMax = 0;

	private Set<Integer> rencontresTrouvees = new TreeSet<>();

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		if (valeurGauche.idVariable == VARIABLE_ID_RENCONTRE) {
			dernierIDRencontreLuMin = valeurDroite.valeur;
			dernierIDRencontreLuMax = valeurDroite.valeur;
		}
	}

	@Override
	public void affecterVariable(Variable valeurGauche, ValeurAleatoire valeurDroite) {
		if (valeurGauche.idVariable == VARIABLE_ID_RENCONTRE) {
			dernierIDRencontreLuMin = valeurDroite.valeurMin;
			dernierIDRencontreLuMax = valeurDroite.valeurMax;
		}
	}

	@Override
	public void Jeu_teleporter(int idMap, int x, int y, ExecEnum.Direction direction) {
		if (idMap == MAP_COMBAT && dernierIDRencontreLuMin != 0) {
			for (int i = dernierIDRencontreLuMin ; i <= dernierIDRencontreLuMax ; i++) {
				rencontresTrouvees.add(i);
			}
		}
	}

	public Set<Integer> getIdTrouves() {
		return rencontresTrouvees;
	}

	public void viderRencontre() {
		dernierIDRencontreLuMin = 0;
		dernierIDRencontreLuMax = 0;
	}
}
