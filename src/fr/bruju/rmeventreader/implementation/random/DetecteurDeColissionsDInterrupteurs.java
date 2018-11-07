package fr.bruju.rmeventreader.implementation.random;

import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.rmobjets.RMPage;
import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmeventreader.ProjetS;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Cette classe permet de détecter les coffres qui utilisent le même interrupteur.
 * <br>Un coffre est défini comme étant un évènement à deux pages dont la première page n'a pas de condition et fait
 * un appel à l'évènement commun 354 et la seconde page a comme condition un interrupteur.
 * <br>On dit qu'il y a une collision si deux pages ont le même interrupteur en condition de deuxième page.
 */
public class DetecteurDeColissionsDInterrupteurs implements Runnable {
	/** Liste des interrupteurs utilisés pour des coffres */
	private Map<Integer, List<Pair<RMMap, RMEvenement>>> coffresExistants;

	@Override
	public void run() {
		coffresExistants = new HashMap<>();

		ProjetS.PROJET.explorerEvenementsSurCarte(this::detecterCoffre);

		for (Map.Entry<Integer, List<Pair<RMMap, RMEvenement>>> paire : coffresExistants.entrySet()) {
			if (paire.getValue().size() > 1) {
				System.out.println("= Collision sur l'interrupteur "
						+ paire.getKey() + " " + ProjetS.PROJET.extraireInterrupteur(paire.getKey()));

				for (Pair<RMMap, RMEvenement> evenement : paire.getValue()) {
					System.out.println(evenement.getLeft().nom() + " : " + evenement.getRight().nom()
					+ " " + evenement.getRight().x() + ";" + evenement.getRight().y());
				}
			}
		}
	}

	private void detecterCoffre(RMMap map, RMEvenement evenement) {
		if (evenement.pages().size() != 2) {
			return;
		}

		RMPage page1 = evenement.pages().get(0);

		if (!(naPasDeCondition(page1) && possedeUnAppelAControleCoffre(page1))) {
			return;
		}

		int idInterrupteurPage2 = evenement.pages().get(1).conditionInterrupteur1();

		if (idInterrupteurPage2 != -1) {
			Utilitaire.Maps.ajouterElementDansListe(coffresExistants, idInterrupteurPage2, new Pair<>(map, evenement));
		}
	}

	private boolean possedeUnAppelAControleCoffre(RMPage page) {
		ExecuteurControle executeur = new ExecuteurControle();
		executeur.appliquerInstructions(page.instructions());
		return executeur.appelTrouve;
	}

	private boolean naPasDeCondition(RMPage page) {
		return page.conditionChrono1() == -1
				&& page.conditionChrono2() == -1
				&& page.conditionHeros() == -1
				&& page.conditionInterrupteur1() == -1
				&& page.conditionInterrupteur2() == -1
				&& page.conditionObjet() == -1
				&& page.conditionVariable() == null;
	}

	/**
	 * Un exécuteur qui renvoie vrai avec getResultat() si il lit une instruction appellant l'évènement 354
	 */
	private static class ExecuteurControle implements ExecuteurInstructions {
		private static int NUMERO_EVENEMENT_COMMUN_CONTROLE_COFFRe = 354;

		private boolean appelTrouve = false;

		public boolean getResultat() {
			return appelTrouve;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		@Override
		public void Flot_appelEvenementCommun(int numero) {
			if (numero == NUMERO_EVENEMENT_COMMUN_CONTROLE_COFFRe) {
				appelTrouve = true;
			}
		}
	}
}
