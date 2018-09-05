package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.ArrierePlanCombat;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurMembre;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.CombatComportementFuite;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.ConditionsDeCombat;

public interface ModuleExecIntegre {
	public static final ModuleExecIntegre NullFalse = new ModuleExecIntegre() {};
	public static final ModuleExecIntegre NullTrue = new ModuleExecIntegre() {
		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}
	};

	


	public default boolean Magasin_auberge(boolean type1, int prix) {
		return getBooleenParDefaut();
	}

	public default void Magasin_aubergeFinBranche() {
	}

	public default boolean Magasin_aubergeNonRepos() {
		return getBooleenParDefaut();
	}

	public default boolean Magasin_aubergeRepos() {
		return getBooleenParDefaut();
	}

	public default boolean Magasin_magasin(int dialogue, int[] objetsAchetables, boolean ventePossible) {
		return getBooleenParDefaut();
	}

	public default boolean Magasin_magasinBrancheNonVente() {
		return getBooleenParDefaut();
	}

	public default boolean Magasin_magasinBrancheVente() {
		return getBooleenParDefaut();
	}

	public default void Magasin_magasinFinBranche() {
	}


	public default boolean Combat_brancheDefaite() {
		return getBooleenParDefaut();
	}

	public default boolean Combat_brancheFuite() {
		return getBooleenParDefaut();
	}

	public default boolean Combat_brancheVictoire() {
		return getBooleenParDefaut();
	}


	public default boolean getBooleenParDefaut() {
		return false;
	}

	public default void Combat_finBranche() {
	}

	public default boolean Combat_lancerCombat(FixeVariable idCombat, ConditionsDeCombat conditions, ArrierePlanCombat arrierePlan,
			CombatComportementFuite fuite, boolean defaitePossible, boolean avantage) {
		return getBooleenParDefaut();
	}

	/**
	 * Simule une attaque sur la cible
	 * @param cible Cible de l'attaque
	 * @param puissance Puissance de l'attaque
	 * @param effetDefense Effet de la défense en %
	 * @param effetIntel Effet de l'intelligence en %
	 * @param variance Variance de l'attaque
	 * @param degatsEnregistresDansVariable 0 si pas d'enregistrement des dégâts infligés dans une variable, la
	 * variable sinon
	 */
	public default void Combat_simulerAttaque(ValeurMembre cible, int puissance, int effetDefense, int effetIntel,
			int variance, int degatsEnregistresDansVariable) {
	}
}
