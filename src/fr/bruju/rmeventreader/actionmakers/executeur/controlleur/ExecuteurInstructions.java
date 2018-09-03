package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

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
	public default ModuleExecEquipe getExecEquipe() {
		return ModuleExecEquipe.Null;
	}
	
	public default ModuleExecMessages getExecMessages() {
		return ModuleExecMessages.NullFalse;
	}

	public default ModuleExecVariables getExecVariables() {
		return ModuleExecVariables.Null;
	}
	
	public default ModuleExecMedia getExecMedia() {
		return ModuleExecMedia.Null;
	}
	
	public default ModuleExecFlot getExecFlot() {
		return ModuleExecFlot.NullFalse;
	}
	
	public default ModuleExecJeu getExecJeu() {
		return ModuleExecJeu.Null;
	}
	
	public default ModuleExecSysteme getExecSysteme() {
		return ModuleExecSysteme.Null;
	}
	
	public default ModuleExecIntegre getExecIntegre() {
		return ModuleExecIntegre.NullFalse;
	}
}
