package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExtCondition;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecMedia;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Comparateur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Couleur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.TypeEffet;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurFixe;
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
public class NomDeMonstresViaShowPicture extends StackedActionMaker<Monstre> implements ModuleExecMedia, ExtCondition {
	// Constantes
	/** Numéro de l'image qui affiche les noms */
	private final int SHOW_PIC_ID_WITH_NAME;

	/** Numéro de la variable contenant l'id des monstres */
	private final int VARIABLE_IDMONSTRE;

	/** Numéro de la variable contenant l'id du combat */
	private final int VARIABLE_IDCOMBAT;

	/* ==========================
	 * Instanciation de la classe
	 * ========================== */

	/**
	 * Base de données de monstre
	 */
	private MonsterDatabase database;

	/**
	 * Instancie le faiseur d'action avec la base de données à compléter
	 * 
	 * @param database La base de données à compléter
	 */
	public NomDeMonstresViaShowPicture(MonsterDatabase database) {
		this.database = database;

		SHOW_PIC_ID_WITH_NAME = database.contexte.getVariable("LecturePicture_IDPicture");
		VARIABLE_IDMONSTRE    = database.contexte.getVariable("LecturePicture_IDMonstre");
		VARIABLE_IDCOMBAT     = database.contexte.getVariable("LecturePicture_IDCombat");
	}

	/* ====================
	 * Stacked Action Maker
	 * ==================== */

	@Override
	protected Collection<Monstre> getAllElements() {
		return database.extractMonsters();
	}
	
	/* ============
	 * Action Maker
	 * ============ */

	// Configuration
	
	@Override
	public ModuleExecMedia getExecMedia() {
		return this;
	}

	@Override
	public boolean Flot_si(Condition condition) {
		return $(condition);
	}
	
	// Actions
	
	@Override
	public void Image_afficher(int numeroImage, String nomImage, FixeVariable xImage, FixeVariable yImage,
			int transparenceHaute, int transparenceBasse, int agrandissement, Couleur couleur, int saturation,
			TypeEffet typeEffet, int intensiteEffet, boolean transparence, boolean defilementAvecCarte) {
		
		if (numeroImage == SHOW_PIC_ID_WITH_NAME) {
			getElementsFiltres().forEach(monstre -> monstre.nom = nomImage);
		}
	}
	
	
	@Override
	public boolean variableFixe(int variable, Comparateur comparateur, ValeurFixe droite) {
		if (variable == VARIABLE_IDMONSTRE) {
			conditions.push(new ConditionOnMonsterId(true, comparateur, droite.valeur));
			return true;
		} else if (variable == VARIABLE_IDCOMBAT) {
			conditions.push(new ConditionOnMonsterId(false, comparateur, droite.valeur));
			return true;
		} else {
			return false;
		}
	}
}
