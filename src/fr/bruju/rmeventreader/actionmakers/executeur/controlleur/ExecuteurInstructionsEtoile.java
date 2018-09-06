package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

/**
 * Permet de déclarer que l'on fait un exécuteur qui s'occupe lui-même de tous les modules.
 * @author Bruju
 *
 */
public interface ExecuteurInstructionsEtoile extends ExecuteurInstructions, ModuleExecMessages, ModuleExecVariables,
	ModuleExecEquipe, ModuleExecMedia, ModuleExecFlot, ModuleExecJeu, ModuleExecSysteme, ModuleExecIntegre {
	@Override
	public default ModuleExecEquipe getExecEquipe() {
		return this;
	}

	@Override
	public default ModuleExecMessages getExecMessages() {
		return this;
	}

	@Override
	public default ModuleExecVariables getExecVariables() {
		return this;
	}

	@Override
	public default ModuleExecMedia getExecMedia() {
		return this;
	}

	@Override
	public default ModuleExecFlot getExecFlot() {
		return this;
	}

	@Override
	public default ModuleExecJeu getExecJeu() {
		return this;
	}

	@Override
	public default ModuleExecSysteme getExecSysteme() {
		return this;
	}

	@Override
	public default ModuleExecIntegre getExecIntegre() {
		return this;
	}

	@Override
	public default boolean getBooleenParDefaut() {
		return true;
	}
}
