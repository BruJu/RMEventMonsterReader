package fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur;

import fr.bruju.rmeventreader.implementation.detectiondeformules._variables.EtatInitial;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Entree;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.detectiondeformules.recomposeur.visiteur.template.VisiteurConstructeur;

public class Injecteur extends VisiteurConstructeur {
	private final EtatInitial etatDesVariables;
	
	public Injecteur() {
		this.etatDesVariables = EtatInitial.getEtatInitial();
	}

	@Override
	protected Valeur modifier(Entree element) {
		int idVariable = element.id;
		
		// Dans la recomposition les interrupteurs sont les variables de 5001 à 10000. Dans EtatInitial, ce sont les
		// variables de -1 à -5000
		if (idVariable > 5000) { 
			idVariable = -(idVariable - 5000);
		}
		
		Integer valeur = etatDesVariables.getValeur(idVariable);
		
		return valeur == null ? element : new Constante(valeur);
	}
}
