package fr.bruju.rmeventreader.implementation.detectiondeformules;

import java.util.List;

import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;
import java.util.Objects;

/**
 * Permet d'extraire la liste des attaques
 * 
 * @author Bruju
 *
 */
public class ListeDesAttaques {
	private final static String CHEMIN_ATTAQUES = "ressources\\Attaques.txt";
	
	public static class AttaqueALire {
		public final int numeroEvenementCommun;
		public final String nomPersonnage;
		public final String nomAttaque;
		
		public AttaqueALire(String serialisation) {
			String[] champs = serialisation.split(" ", 3);
			numeroEvenementCommun = Integer.parseInt(champs[0]);
			nomPersonnage = champs[1];
			nomAttaque = champs[2];
		}
		
		@Override
		public int hashCode() {
			return Objects.hashCode(numeroEvenementCommun);
		}

		@Override
		public boolean equals(Object object) {
			if (object instanceof AttaqueALire) {
				AttaqueALire that = (AttaqueALire) object;
				return this.numeroEvenementCommun == that.numeroEvenementCommun;
			}
			return false;
		}

		public static void afficher(List<AttaqueALire> attaquesALire) {
			int i = 0;
			for (AttaqueALire attaque : attaquesALire) {
				System.out.println(i + " " + attaque.nomPersonnage + " " + attaque.nomAttaque);
				i++;
			}
		}
	}
	
	/**
	 * Donne la liste des attaques existant dans le fichier Attaques.txt
	 * @return La liste des attaques dans le fichier Attaques.txt
	 */
	public static List<AttaqueALire> extraireAttaquesALire() {
		return LecteurDeFichiersLigneParLigne.listerRessources(CHEMIN_ATTAQUES, AttaqueALire::new);
	}
}
