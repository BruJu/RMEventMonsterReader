package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Couleur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum;

public class GestionImages implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();
	
	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		handlers.put(11110, this::afficherImage);
		
	}
	
	private void afficherImage(ExecuteurInstructions executeur, int[] parametres, String s) {
		int numeroImage = parametres[0];
		FixeVariable xImage = d.dechiffrerFixeVariable(parametres[1], parametres[2]);
		FixeVariable yImage = d.dechiffrerFixeVariable(parametres[1], parametres[3]);
		int transparenceHaute = parametres[6];
		int transparenceBasse = parametres[14];
		int agrandissement = parametres[5];
		
		Couleur couleur = new Couleur(parametres[8], parametres[9], parametres[10]);
		int saturation = parametres[11];
		
		ExecEnum.TypeEffet typeEffet = ExecEnum.TypeEffet.values()[parametres[12]];
		int intensiteEffet = parametres[13];
		boolean transparence = parametres[7] == 1;
		boolean defilementAvecCarte = parametres[4] == 1;
		
		executeur.afficherImage(numeroImage, s, xImage, yImage, transparenceHaute, transparenceBasse, agrandissement,
				couleur, saturation, typeEffet, intensiteEffet, transparence, defilementAvecCarte);
	}

}
