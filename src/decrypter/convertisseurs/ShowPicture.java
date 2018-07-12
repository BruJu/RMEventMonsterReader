package decrypter.convertisseurs;

import java.util.List;

import actionner.ActionMaker;

/**
 * Affichage d'une image
 */
public class ShowPicture implements Action {
	private final String PATTERN = "<> Show Picture: #_, _, £";
	
	@Override
	public String getPattern() {
		return PATTERN;
	}

	@Override
	public void faire(ActionMaker actionMaker, List<String> arguments) {
		int idImage = Integer.parseInt(arguments.get(0));
		String imageName = arguments.get(1);
		
		actionMaker.showPicture(idImage, imageName);
	}

}
