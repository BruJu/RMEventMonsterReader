package fr.bruju.rmeventreader.actionmakers.controlleur;

/**
 * Exécuteur d'instructions
 * <p>
 * Pour toutes les instructions, des implémentations par défaut ne faisant rien sont fournies.
 * <p>
 * Ainsi pour implémenter un exécuteur, il suffit d'implémenter les instructions pertinentes.
 * @author Bruju
 *
 */
public interface ExecuteurInstructions {
	/** Donne le module gérant les instructions liées à l'équipe */
	public default ModuleExecEquipe getExecEquipe() {
		return ModuleExecEquipe.Null;
	}

	/** Donne le module gérant les instructions liées aux messages */
	public default ModuleExecMessages getExecMessages() {
		return ModuleExecMessages.NullFalse;
	}

	/** Donne le module gérant les instructions liées aux variables */
	public default ModuleExecVariables getExecVariables() {
		return ModuleExecVariables.Null;
	}

	/** Donne le module gérant les instructions liées aux images, vidéos et musiques */
	public default ModuleExecMedia getExecMedia() {
		return ModuleExecMedia.Null;
	}

	/** Donne le module gérant les instructions liées à la gestion du flot d'instructions */
	public default ModuleExecFlot getExecFlot() {
		return ModuleExecFlot.NullFalse;
	}
	

	/** Donne le module gérant les instructions liées à l'état de la partie */
	public default ModuleExecJeu getExecJeu() {
		return ModuleExecJeu.Null;
	}

	/** Donne le module gérant les instructions liées aux systèmes */
	public default ModuleExecSysteme getExecSysteme() {
		return ModuleExecSysteme.Null;
	}

	/** Donne le module gérant les instructions liés aux systèmes intégrés de RPG Maker */
	public default ModuleExecIntegre getExecIntegre() {
		return ModuleExecIntegre.NullFalse;
	}
}
