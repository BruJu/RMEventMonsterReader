package fr.bruju.rmeventreader.implementationexec.magasin;

import java.util.Map;

import fr.bruju.rmeventreader.actionmakers.executeur.controlleur.ExecuteurInstructions;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.interfaces.ValeurGauche;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ExecEnum.Direction;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.ValeurFixe;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.objets.Variable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurDroiteVariable;
import fr.bruju.rmeventreader.actionmakers.executeur.modele.visiteur.VisiteurValeurGauche;
import fr.bruju.rmeventreader.dictionnaires.header.MapGeneral;
import fr.bruju.rmeventreader.dictionnaires.header.MapRM;
import fr.bruju.rmeventreader.implementationexec.chercheurdevariables.ReferenceMap;

public class ChercheurDeMagasinDansPage implements ExecuteurInstructions {
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
	public void Variables_affecterVariable(ValeurGauche valeurGauche, ValeurDroiteVariable valeurDroite) {
		Boolean gaucheOk = valeurGauche.accept(new VisiteurValeurGauche<Boolean>() {
			@Override
			public Boolean visit(Variable variable) {
				return variable.idVariable == 1209;
			}
		});
		
		Integer droite = valeurDroite.accept(new VisiteurValeurDroiteVariable<Integer>() {
			@Override
			public Integer visit(ValeurFixe valeur) {
				return valeur.valeur;
			}
		});
		
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

	@Override
	public boolean getBooleenParDefaut() {
		return true;
	}

}
