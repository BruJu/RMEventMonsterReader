package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.operation.factorisation;

import java.util.function.BinaryOperator;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;
import fr.bruju.rmeventreader.implementation.formulatracker.contexte.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.exploitation.Maillon;

public class Factorisation extends ConstructeurDeComposantR implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		attaques.transformerComposants(this::traiter);
	}

	private ConstructeurDeRepresentationVariadique builder = new ConstructeurDeRepresentationVariadique();

	@Override
	protected Composant traiter(VTernaire variableTernaire) {
		Condition cFacto = (Condition) traiter(variableTernaire.condition);

		return factoriser(variableTernaire, (v, f) -> new VTernaire(cFacto, v, f), 2, variableTernaire.siVrai,
				variableTernaire.siFaux);
	}

	@Override
	protected Composant traiter(VCalcul vCalcul) {
		int niveau = getNiveau(vCalcul.operateur);

		if (niveau <= 0) {
			return vCalcul;
		}

		return factoriser(vCalcul, (v, f) -> new VCalcul(v, vCalcul.operateur, f), niveau, vCalcul.gauche,
				vCalcul.droite);
	}

	public static int getNiveau(Operator operateur) {
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

	private Valeur factoriser(Valeur base, BinaryOperator<Valeur> fonctionDeCreation, int niveauDOperateur,
			Valeur gauche, Valeur droite) {

		Valeur gaucheFact = (Valeur) traiter(gauche);
		Valeur droiteFact = (Valeur) traiter(droite);

		RepresentationVariadique rg = builder.creerRepresentationVariadique(gaucheFact);
		RepresentationVariadique rd = builder.creerRepresentationVariadique(droiteFact);

		RepresentationVariadique rfacto = rg.factoriser(rd, niveauDOperateur);

		if (rfacto == null) {
			if (gaucheFact == gauche && droiteFact == droite) {
				return base;
			} else {
				return fonctionDeCreation.apply(gaucheFact, droiteFact);
			}
		} else {
			return new VCalcul(fonctionDeCreation.apply(rg.convertirEnCalcul(), rd.convertirEnCalcul()),
					rfacto.getOperateur(), rfacto.convertirEnCalcul());
		}
	}
}
