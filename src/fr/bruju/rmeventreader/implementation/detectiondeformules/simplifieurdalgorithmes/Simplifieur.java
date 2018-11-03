package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes;

import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.implementation.detectiondeformules.AttaqueALire;
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

import static fr.bruju.rmeventreader.ProjetS.PROJET;


public class Simplifieur implements Runnable {
	private static final Transformateur[] simplifications = new Transformateur[] {
			new Borneur(),
			new InlinerGlobal(InlinerGlobal::lireLesVariablesVivantes),
			new DetermineurDeCiblage(),
			new SeparateurParHPDeMonstres(),
			new Tri()
	};
	
	
	@Override
	public void run() {
		BaseDAlgorithmes algorithmes = creerAlgorithmes();
		
		for (Transformateur simplification : simplifications) {
			algorithmes.transformer(simplification);
		}

		System.out.println(algorithmes.getString());
	}

	private BaseDAlgorithmes creerAlgorithmes() {
		return extraireAlgorithmesDesPersonnages();
	}

	private static BaseDAlgorithmes extraireAlgorithmesDesPersonnages() {
		List<AttaqueALire> attaquesALire = AttaqueALire.extraireAttaquesALire();

		BaseDePersonnages personnages = new BaseDePersonnages();
		BaseDAlgorithmes base = new BaseDAlgorithmes(personnages);

		Map<Integer, ExprVariable> variablesInstanciees = personnages.getVariablesInstanciees();

		for (AttaqueALire attaque : attaquesALire) {
			Executeur executeur = new Executeur(variablesInstanciees);
			PROJET.lireEvenementCommun(executeur, attaque.numeroEvenementCommun);
			Algorithme algorithme = executeur.extraireAlgorithme();

			Classificateur personnage = new Classificateur.ClassificateurChaine(attaque.nomPersonnage);
			Classificateur attaqueNom = new Classificateur.ClassificateurChaine(attaque.nomAttaque);

			base.ajouter(new AlgorithmeEtiquete(new Classificateur[]{personnage, attaqueNom}, algorithme));
		}

		return base;
	}

}
