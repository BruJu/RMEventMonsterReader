package fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.affichage;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Attaques;
import fr.bruju.rmeventreader.implementation.formulatracker.operateurdesimplification.Maillon;

public class MaillonSetAffichagegetString implements Maillon {
	private int tMin;
	private int tMax;

	public MaillonSetAffichagegetString(int tMin, int tMax) {
		this.tMin = tMin;
		this.tMax = tMax;
	}

	@Override
	public void traiter(Attaques attaques) {
		attaques.determinerAffichage(

				formule -> {
					String chaine = formule.getString();

					if (chaine.length() < tMin) {
						chaine = "";
					} else if (chaine.length() > tMax) {
						chaine = "Trop long";
					}

					return chaine;
				},

				attaque -> "===" + attaque.nom + "===");
	}

}
