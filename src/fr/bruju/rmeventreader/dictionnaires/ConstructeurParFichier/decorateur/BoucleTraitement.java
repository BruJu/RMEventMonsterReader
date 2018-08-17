package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.decorateur;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Avancement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
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
