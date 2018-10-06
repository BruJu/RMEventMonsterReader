package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques;

import java.util.Objects;

/**
 * Identifie une attaque avec le nom de l'attaque et le nom du lanceur
 * @author Bruju
 *
 */
public class IdentiteAttaque {
	/** Nom du lanceur */
	public final String nomLanceur;
	/** Nom de l'attaque */
	public final String nomAttaque;
	
	/**
	 * Crée un nouvel identifiant d'une attaque
	 * @param nomAttaque Nom de l'attaque
	 * @param nomLanceur Nom du lanceur
	 */
	public IdentiteAttaque(String nomAttaque, String nomLanceur) {
		this.nomAttaque = nomAttaque;
		this.nomLanceur = nomLanceur;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nomLanceur, nomAttaque);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof IdentiteAttaque) {
			IdentiteAttaque that = (IdentiteAttaque) object;
			return Objects.equals(this.nomLanceur, that.nomLanceur) && Objects.equals(this.nomAttaque, that.nomAttaque);
		}
		return false;
	}
}
