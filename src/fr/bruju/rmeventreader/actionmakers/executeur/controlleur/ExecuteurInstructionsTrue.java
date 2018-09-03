package fr.bruju.rmeventreader.actionmakers.executeur.controlleur;

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
