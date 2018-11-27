package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.interfaces;

import fr.bruju.util.table.Table;

/**
 * Opération de transformation de table
 */
public interface TransformationDeTable {
	/**
	 * Modifie la table et renvoie la table transformée. La table peut être modifiée ou une nouvelle table peut être
	 * créée.
	 * @param table La table à modifier
	 * @return La table modifiée
	 */
    Table appliquer(Table table);
}
