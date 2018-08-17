package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.header.Monteur;
import fr.bruju.rmeventreader.utilitaire.lambda.TriConsumer;

public class AssociationStringInt<K extends Monteur<?>> implements Traitement<K> {
	private TriConsumer<K, String, int[]> applicateur;
	private String cle;
	private int[] valeurs;
	
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
