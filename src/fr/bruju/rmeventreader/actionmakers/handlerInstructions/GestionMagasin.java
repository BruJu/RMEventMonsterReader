package fr.bruju.rmeventreader.actionmakers.handlerInstructions;

import java.util.Arrays;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;


class GestionMagasin implements Remplisseur {
	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers, Map<Integer, HandlerInstructionRetour> classe2) {
		handlers.put(10720, this::magasin);
		handlers.put(20720, (e, p, s) -> e.getExecIntegre().Magasin_magasinBrancheVente());
		handlers.put(20720, (e, p, s) -> e.getExecIntegre().Magasin_magasinBrancheNonVente());
		handlers.put(20720, (e, p, s) -> e.getExecIntegre().Magasin_magasinFinBranche());
		handlers.put(10730, this::auberge);
		handlers.put(20730, (e, p, s) -> e.getExecIntegre().Magasin_aubergeRepos());
		handlers.put(20731, (e, p, s) -> e.getExecIntegre().Magasin_aubergeNonRepos());
		handlers.put(20732, (e, p, s) -> e.getExecIntegre().Magasin_aubergeFinBranche());
	}

	private void auberge(ExecuteurInstructions executeur, int[] parametres, String s) {
		boolean type1 = parametres[0] == 0;
		int prix = parametres[1];
		
		executeur.getExecIntegre().Magasin_auberge(type1, prix);
	}
	
	@SuppressWarnings("unused")
	private void magasin(ExecuteurInstructions executeur, int[] parametres, String s) {
		boolean ventePossible = parametres[0] != 1;
		int dialogue = parametres[1];
		
		int[] objetsAchetables;
		
		if (parametres[0] == 2)
			objetsAchetables = null;
		else
			objetsAchetables = Arrays.copyOfRange(parametres, 4, parametres.length);
		
		executeur.getExecIntegre().Magasin_magasin(dialogue, objetsAchetables, ventePossible);
	}
}
