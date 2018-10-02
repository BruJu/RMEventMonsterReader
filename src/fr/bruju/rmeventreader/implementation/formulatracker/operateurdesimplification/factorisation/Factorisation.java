package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.factorisation;

import java.util.function.BinaryOperator;

import fr.bruju.rmeventreader.actionmakers.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

/**
 * Factorise partiellement les calculs dans la valeur
 * 
 * @author Bruju
 *
 */
public class Factorisation extends ConstructeurDeComposantsRecursif implements Maillon {
	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//  - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON -

	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//              - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT -

	/** Constructeur de représentations variadiques */
	private ConstructeurDeRepresentationVariadique builder = new ConstructeurDeRepresentationVariadique();

	@Override
	protected Composant modifier(VTernaire variableTernaire) {
		Condition cFacto = (Condition) traiter(variableTernaire.condition);

		return factoriser(variableTernaire, (v, f) -> new VTernaire(cFacto, v, f), null, variableTernaire.siVrai,
				variableTernaire.siFaux);
	}

	@Override
	protected Composant modifier(VCalcul vCalcul) {
		int niveau = getNiveau(vCalcul.operateur);

		if (niveau <= 0) {
			return vCalcul;
		}

		OpMathematique operateurNormalise = OpMathematique.sensConventionnel(vCalcul.operateur);

		return factoriser(vCalcul, (v, f) -> new VCalcul(v, vCalcul.operateur, f), operateurNormalise, vCalcul.gauche,
				vCalcul.droite);
	}

	
	/**
	 * Donne le niveau de l'opérateur
	 */
	public static int getNiveau(OpMathematique operateur) {
		// TODO : utiliser Utilitaire.getNiveau ?
		if (operateur == null)
			return 2;

		switch (operateur) {
		case MOINS:
		case PLUS:
			return 1;
		case FOIS:
		case DIVISE:
			return 0;
		default:
			return -1;
		}
	}

	private Valeur factoriser(Valeur base, BinaryOperator<Valeur> fonctionDeCreation, OpMathematique operateurBase,
			Valeur gauche, Valeur droite) {

		
		Valeur gaucheFact = gauche;
		Valeur droiteFact = droite;

		OpMathematique operateurGauche = connaitreOperateur(gaucheFact);
		OpMathematique operateurDroite = connaitreOperateur(gaucheFact);

		OpMathematique operateurMax = operateurMax(operateurGauche, operateurDroite);

		if (operateurMax == null || (operateurBase != null && operateurBase == operateurMax)) {
			// Pas de factorisation possible
			if (gaucheFact == gauche && droiteFact == droite) {
				return base;
			} else {
				return fonctionDeCreation.apply(gaucheFact, droiteFact);
			}
		}

		RepresentationVariadique rg = builder.creerRepresentationVariadique(gaucheFact, operateurMax);
		RepresentationVariadique rd = builder.creerRepresentationVariadique(droiteFact, operateurMax);

		RepresentationVariadique rfactoG = rg.factoriserGauche(rd);

		if (rfactoG == null) {
			if (gaucheFact == gauche) {
				return base;
			} else {
				return fonctionDeCreation.apply(gaucheFact, droiteFact);
			}
		}
			
		
		Valeur c = new VCalcul(rfactoG.convertirEnCalcul(), rfactoG.getPreOperateur(),
				fonctionDeCreation.apply(rg.convertirEnCalcul(), rd.convertirEnCalcul()));
		
		return c;
	}

	private OpMathematique operateurMax(OpMathematique operateurGauche, OpMathematique operateurDroite) {
		if (operateurGauche == OpMathematique.PLUS || operateurDroite == OpMathematique.PLUS) {
			return OpMathematique.PLUS;
		}

		if (operateurGauche == OpMathematique.FOIS || operateurDroite == OpMathematique.FOIS) {
			return OpMathematique.FOIS;
		}

		return null;
	}

	private OpMathematique connaitreOperateur(Valeur v) {
		if (v instanceof VCalcul) {
			OpMathematique op = ((VCalcul) v).operateur;

			op = OpMathematique.sensConventionnel(op);

			return op;
		} else {
			return null;
		}
	}
}
