package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.affichage;

import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.personnage.Statistique;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.Maillon;

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

		attaques.determinerAffichageAttaques(n -> "",
				nomAttaque -> "===" + nomAttaque + "===\n",
				
				(nomAttaque, modifStat, formule) ->
				new StringBuilder().append(getStatAffichage(modifStat.stat))
									.append(" ")
									.append(modifStat.operateur.symbole)
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