package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.factorisation;

import java.util.function.BinaryOperator;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

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

		Operator operateurNormalise = Operator.sensConventionnel(vCalcul.operateur);

		return factoriser(vCalcul, (v, f) -> new VCalcul(v, vCalcul.operateur, f), operateurNormalise, vCalcul.gauche,
				vCalcul.droite);
	}

	public static int getNiveau(Operator operateur) {
		if (operateur == null)
			return 2;

		switch (operateur) {
		case MINUS:
		case PLUS:
			return 1;
		case TIMES:
		case DIVIDE:
			return 0;
		default:
			return -1;
		}
	}

	private Valeur factoriser(Valeur base, BinaryOperator<Valeur> fonctionDeCreation, Operator operateurBase,
			Valeur gauche, Valeur droite) {

		
		Valeur gaucheFact = gauche;
		Valeur droiteFact = droite;

		Operator operateurGauche = connaitreOperateur(gaucheFact);
		Operator operateurDroite = connaitreOperateur(gaucheFact);

		Operator operateurMax = operateurMax(operateurGauche, operateurDroite);

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
		RepresentationVariadique rfactoD = rg.factoriserDroite(rd);

		if (rfactoG == null && rfactoD == null) {
			if (gaucheFact == gauche && droiteFact == droite) {
				return base;
			} else {
				return fonctionDeCreation.apply(gaucheFact, droiteFact);
			}
		} else {
			if (rfactoD == null) {
				Valeur c = new VCalcul(rfactoG.convertirEnCalcul(), rfactoG.getPreOperateur(),
						fonctionDeCreation.apply(rg.convertirEnCalcul(), rd.convertirEnCalcul()));
				return c;
			} else {
				throw new UnsupportedOperationException();
			}
		}
	}

	private Operator operateurMax(Operator operateurGauche, Operator operateurDroite) {
		if (operateurGauche == Operator.PLUS || operateurDroite == Operator.PLUS) {
			return Operator.PLUS;
		}

		if (operateurGauche == Operator.TIMES || operateurDroite == Operator.TIMES) {
			return Operator.TIMES;
		}

		return null;
	}

	private Operator connaitreOperateur(Valeur v) {
		if (v instanceof VCalcul) {
			Operator op = ((VCalcul) v).operateur;

			op = Operator.sensConventionnel(op);

			return op;
		} else {
			return null;
		}
	}
}
