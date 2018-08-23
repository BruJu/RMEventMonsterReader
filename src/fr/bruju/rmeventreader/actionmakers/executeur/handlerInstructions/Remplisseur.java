package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

public interface Remplisseur {
	/**
	 * Donne tous les remplisseurs connus
	 * @return Un tableau avec tous les remplisseurs
	 */
	public static Remplisseur[] getAll() {
		return new Remplisseur[] {
				new AffichageDeMessages(),
				new ModificationDeVariables()
		};
	}
	
	public void remplirMap(Map<Integer, HandlerInstruction> handlers);

}
