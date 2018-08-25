package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Arrays;
import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum;

@SuppressWarnings("unused")
class GestionMagasin implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();
	
	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		handlers.put(10690, this::magasin);
		handlers.put(20720, (e, p, s) -> e.Magasin_magasinBrancheVente());
		handlers.put(20720, (e, p, s) -> e.Magasin_magasinBrancheNonVente());
		handlers.put(20720, (e, p, s) -> e.Magasin_magasinFinBranche());
		handlers.put(10730, this::auberge);
		handlers.put(20730, (e, p, s) -> e.Magasin_aubergeRepos());
		handlers.put(20731, (e, p, s) -> e.Magasin_aubergeNonRepos());
		handlers.put(20732, (e, p, s) -> e.Magasin_aubergeFinBranche());
		
		
	}

	private void auberge(ExecuteurInstructions executeur, int[] parametres, String s) {
		boolean type1 = parametres[0] == 0;
		int prix = parametres[1];
		
		executeur.Magasin_auberge(type1, prix);
	}
	
	private void magasin(ExecuteurInstructions executeur, int[] parametres, String s) {
		boolean ventePossible = parametres[0] != 1;
		int dialogue = parametres[1];
		
		int[] objetsAchetables;
		
		if (parametres[0] == 2)
			objetsAchetables = null;
		else
			objetsAchetables = Arrays.copyOfRange(parametres, 4, parametres.length);
		
		executeur.Magasin_magasin(dialogue, objetsAchetables, ventePossible);
	}


}
