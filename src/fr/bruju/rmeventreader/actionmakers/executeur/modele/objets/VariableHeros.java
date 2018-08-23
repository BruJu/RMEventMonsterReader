package fr.bruju.rmeventreader.actionmakers.executeur.modele.objets;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;

public class VariableHeros implements ValeurDroiteVariable {
	public final int idHeros;
	public final Caracteristique caracteristique;
	
	public VariableHeros(int idHeros, Caracteristique caracteristique) {
		this.idHeros = idHeros;
		this.caracteristique = caracteristique;
	}

	@Override
	public <T> T accept(VisiteurValeurDroiteVariable<T> visiteur) throws ObjetNonSupporte {
		return visiteur.visit(this);
	}

	public static enum Caracteristique {
		NIVEAU,
		EXPERIENCE,
		HPACTUEL,
		MPACTUEL,
		HPMAX,
		MPMAX,
		ATTAQUE,
		DEFENSE,
		INTELLIGENCE,
		AGILITE,
		IDARME,
		IDBOUCLIER,
		IDARMURE,
		IDCASQUE,
		IDACCESSOIRE
	}
}
