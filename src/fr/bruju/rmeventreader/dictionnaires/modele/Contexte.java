package fr.bruju.rmeventreader.dictionnaires.modele;

import java.util.Set;
import java.util.TreeSet;

import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.ConvertisseurLigneVersObjet;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.Traitement;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.Passe;
import fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier.base.TableauInt;



public class Contexte {
	public final Set<Integer> maps;
	public final int nombreEC;
	
	public Contexte(Set<Integer> maps, int nombreEC) {
		this.maps = maps;
		this.nombreEC = nombreEC;
	}
	

	@SuppressWarnings("unchecked")
	public static ConvertisseurLigneVersObjet<Contexte, Builder> sousObjet() {
		return new ConvertisseurLigneVersObjet<>(new Builder(), new Traitement[] {
				new TableauInt<Builder>("Maps", (m, t) -> m.setMaps(t)),
				new TableauInt<Builder>("EC", (m, t) -> m.setEC(t[0])),
				new Passe<>(),
				new Passe<>()
		});
	}
	
	public static class Builder implements Monteur<Contexte> {
		private Set<Integer> maps = new TreeSet<>();
		private int nombreEC;
		
		@Override
		public Contexte build() {
			return new Contexte(maps, nombreEC);
		}
		
		public Builder setMaps(int[] tableau) {
			for (int nombre : tableau) {
				maps.add(nombre);
			}
			return this;
		}
		
		public Builder setEC(int nombre) {
			nombreEC = nombre;
			return this;
		}
	}
}
