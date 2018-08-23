package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;

public class NombreObjet implements ValeurDroiteVariable {
	/** Numéro de l'objet */
	public final int numeroObjet;
	/** Si vrai il s'agit du nombre d'objets possédés. Si faux du nombre d'objets équipés */
	public final boolean equipe;
	
	public NombreObjet(int numeroObjet, boolean equipe) {
		this.numeroObjet = numeroObjet;
		this.equipe = equipe;
	}

	@Override
	public <T> T accept(VisiteurValeurDroiteVariable<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}
}
