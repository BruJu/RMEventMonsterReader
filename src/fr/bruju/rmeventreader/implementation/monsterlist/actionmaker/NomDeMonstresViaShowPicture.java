package fr.bruju.rmeventreader.implementation.monsterlist.actionmaker;

import java.util.Collection;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.actionmakers.donnees.ValeurFixe;
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
public class NomDeMonstresViaShowPicture extends StackedActionMaker<Monstre> {
	// Constantes
	/**
	 * Numéro de l'image qui affiche les noms
	 */
	private static final int SHOW_PIC_ID_WITH_NAME = 19;
	
	/**
	 * Numéro de la variable contenant l'id des monstres
	 */
	private static final int VARIABLE_IDMONSTRE = 559;
	
	/**
	 * Numéro de la variable contenant l'id du combat
	 */
	private static final int VARIABLE_IDCOMBAT  = 435;
	
	
	/* ==========================
	 * Instanciation de la classe
	 * ========================== */
	
	/**
	 * Base de données de monstre
	 */
	private MonsterDatabase database;
	
	/**
	 * Instancie le faiseur d'action avec la base de données à compléter
	 * @param database La base de données à compléter
	 */
	public NomDeMonstresViaShowPicture(MonsterDatabase database) {
		this.database = database;
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
	
	@Override
	public void showPicture(int id, String pictureName) {
		if (id != SHOW_PIC_ID_WITH_NAME) {
			return;
		}
		
		Collection<Monstre> monstres = this.getElementsFiltres();

		for (Monstre monstre : monstres) {
			monstre.nom = pictureName;
		}
	}

	@Override
	public boolean condOnVariable(int leftOperandValue, Operator operatorValue, ValeurFixe returnValue) {
		if (!(leftOperandValue == VARIABLE_IDMONSTRE || leftOperandValue == VARIABLE_IDCOMBAT)) {
			return false;
		}
		
		conditions.push(new ConditionOnMonsterId(leftOperandValue == VARIABLE_IDMONSTRE, operatorValue, returnValue.get()));
		
		return true;
	}

}
