package fr.bruju.rmeventreader.implementation.random;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.StringJoiner;

import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMPage;
import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.Couleur;
import fr.bruju.rmdechiffreur.modele.FixeVariable;
import fr.bruju.rmdechiffreur.modele.ExecEnum.TypeEffet;
import fr.bruju.util.MapsUtils;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Module parcourant tous les évènements d'une carte et identifiant les images utilisées par chaque évènement pour
 * en afficher la liste
 * 
 * @author Bruju
 *
 */
public class ChercheurDImages implements Runnable {
	/** Numéro de la carte */
	private int numeroDeMap;
	/** Association entre numéro d'image et évènements l'utilisant */
	private Map<Integer, Set<EvenementLu>> utilisations = new HashMap<>();
	
	/**
	 * Crée un module de recherche des numéros d'image utilisé par chaque évènement
	 * @param numeroDeMap Numéro de la carte
	 */
	public ChercheurDImages(int numeroDeMap) {
		this.numeroDeMap = numeroDeMap;
	}
	
	@Override
	public void run() {
		PROJET.explorerCarte(numeroDeMap, this::lireUnePage);
		utilisations.forEach(this::afficherUneUtilisationDImage);
	}
	
	/**
	 * Rempli la table d'association id d'image - évènements avec la page donné 
	 * @param evenement L'évènement responsable de la génération de la page
	 * @param page La page qui contient les instructions
	 */
	private void lireUnePage(RMEvenement evenement, RMPage page) {
		PROJET.executer(new AnalyseurDInstructions(new EvenementLu(evenement)), page.instructions());
	}
	
	/**
	 * Affiche la liste des évènements utilisant l'image donné au format
	 * <pr>idImage:idEvenement1[Nom;x;y],idEvenement2[Nom;x;y] (...)
	 * @param idImg Le numéro de l'image
	 * @param utilisations La liste des évènements l'utilisant
	 */
	private void afficherUneUtilisationDImage(Integer idImg, Set<EvenementLu> utilisations) {
		StringJoiner sj = new StringJoiner(",");

		for (EvenementLu utilisation : utilisations) {
			sj.add(utilisation.toString());
		}

		System.out.println(idImg + ":" + sj.toString());
	}
	
	/** Représente les données qui seront affichées poru un évènement */
	private class EvenementLu implements Comparable<EvenementLu> {
		// On refait une classe car RMEvenement n'implémente pas Comparable
		/** Id de l'évènement */
		public final int id;
		/** Nom de l'évènement */
		public final String nom;
		/** Position en X de l'évènement */
		public final int x;
		/** Position en Y de l'évènement */
		public final int y;
		
		/**
		 * Crée un évènement lu à partir d'un RMEvenement
		 * @param evenement L'évènement à transformer
		 */
		public EvenementLu(RMEvenement evenement) {
			id = evenement.id();
			nom = evenement.nom();
			x = evenement.x();
			y = evenement.y();
		}
		
		@Override
		public String toString() {
			return id + "[" + nom + ";" + x + ";" + y + "]";
		}

		@Override
		public int compareTo(EvenementLu that) {
			return Integer.compare(id, that.id);
		}
	}
	
	/**
	 * Exécuteur chargé de répertorier dans la classe mère l'évènement qu'on lui a donné dans le constructeur pour
	 * toutes les images qu'on lui demandera de modifier à l'écran.
	 */
	public class AnalyseurDInstructions implements ExecuteurInstructions {
		/** Evènement à ajouter */
		public final EvenementLu evenement;
		
		/**
		 * Crée un analyseur d'insturction qui se chargera d'ajouter l'évènement donné pour tous les numéros d'image
		 * qu'on lui demande d'afficher
		 * @param evenement L'évènement
		 */
		public AnalyseurDInstructions(EvenementLu evenement) {
			this.evenement = evenement;
		}

		/**
		 * Ajoute dans la table des utilisations l'image numéroImage pour l'évènement de cet objet
		 * @param numeroImage Le numéro de l'image
		 */
		private void utiliseImage(int numeroImage) {
			MapsUtils.ajouterElementDansSet(utilisations, numeroImage, evenement);
		}
		
		@Override
		public void Image_afficher(int numeroImage, String nomImage, FixeVariable xImage, FixeVariable yImage,
				int transparenceHaute, int transparenceBasse, int agrandissement, Couleur couleur, int saturation,
				TypeEffet typeEffet, int intensiteEffet, boolean transparence, boolean defilementAvecCarte) {
			utiliseImage(numeroImage);
		}
	
		@Override
		public void Image_deplacer(int numeroImage, FixeVariable xImage, FixeVariable yImage, int transparenceHaute,
				int transparenceBasse, int agrandissement, Couleur couleur, int saturation, TypeEffet typeEffet,
				int intensiteEffet, int temps, boolean pause) {
			utiliseImage(numeroImage);
		}
	
		@Override
		public void Image_effacer(int id) {
			utiliseImage(id);
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}
	}
}
