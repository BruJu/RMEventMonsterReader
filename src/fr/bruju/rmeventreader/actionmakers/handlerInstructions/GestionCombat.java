package fr.bruju.rmeventreader.actionmakers.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.controlleur.Ignorance;
import fr.bruju.rmeventreader.actionmakers.modele.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum;
import fr.bruju.rmeventreader.actionmakers.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurMembre;

class GestionCombat implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();
	
	@Override
	public void remplirMap(Map<Integer, TraiteurSansRetour> handlers, Map<Integer, Traiteur> classe2) {
		handlers.put(10500, this::simulerAttaque);

		classe2.put(10710, new Traiteur() {
			@Override
			public boolean traiter(ExecuteurInstructions executeur, int[] parametres, String chaine) {
				FixeVariable idCombat = d.dechiffrerFixeVariable(parametres[0], parametres[1]);
				ExecEnum.ConditionsDeCombat conditions = ExecEnum.ConditionsDeCombat.values()[parametres[6]];
				ArrierePlanCombat arrierePlan = d.arrierePlan(parametres[2], parametres[7], parametres[8], chaine);
				boolean avantage = parametres[5] == 1;
				
				boolean defaiteAutorisee = parametres[4] == 1;
				ExecEnum.CombatComportementFuite fuite = ExecEnum.CombatComportementFuite.values()[parametres[3]];
				
				return executeur.Combat_lancerCombat(idCombat, conditions, arrierePlan, fuite, defaiteAutorisee,
						avantage);
			}

			@Override
			public Ignorance creerIgnorance() {
				return new Ignorance(10710, 20713);
			}
		});

		handlers.put(20710, new TraiteurSansRetour() {
			@Override
			public void executer(ExecuteurInstructions executeur, int[] parametres, String chaine) {
				executeur.Combat_brancheVictoire();
			}
		});

		handlers.put(20711, new TraiteurSansRetour() {
			@Override
			public void executer(ExecuteurInstructions executeur, int[] parametres, String chaine) {
				executeur.Combat_brancheFuite();
			}
		});

		handlers.put(20712, new TraiteurSansRetour() {
			@Override
			public void executer(ExecuteurInstructions executeur, int[] parametres, String chaine) {
				executeur.Combat_brancheDefaite();
			}
		});
		handlers.put(20713, (e, p, s) -> e.Combat_finBranche());
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
