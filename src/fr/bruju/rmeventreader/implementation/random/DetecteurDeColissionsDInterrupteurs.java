package fr.bruju.rmeventreader.implementation.random;

import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.rmobjets.RMPage;
import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.FixeVariable;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurDroiteVariable;
import fr.bruju.rmdechiffreur.modele.ValeurGauche;
import fr.bruju.rmeventreader.ProjetS;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

import java.util.*;

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
		enleverDoublons();
		afficherCoffresEnDouble();
	}

	/* =====================
	 * DETECTION DES COFFRES
	 * ===================== */

	private void detecterCoffre(RMMap map, RMEvenement evenement) {
		if (evenement.pages().size() != 2) {
			return;
		}

		int idInterrupteurPage2 = evenement.pages().get(1).conditionInterrupteur1();

		if (idInterrupteurPage2 == -1) {
			return;
		}

		RMPage page1 = evenement.pages().get(0);

		if (naPasDeCondition(page1) && possedeUnAppelAControleCoffre(page1, idInterrupteurPage2)) {
			Utilitaire.Maps.ajouterElementDansListe(coffresExistants, idInterrupteurPage2, new Pair<>(map, evenement));
		}
	}

	private boolean possedeUnAppelAControleCoffre(RMPage page, int idInterrupteur) {
		ExecuteurControleFin executeur = new ExecuteurControleFin(idInterrupteur);
		executeur.appliquerInstructions(page.instructions());
		return executeur.getResultat();
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


	/* ======================
	 * DETECTION DES DOUBLONS
	 * ====================== */

	private void enleverDoublons() {
		for (List<Pair<RMMap, RMEvenement>> pairs : coffresExistants.values()) {
			Utilitaire.filtrerParVoisinage(pairs, this::sontVoisins);
		}
	}

	private boolean sontVoisins(Pair<RMMap, RMEvenement> a, Pair<RMMap, RMEvenement> b) {
		if (!a.getLeft().equals(b.getLeft())) {
			return false;
		}

		RMEvenement evenementA = a.getRight();
		RMEvenement evenementB = b.getRight();

		return coordonneesVoisines(evenementA.x(), evenementB.x())
				&& coordonneesVoisines(evenementA.y(), evenementB.y())
				&& (evenementA.nom().equals(evenementB.nom())
				|| evenementA.nom().startsWith("EV") && evenementB.nom().startsWith("EV"));
	}

	private boolean coordonneesVoisines(int a, int b) {
		return a == b || a - 1 == b || a + 1 == b;
	}


	/* =====================
	 * AFFICHAGE DU RESULTAT
	 * ===================== */

	private void afficherCoffresEnDouble() {
		for (Map.Entry<Integer, List<Pair<RMMap, RMEvenement>>> paire : coffresExistants.entrySet()) {
			if (paire.getValue().size() > 1) {
				System.out.println(messageCollision(paire.getKey()));

				for (Pair<RMMap, RMEvenement> evenement : paire.getValue()) {
					System.out.println(messageEvenement(evenement.getLeft(), evenement.getRight()));
				}
			}
		}
	}

	private String messageCollision(int id) {
		return "= Collision sur l'interrupteur " + id + " " + ProjetS.PROJET.extraireInterrupteur(id);
	}

	private String messageEvenement(RMMap map, RMEvenement evenement) {
		return map.nom() + " : " + evenement.nom() + " " + evenement.x() + ";" + evenement.y();
	}
	

	/* ==================================
	 * EXECUTEUR DE DETECTION DES COFFRES
	 * ================================== */

	/**
	 * Un exécuteur qui renvoie vrai si l'évenement donne des objets ou de l'argent, en plus d'appeller l'évènement
	 * commun 354 ou d'activer l'interrupteur présent en page 2.
	 */
	private static class ExecuteurControleFin implements ExecuteurInstructions {
		private static int NUMERO_EVENEMENT_COMMUN_CONTROLE_COFFRe = 354;
		private static int MONNAIE_BIS = 3937;

		private final int interrupteur;

		private boolean appelTrouve = false;
		private boolean donneDesObjets = false;

		public ExecuteurControleFin(int interrupteur) {
			this.interrupteur = interrupteur;
		}

		public boolean getResultat() {
			return appelTrouve && donneDesObjets;
		}

		@Override
		public boolean getBooleenParDefaut() {
			return true;
		}

		// Recherche d'évènements dont un appel à un évènement spécifique est appelé ou qui active l'interrupteur
		// indiqué dans la page 2

		@Override
		public void Flot_appelEvenementCommun(int numero) {
			if (numero == NUMERO_EVENEMENT_COMMUN_CONTROLE_COFFRe) {
				appelTrouve = true;
			}
		}

		@Override
		public void Variables_changerSwitch(ValeurGauche valeurGauche, Boolean nouvelleValeur) {
			if (nouvelleValeur == Boolean.TRUE) {
				valeurGauche.appliquerG(idInterrupteur -> {
					if (idInterrupteur.idVariable == interrupteur) {
						appelTrouve = true;
					}

					return null;
				}, null, null);
			}
		}

		// Détection des évènements donnant des objets

		@Override
		public void Variables_modifierArgent(boolean ajouter, FixeVariable quantite) {
			donneDesObjets = true;
		}

		@Override
		public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
			valeurGauche.appliquerG(v -> {
				if (v.idVariable == MONNAIE_BIS) {
					donneDesObjets = true;
				}
				return null;
			}, null, null);
		}

		@Override
		public void Variables_changerVariable(ValeurGauche valeurGauche, OpMathematique operateur, ValeurDroiteVariable valeurDroite) {
			valeurGauche.appliquerG(v -> {
				if (v.idVariable == MONNAIE_BIS) {
					donneDesObjets = true;
				}
				return null;
			}, null, null);
		}

		@Override
		public void Variables_modifierObjets(boolean ajouter, FixeVariable objet, FixeVariable quantite) {
			donneDesObjets = true;
		}
	}
}
