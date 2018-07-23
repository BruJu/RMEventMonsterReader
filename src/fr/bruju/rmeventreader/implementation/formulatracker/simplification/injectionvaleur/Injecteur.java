package fr.bruju.rmeventreader.implementation.formulatracker.simplification.injectionvaleur;

import fr.bruju.rmeventreader.implementation.formulatracker.formule.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.formule.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.simplification.ConstructeurDeComposant;

public class Injecteur extends ConstructeurDeComposant {

	
	
	private Injection injection;


	public Composant substituer(Injection injection, Composant valeur) {
		this.injection = injection;
		
		visit(valeur);
		
		return pile.pop();
	}
	
	
	
	@Override
	public void visit(BBase composant) {
		int numero = composant.numero;
		Boolean valeur = injection.getInterrupteur(numero);
		
		if (valeur == null) {
			pile.push(composant);
		} else {
			System.out.println("Injecte" + numero + " " + valeur);
			pile.push(BConstant.get(valeur));
		}
	}

	@Override
	public void visit(VBase composant) {
		int numero = composant.idVariable;
		injecterVariable(composant, numero);
	}



	private void injecterVariable(Valeur composant, int numero) {
		Integer valeur = injection.getVariable(numero);
		
		if (valeur == null) {
			pile.push(composant);
		} else {
			pile.push(new VConstante(valeur));
		}
	}
	
	
	@Override
	public void visit(VStatistique composant) {
		int numero = composant.idVariable;
		injecterVariable(composant, numero);
	}
	
	

}
