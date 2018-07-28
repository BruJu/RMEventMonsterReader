package fr.bruju.rmeventreader.implementation.formulatracker.composant.etendu;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.Valeur;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

public class E_Entre implements ComposantEtendu, Valeur {
	public final Valeur borneInf;
	public final Valeur valeur;
	public final Valeur borneSup;
	
	public E_Entre(Valeur borneInf, Valeur valeur, Valeur borneSup) {
		this.borneInf = borneInf;
		this.valeur = valeur;
		this.borneSup = borneSup;
	}

	@Override
	public String getString() {
		return "entre(" + borneInf.getString() + " ; " + valeur.getString() + " ; " + borneSup.getString() +")";
	}

	@Override
	public Composant getComposantNormal() {
		new RuntimeException().printStackTrace();
		
		
		return new E_Borne(new E_Borne(valeur, borneSup, true), borneInf, false).getComposantNormal();
	}


	@Override
	public Composant evaluationRapide() {
		return this;
	}
	

	@Override
	public void accept(VisiteurDeComposants visiteurDeComposants) {
		visiteurDeComposants.visit(this);
	}
}
