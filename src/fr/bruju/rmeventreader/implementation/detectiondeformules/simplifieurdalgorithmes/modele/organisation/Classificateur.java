package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation;

public interface Classificateur {

	public boolean estUnifiable(Classificateur autre);


	public class ClassificateurChaine implements Classificateur {
		public final String chaine;

		public ClassificateurChaine(String chaine) {
			this.chaine = chaine;
		}

		@Override
		public String toString() {
			return chaine;
		}

		@Override
		public boolean estUnifiable(Classificateur autre) {
			if (!(autre instanceof ClassificateurChaine)) {
				return false;
			}

			return chaine.equals(((ClassificateurChaine)autre).chaine);
		}
	}
}
