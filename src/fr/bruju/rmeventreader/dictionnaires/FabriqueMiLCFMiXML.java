package fr.bruju.rmeventreader.dictionnaires;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMEvenementCommun;
import fr.bruju.lcfreader.rmobjets.RMFabrique;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.services.Arborescence;
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
	private RMFabrique fabriqueLCF = new Arborescence("ressources\\FichiersBruts\\");
	/** Fabrique lisant à partir des fichiers issus des xml pour les évènements communs */
	private RMFabrique fabriqueXML = fabriqueLCF;
	

	/* ========
	 * FABRIQUE
	 * ======== */
	
	@Override
	public RMMap map(int idCarte) {
		return fabriqueLCF.map(idCarte);
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
		return fabriqueLCF.maps();
	}
	
	@Override
	public Map<Integer, RMEvenementCommun> evenementsCommuns() {
		return fabriqueXML.evenementsCommuns();
	}
	


}
