package fr.bruju.rmeventreader.implementation.formulatracker.simplification.injectionvaleur;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.ConstructeurDeComposantR;

public class Injecteur extends ConstructeurDeComposantR {
	private Injection injection;
	
	public Composant substituer(Injection injection, Composant valeur) {
		this.injection = injection;
		
		return traiter(valeur);
	}
	
	@Override
	protected Composant traiter(BBase composant) {
		int numero = composant.numero;
		Boolean valeur = injection.getInterrupteur(numero);
		
		return (valeur == null) ? composant : BConstant.get(valeur);
	}

	@Override
	protected Composant traiter(VStatistique composant) {
		int numero = composant.statistique.position;
		return injecterVariable(composant, numero);
	}

	@Override
	protected Composant traiter(VBase composant) {
		int numero = composant.idVariable;
		return injecterVariable(composant, numero);
	}

	private Valeur injecterVariable(Valeur composant, int numero) {
		Integer valeur = injection.getVariable(numero);
		return (valeur == null) ? composant : new VConstante(valeur);
	}
}
