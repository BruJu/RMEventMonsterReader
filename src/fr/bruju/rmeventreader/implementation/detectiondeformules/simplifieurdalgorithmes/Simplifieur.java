package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import fr.bruju.rmeventreader.implementation.detectiondeformules.AttaqueALire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.AjouteurDeTag;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.NouveauTransformateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.nouvellestransformations.RemplaceAlgorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.determinetypeciblage.DetermineurDeCiblage;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.fusiondepersonnages.SeparateurParHPDeMonstres;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.bornage.Borneur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.transformations.inliner.InlinerGlobal;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.AlgorithmeEtiquete;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.BaseDAlgorithmes;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.organisation.Classificateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.personnage.BaseDePersonnages;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Transformateur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.transformation.Tri;
import fr.bruju.util.table.Enregistrement;
import fr.bruju.util.table.Table;

import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Simplifieur implements Runnable {
    /*
	private static final Transformateur[] simplifications = new Transformateur[] {
			new Borneur(),
			new InlinerGlobal(InlinerGlobal::lireLesVariablesVivantes),
			new DetermineurDeCiblage(),
			new SeparateurParHPDeMonstres(),
			new Tri()
	};
	*/

	private static final NouveauTransformateur[] simplificationsN = new NouveauTransformateur[] {

	        new RemplaceAlgorithme(new Borneur()::simplifier),
            new RemplaceAlgorithme(new InlinerGlobal(InlinerGlobal::lireLesVariablesVivantes)::simplifier),
			new AjouteurDeTag("Ciblage", enregistrement -> DetermineurDeCiblage.creerClassification(enregistrement.get("Algorithme")))
    };
	
	
	@Override
	public void run() {
	    Table table = creerAlgorithmesTables();

	    for (NouveauTransformateur nouveauTransformateur : simplificationsN) {
	        table = nouveauTransformateur.appliquer(table);
        }

	    afficherAlgorithmes(table);
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

    private Table creerAlgorithmesTables() {
        List<AttaqueALire> attaquesALire = AttaqueALire.extraireAttaquesALire();

        BaseDePersonnages personnages = new BaseDePersonnages();

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
