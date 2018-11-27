package fr.bruju.rmeventreader.implementation.detectiondeformules;

import java.util.List;

import fr.bruju.rmeventreader.utilitaire.LecteurDeFichiersLigneParLigne;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Une attaque à lire représente un triplet avec le numéro de l'évènement commun décrivant l'attaque, le nom du
 * personnage utilisant l'attaque et le nom de l'attaque
 *
 * @author Bruju
 *
 */
public class AttaqueALire {
	/* ===================
	 * COMPORTEMENT STATIC
	 * =================== */

	/** Fichier contenant la liste des attaques */
	private final static String CHEMIN_ATTAQUES = "ressources\\Attaques.txt";

	/**
	 * Donne la liste des attaques existant dans le fichier Attaques.txt
	 * @return La liste des attaques dans le fichier Attaques.txt
	 */
	public static List<AttaqueALire> extraireAttaquesALire() {
		AtomicBoolean ignorer = new AtomicBoolean(false);
		return LecteurDeFichiersLigneParLigne.listerRessources(CHEMIN_ATTAQUES, serialisation -> {
			if (ignorer.get()) {
				return null;
			}

			if (serialisation.equals("END")) {
				ignorer.set(true);
				return null;
			} else {
				return new AttaqueALire(serialisation);
			}
		});
	}

	/**
	 * Affiche la liste des attaques
	 * @param attaquesALire
	 */
	public static void afficher(List<AttaqueALire> attaquesALire) {
		int i = 0;
		for (AttaqueALire attaque : attaquesALire) {
			System.out.println("[" + i + "] " +  attaque.toString());
			i++;
		}
	}


	/* ==========================
	 * COMPORTEMENT D'UNE ATTAQUE
	 * ========================== */

	public final int numeroEvenementCommun;
	public final String nomPersonnage;
	public final String nomAttaque;

	/**
	 * Crée une attaque à partir d'une sérialisation au format "EvenementCommun Personnage Nom de l'attaque"
	 * @param serialisation La sérialisation
	 */
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

	@Override
	public String toString() {
		return numeroEvenementCommun + " " + nomPersonnage + " " + nomAttaque;
	}
}
