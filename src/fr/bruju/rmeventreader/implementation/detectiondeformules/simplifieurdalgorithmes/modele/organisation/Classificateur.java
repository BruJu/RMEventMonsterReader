package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

public interface Classificateur {




	public class ClassificateurChaine implements Classificateur {
		public final String chaine;

		public ClassificateurChaine(String chaine) {
			this.chaine = chaine;
		}

		@Override
		public String toString() {
			return chaine;
		}
	}
}
