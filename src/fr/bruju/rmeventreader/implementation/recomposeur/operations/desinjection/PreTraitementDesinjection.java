package fr.bruju.rmeventreader.implementation.recomposeur.operations.desinjection;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import fr.bruju.rmeventreader.implementation.recomposeur.Parametres;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionFixe;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.condition.ConditionValeur;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Algorithme;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Personnage;
import fr.bruju.rmeventreader.implementation.recomposeur.exploitation.Statistique;
import fr.bruju.rmeventreader.implementation.recomposeur.formulededegats.GroupeDeConditions;
import fr.bruju.rmeventreader.implementation.recomposeur.operations.interfaces.StructureDInjectionDeHeader;
import fr.bruju.rmeventreader.implementation.recomposeur.visiteur.template.VisiteurConstructeur;
import fr.bruju.rmeventreader.utilitaire.Pair;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class PreTraitementDesinjection implements StructureDInjectionDeHeader {

	private Map<String, Map<Integer, Integer>> cartesConnues;

	public PreTraitementDesinjection(Parametres parametres) {
		cartesConnues = new HashMap<>();

		List<String[]> donnees = parametres.getParametres("Desinjection");

		donnees.forEach(tableau -> {
			String nomPerso = tableau[0];
			Integer idVariable = Integer.decode(tableau[1]);
			Integer valeur = Integer.decode(tableau[2]);

			Map<Integer, Integer> map = cartesConnues.get(nomPerso);

			if (map == null) {
				map = new TreeMap<Integer, Integer>();
				cartesConnues.put(nomPerso, map);
			}

			map.put(idVariable, valeur);
		});
	}

	@Override
	public Iterable<Pair<GroupeDeConditions, Algorithme>> degrouper(Statistique stat, Algorithme algo) {
		return new Desinjection(algo, extraire(stat.possesseur)).recupererIterable();
	}

	@Override
	public String getNom() {
		return "Ciblage";
	}
	

	private Map<Integer, Integer> extraire(Personnage possesseur) {
		return cartesConnues.get(possesseur.getNom());
	}

	/**
	 * Visiteur constructeur statefull
	 * 
	 * @author Bruju
	 *
	 */
	private static class Desinjection extends VisiteurConstructeur {
		/* Résultats */
		private Algorithme resultat;
		private boolean desinjectionProduite;

		/* Traitement */
		private Map<Integer, Integer> listePaires;

		public Desinjection(Algorithme algorithme, Map<Integer, Integer> carte) {
			listePaires = carte;
			desinjectionProduite = false;
			this.resultat = traiter(algorithme);
			listePaires = null;
		}

		public Iterable<Pair<GroupeDeConditions, Algorithme>> recupererIterable() {
			return Utilitaire
					.toArrayList(new Pair<>(new GroupeDeConditions(new Ciblage(desinjectionProduite)), resultat));
		}

		@Override
		protected Condition modifier(ConditionValeur element) {
			Integer variableGauche = Entree.extraire(element.gauche);
			Integer evaluationDroite = Constante.evaluer(element.droite);

			if (variableGauche == null || evaluationDroite == null) {
				return element;
			}

			Integer valeurVoulue = listePaires.get(variableGauche);

			if (valeurVoulue == null) {
				return element;
			}

			desinjectionProduite = true;
			return ConditionFixe.get(evaluationDroite.equals(valeurVoulue));
		}

	}
}