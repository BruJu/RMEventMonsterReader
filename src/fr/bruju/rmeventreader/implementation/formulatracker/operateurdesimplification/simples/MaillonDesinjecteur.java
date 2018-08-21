package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.simples;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CFixe;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.Condition;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantsRecursif;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.CreateurDeGestionnaire;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.inclusion.gestionnairedecondition.GestionnaireDeCondition;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Le désinjecteur lit un fichier ressource du type "Nom de personnage IDVariable Valeur" et supprime les conditions en
 * rapport avec cette égalité pour les formules touchant les statistiques du personnage en question.
 * 
 * @author Bruju
 *
 */
public class MaillonDesinjecteur extends ConstructeurDeComposantsRecursif implements Maillon {
	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//  - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON - MAILLON -

	@Override
	public void traiter(Attaques attaques) {
		remplirAvecFichier("ressources/formulatracker/desinjection.txt");
		
		attaques.modifierFormules((nomStat, formule) -> Attaques.generalisationDeLaTransformationDeComposants(formule,
				composant -> desinjecter(composant, conditionsADesinjecter.get(nomStat))));
	}

	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//        - LECTURE DE RESSOURCES - LECTURE DE RESSOURCES - LECTURE DE RESSOURCES - LECTURE DE RESSOURCES - 

	/**
	 * Condition à désinjecter en fonction du nom du personnage
	 */
	private Map<String, List<CVariable>> conditionsADesinjecter;

	/**
	 * Rempli la carte de conditions à désinjecter en fonction du nom du fichier donné.
	 */
	private void remplirAvecFichier(String chemin) {
		conditionsADesinjecter = new HashMap<>();
		List<String[]> ressources;

		try {
			ressources = FileReaderByLine.lireFichier(chemin, 3);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		for (String[] tableau : ressources) {
			String nomDuMonstre = tableau[0];
			int idVariable = Integer.parseInt(tableau[1]);
			int valeur = Integer.parseInt(tableau[2]);

			CVariable condition = new CVariable(new VBase(idVariable), Operator.IDENTIQUE, new VConstante(valeur));
			Utilitaire.Maps.ajouterElementDansListe(conditionsADesinjecter, nomDuMonstre, condition);
		}
	}

	// =================================================================================================================
	// =================================================================================================================
	// =================================================================================================================
	//              - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT - CONSTRUCTEUR DE COMPOSANT -

	/**
	 * Gestionnaires de condition pour cette itération
	 */
	private Collection<GestionnaireDeCondition> gestionnaires;

	/**
	 * Désinjecte les conditions concernant le personnage
	 * 
	 * @param composant Le composant dont on souhaite désinjecter les membres
	 * @param conditionsAEnlever Les conditions à enlever
	 * @return Le composant sans les conditions que l'on considère defacto respectées.
	 */
	private Composant desinjecter(Composant composant, Collection<? extends Condition> conditionsAEnlever) {
		if (conditionsAEnlever == null) {
			return composant;
		}

		remplirConditions(conditionsAEnlever);
		return traiter(composant);
	}

	/**
	 * Rempli la liste des gestionnaire de condition
	 * 
	 * @param conditionsAEnlever Les conditions à désinjecter
	 */
	private void remplirConditions(Collection<? extends Condition> conditionsAEnlever) {
		CreateurDeGestionnaire createur = new CreateurDeGestionnaire();

		gestionnaires = conditionsAEnlever.stream().map(c -> createur.getGestionnaire(c)).collect(Collectors.toList());
	}

	@Override
	protected Composant modifier(CVariable conditionVariable) {
		CVariable c = conditionVariable;
		Condition r;

		for (GestionnaireDeCondition gestionnaire : gestionnaires) {
			r = gestionnaire.conditionVariable(c);

			Boolean ident = CFixe.identifier(r);

			if (ident != null) {
				return r;
			}
			c = (CVariable) r;
		}
		return c;
	}
}
