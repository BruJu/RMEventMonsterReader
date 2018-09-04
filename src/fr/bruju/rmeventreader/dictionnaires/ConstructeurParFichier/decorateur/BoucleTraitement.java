package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.decorateur;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.modele.Monteur;

/**
 * Réalise une boucle avec le traitement donné
 * @author Bruju
 *
 * @param <K> Le type de monteur utilisé
 */
public class BoucleTraitement<K extends Monteur<?>> implements Traitement<K> {
	/** Fournisseur de traîtement sur lequel la boucle s'effectue */
	private Supplier<Traitement<K>> supplier;
	/** Liste des traîtements opérés par la boucle */
	private List<Traitement<K>> traitementsCrees;
	/** Si non null, la boucle s'arrête si une ligne égale à fin est lue */
	private String fin;
	/** Traitement en cours d'application */
	private Traitement<K> instanceActuelle = null;

	/**
	 * Boucle sur le traitement donné
	 * @param supplier Un fournisseur de traîtement sur lequel on va boucler
	 */
	public BoucleTraitement(Supplier<Traitement<K>> supplier) {
		this.supplier = supplier;
		traitementsCrees = new ArrayList<>();
		fin = null;
	}

	/**
	 * Boucle sur le traitement donné
	 * @param supplier Un fournisseur de traîtement sur lequel on va boucler
	 * @param fin Ligne déclarant la fin de la boucle
	 */
	public BoucleTraitement(Supplier<Traitement<K>> supplier, String fin) {
		this.supplier = supplier;
		traitementsCrees = new ArrayList<>();
		this.fin = fin;
	}

	@Override
	public Avancement traiter(String ligne) {
		if (fin != null && ligne.equals(fin)) {
			decharger();
			return Avancement.FinTraitement;
		}
		
		if (instanceActuelle == null) {
			instanceActuelle = supplier.get();
		}
		
		Avancement resultat = instanceActuelle.traiter(ligne);
		
		switch (resultat) {
		case Rester:
			return Avancement.Rester;
		case Suivant:
			decharger();
			return Avancement.Rester;
		case SuivantDirect:

			decharger();
			//instanceActuelle = null;
			return traiter(ligne);
		case FinTraitement:
			decharger();
			return traiter(ligne);
		case Tuer:
			return Avancement.SuivantDirect;
		}
		
		throw new RuntimeException("Illegal");
	}

	/**
	 * Ajoute à la liste des traîtements à faire sur le monteur le traîtement en cours de lecture
	 */
	private void decharger() {
		if (instanceActuelle != null) {
			traitementsCrees.add(instanceActuelle);
			instanceActuelle = null;
		}
	}

	@Override
	public void appliquer(K monteur) {
		traitementsCrees.forEach(traitement -> traitement.appliquer(monteur));
	}
	
	@Override
	public boolean skippable() {
		decharger();
		return true;
	}
	

	@Override
	public String toString() {
		String s = traitementsCrees.stream().map(t -> t.toString()).collect(Collectors.joining("+"));
		
		return "BT<"+fin+"> (" + s + ")"; 
	}
}
