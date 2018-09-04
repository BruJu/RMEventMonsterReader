package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.decorateur;

import java.util.function.BiConsumer;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.ConvertisseurLigneVersObjet;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.modele.Monteur;

/**
 * Traîtement consistant à lire un objet
 * @author Bruju
 *
 * @param <K> Monteur de l'objet père
 * @param <R> Type de l'objet fils construit par ce traîtement
 */
public class TraitementObjet<K extends Monteur<?>, R> implements Traitement<K> {
	/** Convertisseur pour l'objet fils */
	private ConvertisseurLigneVersObjet<R, ?> sousObjet;
	/** Traitement à appliquer au père avec le fils */
	private BiConsumer<K, R> operationDeMontage;
	
	/**
	 * Traîtement consistant à lire un objet
	 * @param sousObjet Convertisseur pour l'objet fils
	 * @param operationDeMontage Traitement à appliquer au père avec le fils
	 */
	public TraitementObjet(ConvertisseurLigneVersObjet<R, ?> sousObjet, BiConsumer<K, R> operationDeMontage) {
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
	
	@Override
	public String toString() {
		return "TO(" + sousObjet.toString() +")";
	}
}
