package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmdechiffreur.controlleur.ExtCondition;
import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.Couleur;
import fr.bruju.rmdechiffreur.modele.FixeVariable;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.ExecEnum.TypeEffet;
import fr.bruju.rmeventreader.implementation.monsterlist.manipulation.ConditionOnMonsterId;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.MonsterDatabase;
import fr.bruju.rmeventreader.implementation.monsterlist.metier.Monstre;

/**
 * Complète la base de données en lisant des affichages d'images et des conditions sur le numéro du combat et le numéro
 * du monstre.
 * 
 * @author Bruju
 *
 */
public class NomDeMonstresViaShowPicture extends ExecuteurAFiltre<Monstre> implements ExtCondition {
	private static final int ID_IMAGE_AFFICHANt_LES_NOMS = 19;
	private static final int VARIABLE_IDMONSTRE = 559;
	private static final int VARIABLE_IDCOMBAT = 435;

	/* ==========================
	 * Instanciation de la classe
	 * ========================== */

	/**
	 * Base de données de monstre
	 */
	private MonsterDatabase baseDeDonnees;

	/**
	 * Instancie le faiseur d'action avec la base de données à compléter
	 * 
	 * @param baseDeDonnees La base de données à compléter
	 */
	public NomDeMonstresViaShowPicture(MonsterDatabase baseDeDonnees) {
		this.baseDeDonnees = baseDeDonnees;
	}

	/* ====================
	 * Stacked Action Maker
	 * ==================== */

	@Override
	protected Collection<Monstre> getAllElements() {
		return baseDeDonnees.extractMonsters();
	}
	
	/* ============
	 * Action Maker
	 * ============ */

	
	// Actions
	
	@Override
	public void Image_afficher(int numeroImage, String nomImage, FixeVariable xImage, FixeVariable yImage,
			int transparenceHaute, int transparenceBasse, int agrandissement, Couleur couleur, int saturation,
			TypeEffet typeEffet, int intensiteEffet, boolean transparence, boolean defilementAvecCarte) {
		
		if (numeroImage == ID_IMAGE_AFFICHANt_LES_NOMS) {
			getElementsFiltres().forEach(monstre -> monstre.nom = nomImage);
		}
	}
	
	
	@Override
	public int variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		if (variable == VARIABLE_IDMONSTRE) {
			conditions.push(new ConditionOnMonsterId(true, comparateur, droite.valeur));
			return 0;
		} else if (variable == VARIABLE_IDCOMBAT) {
			conditions.push(new ConditionOnMonsterId(false, comparateur, droite.valeur));
			return 0;
		} else {
			return 3;
		}
	}

	@Override
	public boolean getBooleenParDefaut() {
		return false;
	}
}
