package fr.bruju.rmeventreader.actionmakers.controlleur;

/**
 * Permet de déclarer un exécuteur qui explore toutes les branches par défaut
 * 
 * @author Bruju
 *
 */
public interface ExecuteurInstructionsTrue extends ExecuteurInstructions {
	@Override
	public default ModuleExecMessages getExecMessages() {
		return ModuleExecMessages.NullTrue;
	}
	
	@Override
	public default ModuleExecFlot getExecFlot() {
		return ModuleExecFlot.NullTrue;
	}
	
	@Override
	public default ModuleExecIntegre getExecIntegre() {
		return ModuleExecIntegre.NullTrue;
	}
}
