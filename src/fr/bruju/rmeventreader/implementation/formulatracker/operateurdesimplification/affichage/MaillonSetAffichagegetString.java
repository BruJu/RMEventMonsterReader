package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.personnage.Statistique;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

// TODO : afficher les groupes de conditions de modif stat

/**
 * Détermine un affichage basique en utilisant les méthodes getString()
 * 
 * @author Bruju
 *
 */
public class MaillonSetAffichagegetString implements Maillon {
	@Override
	public void traiter(Attaques attaques) {

		attaques.determinerAffichageAttaques(nomAttaque -> "===" + nomAttaque + "===\n",
				
				(nomAttaque, modifStat, formule) ->
				new StringBuilder().append(getStatAffichage(modifStat.stat))
									.append(" ")
									.append(Utilitaire.getSymbole(modifStat.operateur))
									.append(" ")
									.append(formule.getString())
									.append("\n")
									.toString(),

				(nom) -> "");
	}

	/**
	 * Donne l'affichage du nom d'une statistique
	 */
	private static String getStatAffichage(Statistique stat) {
		return stat.possesseur.getNom() + "." + stat.nom;
	}

}