package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;

public class ValeurDeplacable implements ValeurDroiteVariable {
	public final EvenementDeplacable deplacable;
	public final Caracteristique caracteristique;
	
	public ValeurDeplacable(EvenementDeplacable deplacable, Caracteristique caracteristique) {
		this.deplacable = deplacable;
		this.caracteristique = caracteristique;
	}

	@Override
	public <T> T accept(VisiteurValeurDroiteVariable<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}
	
	public static enum Caracteristique {
		ID_MAP,
		X,
		Y,
		DIRECTION,
		X_RELATIF_ECRAN,
		Y_RELATIF_ECRAN
	}
}
