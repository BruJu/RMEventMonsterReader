package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import java.util.function.Function;

public interface ArrierePlanCombat {
	public <T> T appliquer(
			Function<Aucun, T> fonctionAucun,
			Function<Specifique, T> fonctionSpecifique,
			Function<AssocieAuTerrain, T> fonctionTerrain);
	
	public static class Aucun implements ArrierePlanCombat {
		@Override
		public <T> T appliquer(Function<Aucun, T> fonctionAucun, Function<Specifique, T> fonctionSpecifique,
				Function<AssocieAuTerrain, T> fonctionTerrain) {
			return fonctionAucun == null ? null : fonctionAucun.apply(this);
		}
	}

	public static class Specifique implements ArrierePlanCombat {
		public final String nom;
		public final boolean equipiersSerres;
		
		public Specifique(String nom, boolean equipiersSerres) {
			this.nom = nom;
			this.equipiersSerres = equipiersSerres;
		}

		@Override
		public <T> T appliquer(Function<Aucun, T> fonctionAucun, Function<Specifique, T> fonctionSpecifique,
				Function<AssocieAuTerrain, T> fonctionTerrain) {
			return fonctionSpecifique == null ? null : fonctionSpecifique.apply(this);
		}
	}
	
	public static class AssocieAuTerrain implements ArrierePlanCombat {
		public final int numeroTerrain;
		
		public AssocieAuTerrain(int numeroTerrain) {
			this.numeroTerrain = numeroTerrain;
		}

		@Override
		public <T> T appliquer(Function<Aucun, T> fonctionAucun, Function<Specifique, T> fonctionSpecifique,
				Function<AssocieAuTerrain, T> fonctionTerrain) {
			return fonctionTerrain == null ? null : fonctionTerrain.apply(this);
		}
	}
}
