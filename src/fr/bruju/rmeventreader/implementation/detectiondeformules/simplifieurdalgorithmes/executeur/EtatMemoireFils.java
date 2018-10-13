package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.executeur;

import fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.modele.expression.ExprVariable;

public final class EtatMemoireFils extends EtatMemoire {
	public final EtatMemoire pere;
	
	public EtatMemoireFils getFrere() {
		return pere.filsDroit;
	}
	
	public EtatMemoire revenirAuPere() {
		pere.accumulerFils();
		return pere;
	}
	
	public EtatMemoireFils(EtatMemoire pere) {
		this.pere = pere;
	}
	
	protected ExprVariable getValeurManquante(int numeroDeCase) {
		return pere.getValeur(numeroDeCase);
	}
}
