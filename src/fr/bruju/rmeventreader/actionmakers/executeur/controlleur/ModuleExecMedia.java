package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.Couleur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.FixeVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.SonParam;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.TypeEffet;

public interface ModuleExecMedia {
	public static final ModuleExecMedia Null = new ModuleExecMedia() {};


	public default void Media_arreterMusique(int tempsFondu) {
		
	}
	public default void Media_jouerFilm(String nomFilm, FixeVariable x, FixeVariable y, int longueur, int largeur) {
		
	}

	public default void Media_jouerMusique(String nomMusique, int tempsFondu, SonParam parametresMusicaux) {
		
	}

	public default void Media_jouerMusiqueMemorisee() {
		
	}

	public default void Media_jouerSon(String nomSon, SonParam parametresSonore) {
		
	}

	public default void Media_memoriserMusique() {
		
	}

	public default void Image_afficher(int numeroImage, String nomImage, FixeVariable xImage, FixeVariable yImage, int transparenceHaute,
			int transparenceBasse, int agrandissement, Couleur couleur, int saturation, TypeEffet typeEffet,
			int intensiteEffet, boolean transparence, boolean defilementAvecCarte) {
		
	}
	public default void Image_deplacer(int numeroImage, FixeVariable xImage, FixeVariable yImage, int transparenceHaute,
			int transparenceBasse, int agrandissement, Couleur couleur, int saturation, TypeEffet typeEffet,
			int intensiteEffet, int temps, boolean pause) {
		
	}
	
	public default void Image_effacer(int id) {
	}
	
	
}
