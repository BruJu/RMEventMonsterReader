package fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.fusiondepersonnages;

import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.BlocConditionnel;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionAffectation;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.ConditionObjet;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.condition.ConditionVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.Expression;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage.BaseDePersonnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage.Personnage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.interfaces.TransformationDeTable;
import fr.bruju.util.table.Enregistrement;
import fr.bruju.util.table.Table;

import java.util.function.Supplier;

public class UnifierSubstitutions implements TransformationDeTable {
	private final BaseDePersonnages baseDePersonnages;

	public UnifierSubstitutions(BaseDePersonnages baseDePersonnages) {
		this.baseDePersonnages = baseDePersonnages;
	}

	@Override
	public Table appliquer(Table table) {
		table.unifier(this::unificateur);
		return table;
	}


	private Enregistrement unificateur(Enregistrement e1, Enregistrement e2) {
		if (!e1.get("Personnage").equals(e2.get("Personnage"))
				|| !e1.get("Attaque").equals(e2.get("Attaque"))) {
			return null;
		}

		ClassificateurMonstreCible classificateur1 = e1.get("Monstre");
		ClassificateurMonstreCible classificateur2 = e2.get("Monstre");

		Personnage p1 = classificateur1.getPersonnage();
		Personnage p2 = classificateur2.getPersonnage();
		Personnage unifie = baseDePersonnages.getPersonnageUnifie(p1, p2);

		ContexteDeSubstitution contexte = new ContexteDeSubstitution(p2, p1);
		ContexteDeSubstitution contexte2 = new ContexteDeSubstitution(p1, unifie);

		Algorithme a1 = e1.get("Algorithme");
		Algorithme a2 = e2.get("Algorithme");

		Algorithme nouvelAlgorithme = combiner(a1, a2, contexte, contexte2);

		if (nouvelAlgorithme == null) {
			return null;
		}

		e1.set("Algorithme", nouvelAlgorithme);
		e1.set("Monstre", new ClassificateurMonstreCible(unifie.getStatistique(classificateur1.getStatistique().nom)));

		return e1;
	}

	private Algorithme combiner(Algorithme a1, Algorithme a2,
								ContexteDeSubstitution contexteEgal, ContexteDeSubstitution contexteDefini) {
		Algorithme resultat = new Algorithme();

		Supplier<InstructionGenerale> iterateur1 = a1.getListeurDInstructionsEffectives();
		Supplier<InstructionGenerale> iterateur2 = a2.getListeurDInstructionsEffectives();

		while (true) {
			InstructionGenerale i1 = iterateur1.get();
			InstructionGenerale i2 = iterateur2.get();

			if (i1 == null || i2 == null) {
				if (i1 == null && i2 == null) {
					return resultat;
				} else {
					return null;
				}
			}

			if (i1.getClass() != i2.getClass()) {
				return null;
			}

			if (i1 instanceof InstructionAffectation) {
				InstructionAffectation iaff1 = (InstructionAffectation) i1;
				InstructionAffectation iaff2 = (InstructionAffectation) i2;

				InstructionAffectation iaff2modifie = contexteEgal.substituer(iaff2);

				if (iaff1.estIdentique(iaff2modifie)) {
					contexteEgal.enregistrerSubstitution(iaff2, iaff1);

					resultat.ajouterInstruction(contexteDefini.substituer(iaff1));
				} else {
					return null;
				}
			}

			if (i1 instanceof BlocConditionnel) {
				BlocConditionnel bloc1 = (BlocConditionnel) i1;
				BlocConditionnel bloc2 = (BlocConditionnel) i2;

				Condition c1 = bloc1.condition;
				Condition c2 = bloc2.condition;
				Condition condition;

				if (c1 instanceof ConditionVariable && c2 instanceof ConditionVariable) {
					ConditionVariable cv1 = (ConditionVariable) c1;
					ConditionVariable cv2 = (ConditionVariable) c2;

					if (cv1.comparateur != cv2.comparateur) {
						return null;
					}

					Expression gauche2Substitue = contexteEgal.explorer(cv2.gauche);
					Expression droite2Substitue = contexteEgal.explorer(cv2.droite);

					if (!cv1.gauche.equals(gauche2Substitue) || !cv2.droite.equals(droite2Substitue)) {
						return null;
					}

					condition = new ConditionVariable(contexteDefini.explorer(cv1.gauche),
							cv1.comparateur,
							contexteDefini.explorer(cv1.droite));
				} else if (c1 instanceof ConditionObjet && c2 instanceof ConditionObjet) {
					if (!c1.equals(c2)) {
						return null;
					}

					condition = c1;
				} else {
					return null;
				}

				Algorithme vrai = combiner(bloc1.siVrai, bloc2.siVrai, contexteEgal, contexteDefini);
				if (vrai == null) return null;

				Algorithme faux = combiner(bloc1.siFaux, bloc2.siFaux, contexteEgal, contexteDefini);
				if (faux == null) return null;

				resultat.ajouterInstruction(new BlocConditionnel(condition, vrai, faux));
			}
		}
	}


}
