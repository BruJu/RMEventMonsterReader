package fr.bruju.rmeventreader.implementation.magasin;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.controlleur.ExecuteurInstructionsTrue;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.modele.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.modele.ExecEnum.Direction;
import fr.bruju.rmeventreader.actionmakers.reference.ReferenceMap;
import fr.bruju.lcfreader.rmobjets.RMMap;

public class ChercheurDeMagasinDansPage implements ExecuteurInstructionsTrue {
	private Map<Integer, Magasin> magasins;
	private RMMap map;
	private Integer magasinActuel;
	private ReferenceMap ref;

	public ChercheurDeMagasinDansPage(RMMap map, ReferenceMap ref, Map<Integer, Magasin> magasins) {
		this.map = map;
		this.magasins = magasins;
		this.ref = ref;
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
			System.out.println(map.nom());
			System.out.println(ref.getString());
		}
		
		if (magasins.containsKey(magasinActuel))
			return;
		
		System.out.println("Nouveau magasin : " + magasinActuel + " ; " + map.nom()); 
		
		magasins.put(magasinActuel, new Magasin(magasinActuel, map.nom()));
	}
}
