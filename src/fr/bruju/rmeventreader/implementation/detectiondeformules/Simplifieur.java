package fr.bruju.rmeventreader.implementation.detectiondeformules;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.interfaces.TransformationDeTable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.ClassificationCible;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.fusiondepersonnages.ClassificateurMonstreCible;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.fusiondepersonnages.SeparateurParHPDeMonstres;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.bornage.Borneur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.fusiondepersonnages.UnifierSubstitutions;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.inliner.InlineurDAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.modele.personnage.BaseDePersonnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.transformation.ListeurDeStatistiquesModifiees;
import fr.bruju.util.table.Enregistrement;
import fr.bruju.util.table.Table;

import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Simplifieur implements Runnable {
	private static TransformationDeTable[] getTransformationsAAppliquer(BaseDePersonnages baseDePersonnages) {
		return new TransformationDeTable[] {
				new Borneur(),
				new ListeurDeStatistiquesModifiees(),
				new InlineurDAlgorithme(),
				new ClassificationCible.Determineur(),
				new SeparateurParHPDeMonstres(baseDePersonnages),
				new UnifierSubstitutions(baseDePersonnages),
				table -> { table.retirerChamp("Sorties"); return table;}
		};
	}

	
	@Override
	public void run() {
		BaseDePersonnages personnages = new BaseDePersonnages();

		TransformationDeTable[] transformationsAAplliquer = getTransformationsAAppliquer(personnages);

	    Table table = creerAlgorithmesTables(personnages);

	    for (TransformationDeTable transformation : transformationsAAplliquer) {
	        table = transformation.appliquer(table);
        }

        table.reordonner(creerComparateurs());

	    afficherAlgorithmes(table);
	}

	private Iterable<Comparator<Enregistrement>> creerComparateurs() {
		List<Comparator<Enregistrement>> comparateurs = new ArrayList<>();

		comparateurs.add(Simplifieur.<String>creerComparateur("Personnage", (s1, s2) -> s1.compareTo(s2)));
		comparateurs.add(Simplifieur.<String>creerComparateur("Attaque", (s1, s2) -> s1.compareTo(s2)));
		comparateurs.add(Simplifieur.<ClassificationCible>creerComparateur("Ciblage",
				(s1, s2) -> s1.cibleChoisie.compareTo(s2.cibleChoisie)));
		comparateurs.add(Simplifieur.creerComparateur("Monstre", ClassificateurMonstreCible::comparateur));

		return comparateurs;
	}

	private static <T> Comparator<Enregistrement> creerComparateur(String nomChamp, BiFunction<T, T, Integer>
			comparateur) {
		return (e1, e2) -> {
			T c1 = e1.<T>get(nomChamp);
			T c2 = e2.<T>get(nomChamp);
			return comparateur.apply(c1, c2);
		};
	}


	private void afficherAlgorithmes(Table table) {
	    StringBuilder sb = new StringBuilder();
	    table.forEach(enregistrement -> ajouterAlgorithme(sb, enregistrement));
	    System.out.println(sb.toString());
    }

    private void ajouterAlgorithme(StringBuilder sb, Enregistrement enregistrement) {
	    AtomicReference<String> algorithme = new AtomicReference<>("??");

	    sb.append("--");

	    enregistrement.reconstruireObjet((nomChamp, objet) -> {
	        if (nomChamp.equals("Algorithme")) {
	            algorithme.set( ((Algorithme)objet).getString());
            } else {
                sb.append(" ").append(objet.toString());
            }
        });

	    sb.append(" --\n");
	    sb.append(algorithme.get());
	    sb.append("\n\n");
    }

    private Table creerAlgorithmesTables(BaseDePersonnages personnages) {
        List<AttaqueALire> attaquesALire = AttaqueALire.extraireAttaquesALire();

        Table table = new Table();
        table.insererChamp(0, "Personnage", null);
        table.insererChamp(1, "Attaque", null);
        table.insererChamp(2, "Algorithme", null);

        Map<Integer, ExprVariable> variablesInstanciees = personnages.getVariablesInstanciees();

        for (AttaqueALire attaque : attaquesALire) {
            Executeur executeur = new Executeur(variablesInstanciees);
            PROJET.lireEvenementCommun(executeur, attaque.numeroEvenementCommun);
            Algorithme algorithme = executeur.extraireAlgorithme();

            List<Object> objets = new ArrayList<>();

            objets.add(attaque.nomPersonnage);
            objets.add(attaque.nomAttaque);
            objets.add(algorithme);

            table.ajouterContenu(objets);
        }

        return table;
    }
}
