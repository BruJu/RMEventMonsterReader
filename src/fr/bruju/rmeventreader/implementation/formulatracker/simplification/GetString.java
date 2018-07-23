package fr.bruju.rmeventreader.implementation.formulatracker.simplification;

import fr.bruju.rmeventreader.implementation.formulatracker.composant.Composant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.ComposantTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BConstant;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.bouton.BTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CArme;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CSwitch;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.condition.CVariable;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VAleatoire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VBase;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VCalcul;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VConstante;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VStatistique;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.valeur.VTernaire;
import fr.bruju.rmeventreader.implementation.formulatracker.composant.visiteur.VisiteurDeComposants;

public class GetString implements VisiteurDeComposants {
	
	private StringBuilder sb;
	private int niveau;
	
	public void append() {
		sb.append("\n");
		for (int i = 0 ; i != niveau ; i++) {
			sb.append("    ");
		}
	}
	
	public String getString(Composant composant) {
		sb = new StringBuilder();
		niveau = 0;
		
		visit(composant);
		
		return sb.toString();
	}
	

	@Override
	public void visit(BBase composant) {
		sb.append(composant.getString());
	}

	@Override
	public void visit(BConstant composant) {
		sb.append(composant.getString());
	}

	@Override
	public void visit(VAleatoire composant) {
		sb.append(composant.getString());
	}

	@Override
	public void visit(VBase composant) {
		sb.append(composant.getString());
	}

	@Override
	public void visit(VConstante composant) {
		sb.append(composant.getString());
	}

	@Override
	public void visit(VStatistique composant) {
		sb.append(composant.getString());
	}

	@Override
	public void visit(VCalcul vCalcul) {
		sb.append(vCalcul.getString());
	}

	@Override
	public void visit(VTernaire vTernaire) {
		traiter(vTernaire);
	}

	private <T extends Composant> void traiter(ComposantTernaire<T> vTernaire) {
		sb.append("(").append(vTernaire.condition.getString()).append(") ?");
		niveau++;
		append();
		visit(vTernaire.siVrai);
		sb.append(" : ");
		append();
		visit(vTernaire.siFaux);
		niveau--;
		append();
	}

	@Override
	public void visit(BTernaire bTernaire) {
		traiter(bTernaire);
	}

	@Override
	public void visit(CArme cArme) {
		sb.append(cArme.getString());
	}

	@Override
	public void visit(CSwitch cSwitch) {
		sb.append(cSwitch.getString());
	}

	@Override
	public void visit(CVariable cVariable) {
		sb.append(cVariable.getString());
	}

}
