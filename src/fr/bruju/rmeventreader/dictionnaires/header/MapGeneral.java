package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.BoucleTraitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.LigneAttendue;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Passe;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.SousObject;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.TableauInt;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.TraitementObjet;

public class MapGeneral implements ElementComposite<Evenement> {
	public final MapRM map;
	public final List<Integer> evenements;
	public final List<Integer> evenementsComplexes;
	
	public MapGeneral(MapRM map) {
		this.map = map;
		this.evenements = new ArrayList<>();
		this.evenementsComplexes = new ArrayList<>();
	}
	
	@Override
	public void ajouter(Evenement t) {
		throw new UnsupportedOperationException("MapGeneral::ajouter");
	}

	@SuppressWarnings("unchecked")
	public static SousObject<MapGeneral, Builder> sousObjet() {
		return new SousObject<>(new Builder(), new Traitement[] {
				new TraitementObjet<Builder, MapRM>(MapRM.sousObjet(), (b, map) -> b.creerMap(map)),
				new LigneAttendue<>("- Evenements - "),
				new TableauInt<Builder>(null, (b, t) -> b.setEvenements(t)),
				new TableauInt<Builder>(null, (b, t) -> b.setComplexes(t)),
				new BoucleTraitement<Builder>(Passe::new)
			});
	}
	
	public static class Builder implements Monteur<MapGeneral> {
		private MapGeneral map;

		@Override
		public MapGeneral build() {
			return map;
		}
		
		public Builder creerMap(MapRM mapRM) {
			map = new MapGeneral(mapRM);
			return this;
		}
		
		public Builder setEvenements(int[] events) {
			
			return this;
		}
		
		public Builder setComplexes(int[] complexes) {
			
			return this;
		}
		
		
		
	}
}
