package fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.evaluationrapide;

public interface Reponse {
	
	public boolean estVrai();
	public int getValeur();
	
	
	public static class B implements Reponse {
		private boolean b;
		public B(boolean b) {
			this.b = b;
		}
		
		@Override
		public boolean estVrai() {
			return b;
		}

		@Override
		public int getValeur() {
			throw new UnsupportedOperationException("getValeur sur RBooleen");
		}
	}
	
	public static class V implements Reponse {
		private int v;
		public V(int v) {
			this.v = v;
		}
		@Override
		public boolean estVrai() {
			throw new UnsupportedOperationException("estVrai sur RInteger");
		}
		@Override
		public int getValeur() {
			return v;
		}
	}
}
