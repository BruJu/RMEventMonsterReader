package fr.bruju.rmeventreader.actionmakers.controlleur;

/**
 * Permet de déclarer que l'on fait un exécuteur qui s'occupe lui-même de tous les modules.
 * @author Bruju
 *
 */
public interface ExecuteurInstructionsEtoile extends ExecuteurInstructions, ModuleExecMessages,
	ModuleExecMedia, ModuleExecFlot, ModuleExecSysteme, ModuleExecIntegre {
	@Override
	public default ModuleExecMessages getExecMessages() {
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
