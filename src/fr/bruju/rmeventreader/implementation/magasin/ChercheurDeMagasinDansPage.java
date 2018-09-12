package fr.bruju.rmeventreader.implementation.magasin;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecJeu;
import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ModuleExecVariables;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.ExecEnum.Direction;
import fr.bruju.rmeventreader.dictionnaires.modele.MapGeneral;
import fr.bruju.rmeventreader.dictionnaires.modele.MapRM;
import fr.bruju.rmeventreader.implementation.chercheurdevariables.reference.ReferenceMap;

public class ChercheurDeMagasinDansPage implements ExecuteurInstructionsTrue, ModuleExecVariables, ModuleExecJeu {
	private Map<Integer, Magasin> magasins;
	private MapRM map;
	private Integer magasinActuel;
	private ReferenceMap ref;

	public ChercheurDeMagasinDansPage(MapGeneral map, ReferenceMap ref, Map<Integer, Magasin> magasins) {
		this.map = map.map;
		this.magasins = magasins;
		this.ref = ref;
	}
	
	@Override
	public ModuleExecVariables getExecVariables() {
		return this;
	}

	@Override
	public ModuleExecJeu getExecJeu() {
		return this;
	}

	@Override
	public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
		Boolean gaucheOk = valeurGauche.appliquerG(v -> v.idVariable == 1209, null, null);
		Integer droite = valeurDroite.appliquerDroite(v -> v.valeur, null, null);
		
		if (gaucheOk == Boolean.TRUE && droite != null) {
			magasinActuel = droite;
		}
	}


	@Override
	public void Jeu_teleporter(int idMap, int x, int y, Direction direction) {
		if (idMap == 461) {
			ajouterMagasin();
		}
	}

	private void ajouterMagasin() {
		if (magasinActuel == null) {
			System.out.println(map.getNom());
			System.out.println(ref.getString());
		}
		
		
		if (magasins.containsKey(magasinActuel))
			return;
		
		System.out.println("Nouveau magasin : " + magasinActuel + " ; " + map.getNom()); 
		
		magasins.put(magasinActuel, new Magasin(magasinActuel, map.getNom()));
	}
}
