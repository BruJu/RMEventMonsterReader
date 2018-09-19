package fr.bruju.rmeventreader.dictionnaires;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import fr.bruju.lcfreader.modele.FabriqueLCF;
import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMEvenementCommun;
import fr.bruju.lcfreader.rmobjets.RMFabrique;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.rmeventreader.dictionnaires.liblcfreader.FabriqueCache;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;

public class FabriqueMiLCFMiXML implements RMFabrique {
	
	private static FabriqueMiLCFMiXML instance;

	private FabriqueMiLCFMiXML() {
	}

	public static FabriqueMiLCFMiXML getInstance() {
		if (null == instance) {
			instance = new FabriqueMiLCFMiXML();
		}
		return instance;
	}
	
	private RMFabrique fabriqueLCF = new FabriqueLCF("ressources\\FichiersBruts");
	private RMFabrique fabriqueXML = FabriqueCache.getInstance();
	
	private ArbreDeCartes arbre = new ArbreDeCartes();
	
	
	private static class ArbreDeCartes {
		private static class Element {
			private int idPere;
			private String nom;
			
			public Element(int idPere, String nom) {
				this.idPere = idPere;
				this.nom = nom;
			}
		}
		
		List<Element> elements = new ArrayList<>();
		
		public ArbreDeCartes() {
			FileReaderByLine.lectureFichierRessources("ressources_gen\\bdd_maps.txt", ligne -> {
				String[] elements = FileReaderByLine.splitter(ligne, 3);
				
				this.elements.add(new Element(Integer.parseInt(elements[1]), elements[2]));
			});
		}
		
		public String getChemin(int idMap) {
			Element element = elements.get(idMap);
			String prefixe = (idMap == 0) ? "" : getChemin(elements.get(idMap).idPere) + "-";
			return prefixe + element.nom;
		}
		
	}
	
	
	@Override
	public RMMap map(int idCarte) {
		return convertirMap(fabriqueLCF.map(idCarte));
	}

	@Override
	public RMEvenement evenement(int idCarte, int idEvenement) {
		return fabriqueLCF.evenement(idCarte, idEvenement);
	}

	@Override
	public RMEvenementCommun evenementCommun(int idEvenementCommun) {
		return fabriqueXML.evenementCommun(idEvenementCommun);
	}

	@Override
	public List<RMMap> maps() {
		return fabriqueLCF.maps().stream().map(this::convertirMap).collect(Collectors.toList());
	}
	
	private RMMap convertirMap(RMMap map) {
		return new $Map(map, arbre.getChemin(map.id()));
	}

	@Override
	public List<RMEvenementCommun> evenementsCommuns() {
		return fabriqueXML.evenementsCommuns();
	}
	
	
	private class $Map implements RMMap {
		
		private RMMap map;
		private String nom;

		public $Map(RMMap map, String nouveauNom) {
			this.map = map;
			this.nom = nouveauNom;
		}

		@Override
		public int id() {
			return map.id();
		}

		@Override
		public String nom() {
			return nom;
		}

		@Override
		public List<RMEvenement> evenements() {
			return map.evenements();
		}
	}


}
