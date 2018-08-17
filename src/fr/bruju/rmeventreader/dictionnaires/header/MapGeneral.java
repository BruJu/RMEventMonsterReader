package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Constructeur;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;

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
	public static MapGeneral construire(String fichier) {
		return Constructeur.construire(fichier, new Builder(), new Traitement[] {
				
				
				
				
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
		
		
		
		
	}
}
