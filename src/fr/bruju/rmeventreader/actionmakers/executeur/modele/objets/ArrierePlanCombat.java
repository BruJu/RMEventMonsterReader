package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

public interface ArrierePlanCombat {
	public static interface Visiteur<T> {
		public default T visit(ArrierePlanCombat arrierePlan) {
			return arrierePlan.accept(this);
		}

		public T visit(Aucun arrierePlan);
		public T visit(Specifique arrierePlan);
		public T visit(AssocieAuTerrain arrierePlan);
	}
	
	public static class Aucun implements ArrierePlanCombat {
		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
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
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
	}
	
	public static class AssocieAuTerrain implements ArrierePlanCombat {
		public final int numeroTerrain;
		
		public AssocieAuTerrain(int numeroTerrain) {
			this.numeroTerrain = numeroTerrain;
		}

		@Override
		public <T> T accept(Visiteur<T> visiteur) {
			return visiteur.visit(this);
		}
	}
	
	public <T> T accept(Visiteur<T> visiteur);
}
