package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import fr.bruju.rmeventreader.dictionnaires.header.Monteur;

public class BoucleTraitement<K extends Monteur<?>> implements Traitement<K> {
	private Supplier<Traitement<K>> supplier;

	private List<Traitement<K>> traitementsCrees;

	public BoucleTraitement(Supplier<Traitement<K>> supplier) {
		this.supplier = supplier;
		traitementsCrees = new ArrayList<>();
	}

	@Override
	public Avancement traiter(String ligne) {
		Traitement<K> nouvelleInstance = supplier.get();
		
		Avancement resultat = nouvelleInstance.traiter(ligne);
		
		if (resultat == Avancement.Suivant) {
			traitementsCrees.add(nouvelleInstance);
			return Avancement.Rester;
		} else if (resultat == Avancement.Tuer) {
			return Avancement.SuivantDirect;
		} else {
			throw new RuntimeException("BoucleTraitement a reçu autre chose que suivant ou tuer");
		}
	}

	@Override
	public void appliquer(K monteur) {
		traitementsCrees.forEach(traitement -> traitement.appliquer(monteur));
	}
}
