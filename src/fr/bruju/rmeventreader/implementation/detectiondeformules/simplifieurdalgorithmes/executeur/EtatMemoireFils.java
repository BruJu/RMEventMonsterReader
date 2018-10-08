package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import java.util.HashMap;
import java.util.Map;

import fr.bruju.rmdechiffreur.modele.Comparateur;
import fr.bruju.rmdechiffreur.modele.Condition.CondHerosPossedeObjet;
import fr.bruju.rmdechiffreur.modele.Condition.CondInterrupteur;
import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmdechiffreur.modele.ValeurFixe;
import fr.bruju.rmdechiffreur.modele.Variable;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.Algorithme;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.algorithme.InstructionGenerale;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.condition.Condition;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.CaseMemoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.Constante;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.NombreAleatoire;
import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.VariableInstanciee;
import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class EtatMemoireFils extends EtatMemoire {
	public final EtatMemoire pere;
	
	private Map<Integer, CaseMemoire> pereOnly = new HashMap<>();
	
	
	
	public EtatMemoireFils getFrere() {
		return pere.filsDroit;
	}
	
	public EtatMemoire revenirAuPere() {
		pere.accumulerFils();
		return pere;
	}
	
	
	
	public EtatMemoireFils() {
		pere = null;
	}

	public EtatMemoireFils(EtatMemoire pere) {
		this.pere = pere;
	}
	
	

	




	
	
	
	
	
	
	
	

	
	public VariableInstanciee getValeur(int numeroDeCase) {
		if (this.variablesActuelles.containsKey(numeroDeCase)) {
			return variablesActuelles.get(numeroDeCase).nouvelleInstance();
		} else if (this.pere != null) {
			return pere.getNouvelleInstanciation(numeroDeCase);
		} else {
			return Utilitaire.Maps.getX(pereOnly, numeroDeCase, () -> new CaseMemoire(numeroDeCase)).premiereInstance();
		}
	}
	
	public VariableInstanciee getNouvelleInstanciation(int numeroDeCase) {
		if (this.variablesActuelles.containsKey(numeroDeCase)) {
			return variablesActuelles.get(numeroDeCase).nouvelleInstance();
		} else if (this.pere != null) {
			return pere.getNouvelleInstanciation(numeroDeCase);
		} else {
			return Utilitaire.Maps.getX(pereOnly, numeroDeCase, () -> new CaseMemoire(numeroDeCase)).premiereInstance();
		}
	}

}
