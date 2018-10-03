package fr.bruju.rmeventreader.actionmakers.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum;
import fr.bruju.rmeventreader.actionmakers.modele.SonParam;

class GestionSysteme implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();
	
	@Override
	public void remplirMap(Map<Integer, TraiteurSansRetour> handlers, Map<Integer, Traiteur> classe2) {
		handlers.put(10610, (e, p, s) -> e.getExecSysteme().Systeme_modifierNom(p[0], s));
		handlers.put(10620, (e, p, s) -> e.getExecSysteme().Systeme_modifierGrade(p[0], s));
		handlers.put(10630, this::modifierCharsetHeros);
		handlers.put(10640, (e, p, s) -> e.getExecSysteme().Systeme_modifierFaceset(p[0], s, p[1]));
		handlers.put(10650, (e, p, s) -> e.getExecSysteme().Systeme_modifierApparenceVehicule(d.dechiffreVehicule(p[0]), s, p[1]));
		handlers.put(10660, this::modifierMusique);
		handlers.put(10670, this::modifierEffetSonore);
		handlers.put(10680, this::modifierApparence);
		handlers.put(10690, this::modifierTransition);
		handlers.put(11820, (e,p,s) -> e.getExecSysteme().Systeme_peutSeTeleporter(p[0] == 0));
		handlers.put(11840, (e,p,s) -> e.getExecSysteme().Systeme_peutFuir(p[0] == 0));
		handlers.put(11930, (e,p,s) -> e.getExecSysteme().Systeme_peutSauvegarder(p[0] == 0));
		handlers.put(11960, (e,p,s) -> e.getExecSysteme().Systeme_peutOuvrirLeMenu(p[0] == 0));
		
		handlers.put(1009, this::modifierCommandes);
		handlers.put(1008, this::modifierClasse);
	}
	

	private void modifierClasse(ExecuteurInstructions executeur, int[] p, String s) {
		int idHeros = p[1];
		int idClasse = p[2];
		boolean revenirAuNiveau1 = p[3] == 1;
		ExecEnum.ClasseComp competences = ExecEnum.ClasseComp.values()[p[4]];
		ExecEnum.ClasseCarac caracBase = ExecEnum.ClasseCarac.values()[p[5]];
		boolean montrerCompetencesApprises = p[6] == 1;
		
		executeur.getExecSysteme().Systeme_changerClasse(idHeros, idClasse, revenirAuNiveau1, competences, caracBase,
				montrerCompetencesApprises);
	}
	
	
	private void modifierCommandes(ExecuteurInstructions executeur, int[] parametres, String s) {
		int idHeros = parametres[1];
		boolean ajout = parametres[3] == 0;
		int numeroCommande = parametres[2];
		executeur.getExecSysteme().Systeme_modifierCommandes(idHeros, ajout, numeroCommande);
	}
	
	
	
	private void modifierTransition(ExecuteurInstructions executeur, int[] parametres, String s) {
		ExecEnum.SujetTransition sujetTransition = d.sujetTransition(parametres[0]);
		boolean entrant = parametres[0] % 2 == 0;
		ExecEnum.Transition transition = ExecEnum.Transition.values()[parametres[1]];
		
		executeur.getExecSysteme().Systeme_modifierTransition(sujetTransition, entrant, transition);
	}
	

	private void modifierApparence(ExecuteurInstructions executeur, int[] parametres, String s) {
		executeur.getExecSysteme().Systeme_modifierApparence(parametres[0] == 0, parametres[1] == 0, s);
	}
	
	private void modifierEffetSonore(ExecuteurInstructions executeur, int[] parametres, String s) {
		ExecEnum.EffetSonore son = ExecEnum.EffetSonore.values()[parametres[0]];
		SonParam sonParam = new SonParam(parametres[1], parametres[2], parametres[3]);
		
		executeur.getExecSysteme().Systeme_modifierEffetSonore(son, s, sonParam);
	}
	
	private void modifierMusique(ExecuteurInstructions executeur, int[] parametres, String s) {
		ExecEnum.Musique musique = ExecEnum.Musique.values()[parametres[0]];
		int tempsFondu = parametres[1];
		SonParam sonParam = new SonParam(parametres[2], parametres[3], parametres[4]);
		
		executeur.getExecSysteme().Systeme_modifierMusique(musique, s, tempsFondu, sonParam);
	}

	private void modifierCharsetHeros(ExecuteurInstructions executeur, int[] parametres, String s) {
		boolean transparent = parametres[2] == 1;
		executeur.getExecSysteme().Systeme_modifierApparenceHeros(parametres[0], s, parametres[1], transparent);
	}
}