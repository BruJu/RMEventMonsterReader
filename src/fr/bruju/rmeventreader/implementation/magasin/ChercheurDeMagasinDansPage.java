package fr.bruju.rmeventreader.implementation.magasin;

import java.util.Map;

import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.rmdechiffreur.ExecuteurInstructions;
import fr.bruju.rmdechiffreur.controlleur.ExtChangeVariable;
import fr.bruju.rmdechiffreur.modele.*;
import fr.bruju.rmdechiffreur.modele.ExecEnum.Direction;
import fr.bruju.rmdechiffreur.reference.ReferenceMap;

public class ChercheurDeMagasinDansPage implements ExecuteurInstructions, ExtChangeVariable {
	public static final int MAP_MAGASINS = 461;
	public static final int VARIABLE_ID_MAGASIN = 1209;
	public static final int VARIABLE_VARIATION_PRIX = 973;

	private Map<Integer, Magasin> magasins;
	private RMMap map;
	private Integer magasinActuel;
	private int derniereVariationPrix = 0;
	private ReferenceMap ref;

	public ChercheurDeMagasinDansPage(RMMap map, ReferenceMap ref, Map<Integer, Magasin> magasins) {
		this.map = map;
		this.magasins = magasins;
		this.ref = ref;
	}


	@Override
	public void affecterVariable(Variable valeurGauche, ValeurFixe valeurDroite) {
		if (valeurGauche.idVariable == VARIABLE_ID_MAGASIN) {
			magasinActuel = valeurDroite.valeur;
		} else if (valeurGauche.idVariable == VARIABLE_VARIATION_PRIX) {
			derniereVariationPrix = valeurDroite.valeur;
		}
	}

	@Override
	public int Flot_si(Condition condition) {
		derniereVariationPrix = 0;
		return 0;
	}

	@Override
	public void Flot_siFin() {
		derniereVariationPrix = 0;
	}

	@Override
	public void Flot_siNon() {
		derniereVariationPrix = 0;
	}

	@Override
	public void Jeu_teleporter(int idMap, int x, int y, Direction direction) {
		if (idMap == MAP_MAGASINS) {
			ajouterMagasin();
		}
	}

	private void ajouterMagasin() {
		if (magasinActuel == null) {
			System.out.println(map.nom());
			System.out.println(ref.getString());
		}

		Magasin magasinExistant = magasins.get(magasinActuel);

		int variationDePrixReel = Magasin.transformerVariation(derniereVariationPrix);

		if (magasinExistant != null && variationDePrixReel >= magasinExistant.variationPrix) {
			return;
		}

		magasins.put(magasinActuel, new Magasin(magasinActuel, map.nom(), variationDePrixReel));
	}

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}
}
