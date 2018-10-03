package fr.bruju.rmeventreader.actionmakers.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.modele.Couleur;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum;
import fr.bruju.rmeventreader.actionmakers.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.modele.SonParam;

class GestionImages implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();
	
	@Override
	public void remplirMap(Map<Integer, TraiteurSansRetour> handlers, Map<Integer, Traiteur> classe2) {
		handlers.put(11110, this::afficherImage);
		handlers.put(11120, this::deplacerImage);
		handlers.put(11130, (e, p, s) -> e.Image_effacer(p[0]));
		
		
		handlers.put(11510, this::jouerMusique);
		handlers.put(11520, (e,p,s) -> e.Media_arreterMusique(p[0]));
		handlers.put(11530, (e,p,s) -> e.Media_memoriserMusique());
		handlers.put(11540, (e,p,s) -> e.Media_jouerMusiqueMemorisee());
		handlers.put(11550, this::jouerSon);
		handlers.put(11560, this::jouerFilm);
	}
	

	private void jouerFilm(ExecuteurInstructions executeur, int[] parametres, String nomFilm) {
		FixeVariable x = d.dechiffrerFixeVariable(parametres[0], parametres[1]);
		FixeVariable y = d.dechiffrerFixeVariable(parametres[0], parametres[2]);
		
		int longueur = parametres[3];
		int largeur = parametres[4];
		
		executeur.Media_jouerFilm(nomFilm, x, y, longueur, largeur);
	}
	

	private void jouerSon(ExecuteurInstructions executeur, int[] parametres, String nomSon) {
		SonParam parametresSonore = new SonParam(parametres[0], parametres[1], parametres[2]);	
		executeur.Media_jouerSon(nomSon, parametresSonore);
	}

	private void jouerMusique(ExecuteurInstructions executeur, int[] parametres, String nomMusique) {
		int tempsFondu = parametres[0];
		SonParam parametresMusicaux = new SonParam(parametres[1], parametres[2], parametres[3]);
		
		executeur.Media_jouerMusique(nomMusique, tempsFondu, parametresMusicaux);
	}
	
	private void deplacerImage(ExecuteurInstructions executeur, int[] parametres, String s) {
		int numeroImage = parametres[0];
		FixeVariable xImage = d.dechiffrerFixeVariable(parametres[1], parametres[2]);
		FixeVariable yImage = d.dechiffrerFixeVariable(parametres[1], parametres[3]);
		int transparenceHaute = parametres[6];
		int transparenceBasse = parametres.length > 16 ? parametres[16] : parametres[6];
		int agrandissement = parametres[5];
		
		Couleur couleur = new Couleur(parametres[8], parametres[9], parametres[10]);
		int saturation = parametres[11];
		
		ExecEnum.TypeEffet typeEffet = ExecEnum.TypeEffet.values()[parametres[12]];
		int intensiteEffet = parametres[13];
		
		int temps = parametres[14];
		boolean pause = parametres[15] == 1;
		
		executeur.Image_deplacer(numeroImage, xImage, yImage, transparenceHaute, transparenceBasse, agrandissement,
				couleur, saturation, typeEffet, intensiteEffet, temps, pause);
	}
	
	private void afficherImage(ExecuteurInstructions executeur, int[] parametres, String s) {
		int numeroImage = parametres[0];
		FixeVariable xImage = d.dechiffrerFixeVariable(parametres[1], parametres[2]);
		FixeVariable yImage = d.dechiffrerFixeVariable(parametres[1], parametres[3]);
		int transparenceHaute = parametres[6];
		int transparenceBasse = parametres.length > 14 ? parametres[14] : parametres[6];
		int agrandissement = parametres[5];
		
		Couleur couleur = new Couleur(parametres[8], parametres[9], parametres[10]);
		int saturation = parametres[11];
		
		ExecEnum.TypeEffet typeEffet = ExecEnum.TypeEffet.values()[parametres[12]];
		int intensiteEffet = parametres[13];
		boolean transparence = parametres[7] == 1;
		boolean defilementAvecCarte = parametres[4] == 1;
		
		executeur.Image_afficher(numeroImage, s, xImage, yImage, transparenceHaute, transparenceBasse, agrandissement,
				couleur, saturation, typeEffet, intensiteEffet, transparence, defilementAvecCarte);
	}

}
