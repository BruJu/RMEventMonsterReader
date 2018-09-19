package fr.bruju.rmeventreader.implementation.random;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.DechiffreurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecMedia;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.Couleur;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.FixeVariable;
import fr.bruju.rmeventreader.dictionnaires.FabriqueMiLCFMiXML;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.TypeEffet;
import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMFabrique;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;



public class ChercheurDImages implements Runnable {
	private int numeroDeMap;
	private Map<Integer, Set<Integer>> utilisations = new HashMap<>();
	
	public ChercheurDImages(int numeroDeMap) {
		this.numeroDeMap = numeroDeMap;
	}
	
	@Override
	public void run() {
		RMFabrique usine = FabriqueMiLCFMiXML.getInstance();
		
		List<RMEvenement> evenements = usine.map(numeroDeMap).evenements();
		
		evenements.forEach(evenement -> {
			int idEvent = evenement.id();
			DechiffreurInstructions dechiffreur = new DechiffreurInstructions(new AnalyseurDInstructions(idEvent));
			
			evenement.pages().forEach(page -> dechiffreur.executer(page.instructions()));
		});
		
		utilisations.forEach((image, events) -> {
			System.out.println(image + ":" +
					events
						.stream()
						.map(numero -> convertir(numero, evenements))
						.collect(Collectors.joining(",")));
		});
	}
	
	
	
	private String convertir(Integer numero, List<RMEvenement> evenements) {
		for (RMEvenement evenement : evenements) {
			if (evenement.id() == numero) {
				return numero + "[" + evenement.nom() + ";" + evenement.x() + ";" + evenement.y() +"]";
			}
		}
		
		return "???";
	}



	public class AnalyseurDInstructions implements ExecuteurInstructionsTrue, ModuleExecMedia {
		public final int numeroEvent;

		private void utiliseImage(int numeroImage) {
			Utilitaire.Maps.ajouterElementDansSet(utilisations, numeroImage, numeroEvent);
		}

		@Override
		public ModuleExecMedia getExecMedia() {
			return this;
		}

		public AnalyseurDInstructions(int numeroEvent) {
			this.numeroEvent = numeroEvent;
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
	}
}
