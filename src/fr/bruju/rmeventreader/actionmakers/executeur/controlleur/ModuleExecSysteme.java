package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.SonParam;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.ClasseCarac;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.ClasseComp;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.SujetTransition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.Transition;

public interface ModuleExecSysteme {
	public static final ModuleExecSysteme Null = new ModuleExecSysteme() {};
	

	public default void Systeme_changerClasse(int idHeros, int idClasse, boolean revenirAuNiveau1, ClasseComp competences,
			ClasseCarac caracBase, boolean montrerCompetencesApprises) {
		
	}

	/**
	 * 
	 * @param etire Vrai = étiré, Faux = Mosaique
	 * @param premierePolice Vrai = première police, Faux = seconde police
	 * @param nomApparence Nom du fichier contenant l'apparence
	 */
	public default void Systeme_modifierApparence(boolean etire, boolean premierePolice, String nomApparence) {
	}

	public default void Systeme_modifierApparenceHeros(int idHeros, String charset, int numeroCharset,
			boolean transparent) {
	}

	public default void Systeme_modifierApparenceVehicule(ExecEnum.Vehicule vehicule, String charset,
			int numeroCharset) {
	}

	public default void Systeme_modifierCommandes(int idHeros, boolean ajout, int numeroCommande) {
		
	}
	public default void Systeme_modifierEffetSonore(ExecEnum.EffetSonore son, String nom, SonParam parametres) {
	}
	
	public default void Systeme_modifierFaceset(int idHeros, String faceset, int numeroFaceset) {
	}

	public default void Systeme_modifierGrade(int idHeros, String nouveauGrade) {
	}

	public default void Systeme_modifierMusique(ExecEnum.Musique musique, String nom, int temspFondu,
			SonParam parametres) {
	}

	public default void Systeme_modifierNom(int idHeros, String nouveauNom) {
	}

	public default void Systeme_modifierTransition(SujetTransition sujetTransition, boolean entrant,
			Transition transition) {
		
	}
	public default void Systeme_peutFuir(boolean etat) {
		
	}

	public default void Systeme_peutOuvrirLeMenu(boolean etat) {
		
	}
	
	public default void Systeme_peutSauvegarder(boolean etat) {
		
	}

	public default void Systeme_peutSeTeleporter(boolean etat) {
		
	}
}
