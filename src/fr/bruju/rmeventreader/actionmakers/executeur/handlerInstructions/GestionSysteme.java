package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.SonParam;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum;

class GestionSysteme implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();
	
	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		handlers.put(10610, (e, p, s) -> e.Systeme_modifierNom(p[0], s));
		handlers.put(10620, (e, p, s) -> e.Systeme_modifierGrade(p[0], s));
		handlers.put(10630, this::modifierCharsetHeros);
		handlers.put(10640, (e, p, s) -> e.Systeme_modifierFaceset(p[0], s, p[1]));
		handlers.put(10650, (e, p, s) -> e.Systeme_modifierApparenceVehicule(d.dechiffreVehicule(p[0]), s, p[1]));
		handlers.put(10660, this::modifierMusique);
		handlers.put(10670, this::modifierEffetSonore);
		
		
		
	}

	private void modifierEffetSonore(ExecuteurInstructions executeur, int[] parametres, String s) {
		ExecEnum.EffetSonore son = ExecEnum.EffetSonore.values()[parametres[0]];
		SonParam sonParam = new SonParam(parametres[1], parametres[2], parametres[3]);
		
		executeur.Systeme_modifierEffetSonore(son, s, sonParam);
	}
	
	private void modifierMusique(ExecuteurInstructions executeur, int[] parametres, String s) {
		ExecEnum.Musique musique = ExecEnum.Musique.values()[parametres[0]];
		int tempsFondu = parametres[1];
		SonParam sonParam = new SonParam(parametres[2], parametres[3], parametres[4]);
		
		executeur.Systeme_modifierMusique(musique, s, tempsFondu, sonParam);
	}

	private void modifierCharsetHeros(ExecuteurInstructions executeur, int[] parametres, String s) {
		boolean transparent = parametres[2] == 1;
		executeur.Systeme_modifierApparenceHeros(parametres[0], s, parametres[1], transparent);
	}
}
