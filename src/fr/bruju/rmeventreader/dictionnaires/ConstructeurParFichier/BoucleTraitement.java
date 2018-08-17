package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;

public class BoucleTraitement<K extends Monteur<?>> implements Traitement<K> {
	private Supplier<Traitement<K>> supplier;

	private List<Traitement<K>> traitementsCrees;

	private String fin;
	
	private Traitement<K> instanceActuelle = null;

	public BoucleTraitement(Supplier<Traitement<K>> supplier) {
		this.supplier = supplier;
		traitementsCrees = new ArrayList<>();
		fin = null;
	}
	
	public BoucleTraitement(Supplier<Traitement<K>> supplier, String fin) {
		this.supplier = supplier;
		traitementsCrees = new ArrayList<>();
		this.fin = fin;
	}

	@Override
	public Avancement traiter(String ligne) {
		if (fin != null && ligne.equals(fin)) {
			return Avancement.SuivantDirect;
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
		case Tuer:
			return Avancement.SuivantDirect;
		default:
			return Avancement.Tuer;
		}
	}

	private void decharger() {
		traitementsCrees.add(instanceActuelle);
		instanceActuelle = null;
	}

	@Override
	public void appliquer(K monteur) {
		traitementsCrees.forEach(traitement -> traitement.appliquer(monteur));
	}
	
	@Override
	public boolean skippable() {
		return true;
	}
}
