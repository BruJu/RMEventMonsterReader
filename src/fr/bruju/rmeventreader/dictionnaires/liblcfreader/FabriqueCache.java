package fr.bruju.rmeventreader.dictionnaires.liblcfreader;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import fr.bruju.lcfreader.rmobjets.RMEvenement;
import fr.bruju.lcfreader.rmobjets.RMEvenementCommun;
import fr.bruju.lcfreader.rmobjets.RMFabrique;
import fr.bruju.lcfreader.rmobjets.RMMap;
import fr.bruju.lcfreader.rmobjets.RMPage;
import fr.bruju.rmeventreader.utilitaire.Pair;

public class FabriqueCache implements RMFabrique {
	private static FabriqueCache instance;

	private FabriqueCache() {
	}

	public static FabriqueCache getInstance() {
		if (null == instance) {
			instance = new FabriqueCache();
		}
		return instance;
	}

	@Override
	public RMMap map(int idCarte) {
		return LecteurDeCache.getMapGeneral(idCarte).getRMMap();
	}

	@Override
	public RMEvenement evenement(int idCarte, int idEvenement) {
		return LecteurDeCache.getEvenement(idCarte, idEvenement).getRMEvenement();
	}

	@Override
	public RMPage page(int idCarte, int idEvenement, int idPage) {
		return evenement(idCarte, idEvenement).pages().get(idPage - 1);
	}

	@Override
	public RMEvenementCommun evenementCommun(int idEvenementCommun) {
		return LecteurDeCache.getEvenementCommun(idEvenementCommun).getRMEvenementCommun();
	}

	@Override
	public List<RMMap> maps() {
		return LecteurDeCache.getInformations().getRight().stream().map(id -> map(id)).collect(Collectors.toList());
	}

	@Override
	public Map<Integer, RMEvenementCommun> evenementsCommuns() {
		int nombreDEC = LecteurDeCache.getInformations().getLeft();
		return IntStream.range(0, nombreDEC)
				        .mapToObj(id -> new Pair<>(id, evenementCommun(id+1)))
				        .collect(Pair.toMap());
	}
}
