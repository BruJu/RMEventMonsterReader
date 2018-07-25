package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.unification;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.Bouton;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.Personnage;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.PersonnageReel;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.personnage.PersonnageUnifie;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.FormuleDeDegats;


/**
 * En tant que constructeur de composants : unifie les composants en utilisant une base de personnages unifiés et le
 * personnage unifié actuellement. On dit qu'un composant est unifiable avec un autre si il est identique à cet autre
 * composant, avec pour seule exception que les statistiques peuvent être appartenues par des personnages différents.
 * <p> En tant que maillon, applique le processus d'unification à toutes les formules.
 * 
 * @author Bruju
 *
 */
public class Unificateur extends ConstructeurDeComposantR implements Maillon {
	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//  - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON -

	@Override
	public void traiter(Attaques attaques) {
		attaques.appliquerJusquaStabilite((f1, f2) -> {
			if (f1.operator != f2.operator
					|| f1.conditions.size() != f2.conditions.size()) {
				return null;
			}
			
			List<Condition> conditionsUnifiees = new ArrayList<>(f1.conditions.size());
			Valeur valeurUnifiee;
			
			viderPersonnageUnifieActuel();
			
			for (int i = 0 ; i != f1.conditions.size(); i++) {
				Condition cU = (Condition) unifier(f1.conditions.get(i), f2.conditions.get(i));
				
				if (cU == null)
					return null;
				
				conditionsUnifiees.add(cU);
			}
			
			valeurUnifiee = (Valeur) unifier(f1.formule, f2.formule);
			
			return (valeurUnifiee == null) ? null : new FormuleDeDegats(f1.operator, conditionsUnifiees, valeurUnifiee);
		});
	}


	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//              - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT -
	
	Map<Set<PersonnageReel>, PersonnageUnifie> personnagesUnifies = new HashMap<>();

	private Composant second;
	private PersonnageUnifie personnageUnifie;

	public void viderPersonnageUnifieActuel() {
		personnageUnifie = null;
	}
	
	public Composant unifier(Composant premier, Composant second) {
		this.second = second;

		return traiter(premier);
	}

	@Override
	protected Composant traiter(VStatistique p) {
		if (!(second instanceof VStatistique))
			return null;

		VStatistique s = (VStatistique) second;
		
		// Cas 1 : il s'agit de la même statistique chez le même personnage. L'unification est inutile
		if (p.equals(s)) {
			return p;
		}
		
		// Cas 2 : La statistique n'est pas la même chez des personnages différents. L'unification est impossible
		if (p.statistique.nom.equals(s.statistique.nom)) {
			return null;
		}
		
		// Cas 3 : La statistique est identique

		// Détermination du personnage unifié
		Personnage p1 = p.statistique.possesseur;
		Personnage p2 = p.statistique.possesseur;
		
		Set<PersonnageReel> personnagesReels = new TreeSet<>();
		personnagesReels.addAll(p1.getPersonnagesReels());
		personnagesReels.addAll(p2.getPersonnagesReels());

		if (personnageUnifie == null) {
			// Première unification rencontrée. On la réalise
			PersonnageUnifie pU = personnagesUnifies.get(personnagesReels);
			
			if (pU == null) {
				pU = new PersonnageUnifie(personnagesReels);
				personnagesUnifies.put(personnagesReels, pU);
			}
			
			return new VStatistique(pU.getStatistiques().get(p.statistique.nom));
		} else {
			if (personnageUnifie.getPersonnagesReels().equals(personnagesReels)) {
				// ok
				return new VStatistique(personnageUnifie.getStatistiques().get(p.statistique.nom));
			} else {
				// échec de l'unification
				return null;
			}
		}
	}

	/* ===============
	 * Noeuds composés
	 * =============== */

	/**
	 * Fonction permettant de traiter les fils d'un noeuds dont on a déterminé que si ses noeuds fils sont unifiables, alors
	 * le noeud est unifiable.
	 * @param p Le premier noeud à unifier
	 * @param s Le second noeud à unifier
	 * @param accesFils Une liste donnant les fonctions permettant d'accéder aux fils des noeuds
	 * @param fonctionDeCreation La fonction permettant de créer le noeud unifié si les deux noeuds sont unifiables
	 * @return null si le noeud n'est pas unifiable, le noeud unifié si il l'est
	 */
	private <T extends Composant> T traiterNoeudsFils(T p, T s, List<Function<T, Composant>> accesFils,
			Function<List<Composant>, T> fonctionDeCreation) {
		List<Composant> composantsUnifies = new ArrayList<>();
		boolean aBesoinDeCreerUnNouveauNoeud = false;
		
		for (Function<T, Composant> getFils : accesFils) {
			// Récupération des deux fils
			Composant cp = getFils.apply(p);
			Composant cs = getFils.apply(s);
			
			// Visite des fils
			this.second = cs;
			Composant filsUnifies = traiter(cp);
			
			if (filsUnifies == null) {
				return null;
			} else {
				composantsUnifies.add(filsUnifies);
				aBesoinDeCreerUnNouveauNoeud |= cs != filsUnifies;
			}
		}
		
		// Ne pas créer des noeuds inutiles
		if (aBesoinDeCreerUnNouveauNoeud) {
			return fonctionDeCreation.apply(composantsUnifies);
		} else {
			return p;
		}
	}

	@Override
	protected Composant traiter(CSwitch p) {
		if (!(second instanceof CSwitch))
			return null;

		// Vérification des éléments propres au composant
		CSwitch s = (CSwitch) second;

		if (s.valeur != p.valeur) {
			return null;
		}

		// Vérification des sous membres
		List<Function<CSwitch, Composant>> accesFils = new ArrayList<>();
		accesFils.add(e -> e.interrupteur);

		Function<List<Composant>, CSwitch> creation = list -> new CSwitch((Bouton) list.get(0), p.valeur);

		return traiterNoeudsFils(p, s, accesFils, creation);
	}

	@Override
	protected Composant traiter(BTernaire p) {
		if (!(second instanceof BTernaire))
			return null;

		// Vérification des éléments propres au composant
		BTernaire s = (BTernaire) second;

		// Vérification des sous membres
		List<Function<BTernaire, Composant>> accesFils = new ArrayList<>();
		accesFils.add(e -> e.condition);
		accesFils.add(e -> e.siVrai);
		accesFils.add(e -> e.siFaux);

		Function<List<Composant>, BTernaire> creation = list -> new BTernaire((Condition) list.get(0),
				(Bouton) list.get(1), (Bouton) list.get(2));

		return traiterNoeudsFils(p, s, accesFils, creation);
	}

	@Override
	protected Composant traiter(VTernaire p) {
		if (!(second instanceof VTernaire))
			return null;

		// Vérification des éléments propres au composant
		VTernaire s = (VTernaire) second;

		// Vérification des sous membres
		List<Function<VTernaire, Composant>> accesFils = new ArrayList<>();
		accesFils.add(e -> e.condition);
		accesFils.add(e -> e.siVrai);
		accesFils.add(e -> e.siFaux);

		Function<List<Composant>, VTernaire> creation = list -> new VTernaire((Condition) list.get(0),
				(Valeur) list.get(1), (Valeur) list.get(2));

		return traiterNoeudsFils(p, s, accesFils, creation);
	}

	@Override
	protected Composant traiter(CVariable p) {
		if (!(second instanceof CVariable))
			return null;

		// Vérification des éléments propres au composant
		CVariable s = (CVariable) second;

		if (p.operateur != s.operateur)
			return null;

		// Vérification des sous membres
		List<Function<CVariable, Composant>> accesFils = new ArrayList<>();
		accesFils.add(e -> e.gauche);
		accesFils.add(e -> e.droite);

		Function<List<Composant>, CVariable> creation = list -> new CVariable((Valeur) list.get(0), p.operateur,
				(Valeur) list.get(1));

		return traiterNoeudsFils(p, s, accesFils, creation);

	}

	@Override
	protected Composant traiter(VCalcul p) {
		if (!(second instanceof VCalcul))
			return null;

		// Vérification des éléments propres au composant
		VCalcul s = (VCalcul) second;

		if (p.operateur != s.operateur)
			return null;

		// Vérification des sous membres
		List<Function<VCalcul, Composant>> accesFils = new ArrayList<>();
		accesFils.add(e -> e.gauche);
		accesFils.add(e -> e.droite);

		Function<List<Composant>, VCalcul> creation = list -> new VCalcul((Valeur) list.get(0), p.operateur,
				(Valeur) list.get(1));

		return traiterNoeudsFils(p, s, accesFils, creation);

	}

	/* ===================
	 * Feuilles identiques
	 * =================== */

	private Composant siIdentiques(Composant premier) {
		if (Objects.equals(premier, second)) {
			return premier;
		} else {
			return null;
		}
	}

	@Override
	protected Composant traiter(BBase boutonBase) {
		return siIdentiques(boutonBase);
	}

	@Override
	protected Composant traiter(BConstant boutonConstant) {
		return siIdentiques(boutonConstant);
	}

	@Override
	protected Composant traiter(VConstante variableConstante) {
		return siIdentiques(variableConstante);
	}

	@Override
	protected Composant traiter(VAleatoire variableAleatoire) {
		return siIdentiques(variableAleatoire);
	}

	@Override
	protected Composant traiter(VBase variableBase) {
		return siIdentiques(variableBase);
	}

	@Override
	protected Composant traiter(CArme conditionArme) {
		return siIdentiques(conditionArme);
	}

}
