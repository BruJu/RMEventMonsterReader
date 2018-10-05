package fr.bruju.rmeventreader.implementation.magasin;

import java.util.Map;

import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.modele.ValeurDroiteVariable;
import fr.bruju.rmdechiffreur.modele.ValeurGauche;
import fr.bruju.rmdechiffreur.modele.ExecEnum.Direction;
import fr.bruju.rmdechiffreur.reference.ReferenceMap;

public class ChercheurDeMagasinDansPage implements ExecuteurInstructions {
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

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}
}
