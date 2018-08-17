package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

import java.util.function.BiConsumer;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;

public class TraitementObjet<K extends Monteur<?>, R> implements Traitement<K> {
	private SousObject<R, ?> sousObjet;
	private BiConsumer<K, R> operationDeMontage;
	
	public TraitementObjet(SousObject<R, ?> sousObjet, BiConsumer<K, R> operationDeMontage) {
		this.sousObjet = sousObjet;
		this.operationDeMontage = operationDeMontage;
	}

	@Override
	public Avancement traiter(String ligne) {
		Avancement reponse = sousObjet.traiter(ligne);
		return reponse;
	}

	@Override
	public void appliquer(K monteur) {
		operationDeMontage.accept(monteur, sousObjet.build());
	}
}
