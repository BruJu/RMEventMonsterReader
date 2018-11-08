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
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

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

	private void enleverDoublons() {
		for (List<Pair<RMMap, RMEvenement>> pairs : coffresExistants.values()) {
			if (pairs.size() <= 1) {
				continue;
			}

			enleverDoublons(pairs, this::sontVoisins);
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

	private <T> void enleverDoublons(List<T> nonExplores, BiPredicate<T, T> sontVoisins) {
		List<T> explores = new ArrayList<>();

		while (!nonExplores.isEmpty()) {
			T caseRemplie = Utilitaire.Pile.pop(nonExplores);
			explores.add(caseRemplie);

			Stack<T> voisins = new Stack<>();
			voisins.push(caseRemplie);

			while (!voisins.isEmpty()) {
				T caseDepilee = voisins.pop();

				int i = 0;
				while (i != nonExplores.size()) {
					T caseCandidateAuVoisinage = nonExplores.get(i);

					if (sontVoisins.test(caseDepilee, caseCandidateAuVoisinage)) {
						nonExplores.remove(i);
						voisins.push(caseCandidateAuVoisinage);
					} else {
						i++;
					}
				}
			}
		}

		nonExplores.addAll(explores);
	}

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


	private static class ExecuteurControleFin implements ExecuteurInstructions {
		private static int NUMERO_EVENEMENT_COMMUN_CONTROLE_COFFRe = 354;
		private final int interrupteur;

		private boolean appelTrouve = false;
		private boolean donneDesObjets = false;

		public boolean getResultat() {
			return appelTrouve && donneDesObjets;
		}

		public ExecuteurControleFin(int interrupteur) {
			this.interrupteur = interrupteur;
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


		@Override
		public void Variables_modifierArgent(boolean ajouter, FixeVariable quantite) {
			donneDesObjets = true;
		}

		private static int MONNAIE_BIS = 3937;

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
