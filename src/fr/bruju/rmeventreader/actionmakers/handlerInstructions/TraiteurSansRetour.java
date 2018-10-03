package fr.bruju.rmeventreader.actionmakers.handlerInstructions;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.controlleur.Ignorance;

public interface TraiteurSansRetour extends Traiteur {
	public void executer(ExecuteurInstructions executeur, int[] parametres, String chaine);

	@Override
	public default boolean traiter(ExecuteurInstructions executeur, int[] parametres, String chaine) {
		executer(executeur, parametres, chaine);
		return true;
	}

	@Override
	public default Ignorance creerIgnorance() {
		return null;
	}
}