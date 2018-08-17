package fr.bruju.rmeventreader.actionmakers.ConstructeurParFichier;

import java.util.ArrayList;
import java.util.List;

public class BoucleTraitement implements Traitement {
	private Traitement base;
	private List<Object> objets;

	public BoucleTraitement(Traitement base) {
		this.base = base;
		objets = new ArrayList<>();
	}

	@Override
	public Avancement traiter(String ligne) {
		Avancement resultat = base.traiter(ligne);
		
		if (resultat == Avancement.Suivant) {
			return Avancement.Rester;
		} else if (resultat == Avancement.Tuer) {
			return Avancement.SuivantDirect;
		} else {
			throw new RuntimeException("BoucleTraitement a reçu autre chose que suivant ou tuer");
		}
	}

	@Override
	public List<Object> resultat() {
		return objets;
	}

}
