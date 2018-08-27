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
				new ModificationDeVariables(),
				new GestionEquipe(),
				new GestionCombat(),
				new GestionSysteme(),
				new GestionMagasin(),
				new GestionJeu(),
				new GestionImages(),
				new ControleDeFlot()
		};
	}
	
	public void remplirMap(Map<Integer, HandlerInstruction> handlers);

}
