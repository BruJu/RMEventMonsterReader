package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum;

@SuppressWarnings("unused")
class GestionCombat implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();
	
	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		handlers.put(10500, this::simulerAttaque);

		handlers.put(10710, this::lancerCombat);

		handlers.put(20710, (e, p, s) -> e.Combat_brancheVictoire());
		handlers.put(20711, (e, p, s) -> e.Combat_brancheFuite());
		handlers.put(20712, (e, p, s) -> e.Combat_brancheDefaite());
		handlers.put(20713, (e, p, s) -> e.Combat_finBranche());
	}

	private void lancerCombat(ExecuteurInstructions executeur, int[] parametres, String s) {
		FixeVariable idCombat = d.dechiffrerFixeVariable(parametres[0], parametres[1]);
		ExecEnum.ConditionsDeCombat conditions = ExecEnum.ConditionsDeCombat.values()[parametres[6]];
		ArrierePlanCombat arrierePlan = d.arrierePlan(parametres[2], parametres[7], parametres[8], s);
		boolean avantage = parametres[5] == 1;
		
		boolean defaiteAutorisee = parametres[4] == 1;
		ExecEnum.CombatComportementFuite fuite = ExecEnum.CombatComportementFuite.values()[parametres[3]];
		
		executeur.Combat_lancerCombat(idCombat, conditions, arrierePlan, fuite, defaiteAutorisee, avantage);
	}

	private void simulerAttaque(ExecuteurInstructions executeur, int[] parametres, String s) {
		ValeurMembre cible = d.dechiffrerMembreCible(parametres[0], parametres[1]);
		int puissance = parametres[2];
		int effetDefense = parametres[3];
		int effetIntel = parametres[4];
		int variance = parametres[5];
		int degatsEnregistresDansVariable = parametres[6] == 0 ? 0 : parametres[7];
		executeur.Combat_simulerAttaque(cible, puissance, effetDefense, effetIntel, variance,
				degatsEnregistresDansVariable);
	}
}
