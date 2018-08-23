package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.header.Monteur;
import fr.bruju.rmeventreader.utilitaire.lambda.TriConsumer;

/**
 * Lit une ligne du type Cle Nombres...
 * 
 * @author Bruju
 *
 * @param <K> Type de monteur
 */
public class AssociationStringInt<K extends Monteur<?>> implements Traitement<K> {
	/** Action à appliquer au monteur */
	private TriConsumer<K, String, int[]> applicateur;
	/** Clé lue */
	private String cle;
	/** Valeurs lues */
	private int[] valeurs;
	
	/**
	 * Construit un traitement de lignes du type "Texte Nombres..." et applique au monteur la fonction applicateur
	 * @param applicateur La fonction d'application
	 */
	public AssociationStringInt(TriConsumer<K, String, int[]> applicateur) {
		this.applicateur = applicateur;
	}
	
	@Override
	public Avancement traiter(String ligne) {
		String[] split = ligne.split(" ");
		
		cle = split[0];
		
		valeurs = new int[split.length - 1];
		
		for (int i = 1 ; i != split.length ; i++) {
			valeurs[i - 1] = Integer.parseInt(split[i]);
		}
		
		return Avancement.Suivant;
	}

	@Override
	public void appliquer(K monteur) {
		applicateur.consume(monteur, cle, valeurs);
	}
	
	@Override
	public String toString() {
		return "AssociationStringInt";
	}
}
