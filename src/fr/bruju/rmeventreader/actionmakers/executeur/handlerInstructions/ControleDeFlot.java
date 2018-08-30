package fr.bruju.rmeventreader.actionmakers.executeur.handlerInstructions;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Condition;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;

class ControleDeFlot implements Remplisseur {
	private Dechiffreur d = Dechiffreur.getInstance();

	@Override
	public void remplirMap(Map<Integer, HandlerInstruction> handlers) {
		handlers.put(12110, (e,p,s) -> e.Flot_etiquette(p[0]));
		handlers.put(12120, (e,p,s) -> e.Flot_sautEtiquette(p[0]));
		handlers.put(12210, (e,p,s) -> e.Flot_boucleDebut());
		handlers.put(22210, (e,p,s) -> e.Flot_boucleFin());
		handlers.put(12220, (e,p,s) -> e.Flot_boucleSortir());
		
		handlers.put(12310, (e,p,s) -> e.Flot_stopperCetEvenement());
		handlers.put(12320, (e,p,s) -> e.Flot_effacerCetEvenement());
		
		handlers.put(12330, this::evenement);

		handlers.put(12010, this::condition);
		
		handlers.put(12410, (e,p,s) -> e.Flot_commentaire(s));
		handlers.put(22010, (e,p,s) -> e.Flot_siNon());
		handlers.put(22011, (e,p,s) -> e.Flot_siFin());
		
	}
	

	private void condition(ExecuteurInstructions executeur, int[] parametres, String s) {
		Condition condition = d.dechiffrerCondition(parametres, s);
		executeur.Flot_si(condition);
	}
	
	
	
	private void evenement(ExecuteurInstructions executeur, int[] parametres, String s) {
		switch (parametres[0]) {
		case 0:
			executeur.Flot_appelEvenementCommun(parametres[1]);
			return;
		case 1:
			executeur.Flot_appelEvenementCarte(new ValeurFixe(parametres[1]), new ValeurFixe(parametres[2]));
			return;
		case 2:
			executeur.Flot_appelEvenementCarte(new Variable(parametres[1]), new Variable(parametres[2]));
			return;
		}
	}

}