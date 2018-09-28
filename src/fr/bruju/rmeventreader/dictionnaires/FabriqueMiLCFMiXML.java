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
	/* =========
	 * SINGLETON
	 * ========= */
	
	private static FabriqueMiLCFMiXML instance;

	private FabriqueMiLCFMiXML() {
	}

	public static FabriqueMiLCFMiXML getInstance() {
		if (null == instance) {
			instance = new FabriqueMiLCFMiXML();
		}
		return instance;
	}

	/* =========
	 * ATTRIBUTS
	 * ========= */
	
	/** Fabrique LCF pour les maps */
	private RMFabrique fabriqueLCF = new FabriqueLCF("ressources\\FichiersBruts");
	/** Fabrique lisant à partir des fichiers issus des xml pour les évènements communs */
	private RMFabrique fabriqueXML = FabriqueCache.getInstance();
	/** Redéfinition des noms */
	private ArbreDeCartes arbre = new ArbreDeCartes();
	
	/**
	 * Décore la map lue dans les fichiers LCF avec le nom voulu
	 * @param map La map à décorer
	 * @return Une map comportant le nom issu de la redéfinition des noms
	 */
	private RMMap convertirMap(RMMap map) {
		return new $Map(map, arbre.getChemin(map.id()));
	}

	/* ========
	 * FABRIQUE
	 * ======== */
	
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
	
	@Override
	public List<RMEvenementCommun> evenementsCommuns() {
		return fabriqueXML.evenementsCommuns();
	}
	
	/**
	 * Classe permettant de redéfinir le nom d'une map
	 */
	private class $Map implements RMMap {
		/** Map */
		private RMMap map;
		/** Nom de la map */
		private String nom;

		/**
		 * Crée une map à partir d'une map déjà existante et d'un nouveau nom
		 * @param map La map
		 * @param nouveauNom Le nouveau nom
		 */
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

	/* =========================
	 * LECTURE DES NOUVEAUX NOMS 
	 * ========================= */

	/**
	 * Classe englobant tous les noms à donner aux map
	 */
	private static class ArbreDeCartes {
		/**
		 * Un élément est un numéro de la map père et du nom de la map
		 */
		private static class Element {
			/** Position du père dans le tableau */
			private int idPere;
			/** Nom de la map */
			private String nom;
			
			/**
			 * Construit un élément avec l'id du père et le nom de la map
			 * @param idPere La position du père
			 * @param nom Le nom de la map
			 */
			public Element(int idPere, String nom) {
				this.idPere = idPere;
				this.nom = nom;
			}
		}
		
		/** Liste des éléments connus */
		private List<Element> elements = new ArrayList<>();
		
		/**
		 * Crée l'arbre des cartes en lisant dans un fichier ressources nommés bdd_maps.txt
		 */
		public ArbreDeCartes() {
			FileReaderByLine.lectureFichierRessources("ressources_gen\\bdd_maps.txt", ligne -> {
				String[] elements = FileReaderByLine.splitter(ligne, 3);
				
				this.elements.add(new Element(Integer.parseInt(elements[1]), elements[2]));
			});
		}
		
		/**
		 * Donne le chemin pour accéder à la map dans l'arborescence 
		 * @param idMap Le numéro de la map
		 * @return Une chaîne représentant le chemin pour aller à cette map sous la forme
		 * <pre>Nom du Projet - Map 1 - (...) Map idMap</pre>
		 */
		public String getChemin(int idMap) {
			Element element = elements.get(idMap);
			String prefixe = (idMap == 0) ? "" : getChemin(elements.get(idMap).idPere) + "-";
			return prefixe + element.nom;
		}
	}
}
