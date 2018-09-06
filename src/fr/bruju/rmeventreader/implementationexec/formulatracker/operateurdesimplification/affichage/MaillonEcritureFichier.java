package fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.affichage;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import fr.bruju.rmeventreader.implementationexec.formulatracker.formule.attaques.Attaques;
import fr.bruju.rmeventreader.implementationexec.formulatracker.operateurdesimplification.Maillon;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

/**
 * Maillon écrivant les résultats dans le fichier sorties/sortieTIMESTAMP.txt
 * 
 * @author Bruju
 *
 */
public class MaillonEcritureFichier implements Maillon {
	@Override
	public void traiter(Attaques attaques) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());

		Utilitaire.Fichier_Ecrire("sorties/sortie" + sdf.format(timestamp) + ".txt", attaques.getAffichage());
	}


}
