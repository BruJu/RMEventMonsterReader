package fr.bruju.rmeventreader.implementation.formulareader.formule.valeur.simple;

/**
 * Variable portant un nom
 * @author Bruju
 *
 */
public class ValeurNommee extends ValeurVariable {
	/**
	 * Nom de la variable
	 */
	private String nom;
	
	/**
	 * Crée une varaible nommée
	 * @param idVar Numéro de la variable
	 * @param nom Le nom de la variable
	 */
	public ValeurNommee(int idVar, String nom) {
		super(idVar);
		this.nom = nom.substring(1);
	}
	
	@Override
	public String getString() {
		return nom;
	}
}
