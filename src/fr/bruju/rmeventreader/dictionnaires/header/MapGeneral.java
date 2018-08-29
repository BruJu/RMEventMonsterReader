package fr.bruju.rmeventreader.dictionnaires.header;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.ConvertisseurLigneVersObjet;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.LigneAttendue;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.Passe;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.TableauInt;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.decorateur.BoucleTraitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.decorateur.TraitementObjet;

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
	public static ConvertisseurLigneVersObjet<MapGeneral, Builder> sousObjet() {
		return new ConvertisseurLigneVersObjet<>(new Builder(), new Traitement[] {
				new TraitementObjet<Builder, MapRM>(MapRM.sousObjet(), (b, map) -> b.creerMap(map)),
				new LigneAttendue<>("- Evenements - "),
				new TableauInt<Builder>("Present", (b, t) -> b.setEvenements(t)),
				new TableauInt<Builder>("Complexe", (b, t) -> b.setComplexes(t)),
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
			for (int i : events) {
				map.evenements.add(i);
			}
			return this;
		}
		
		public Builder setComplexes(int[] complexes) {
			for (int i : complexes) {
				map.evenementsComplexes.add(i);
			}
			return this;
		}
		
		
		
	}
}
