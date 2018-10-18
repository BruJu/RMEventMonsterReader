package fr.bruju.rmeventreader.implementation.detectiondeformules.simplifieurdalgorithmes.inliner;

import java.util.HashMap;
import java.util.Map;

public class Deviation {

	public final Map<Integer, VivaciteVariable> pere;
	public final Map<Integer, VivaciteVariable> filsVrai;
	public final Map<Integer, VivaciteVariable> filsFaux;


	public Deviation(Map<Integer, VivaciteVariable> vivacites) {
		this.pere = vivacites;
		this.filsVrai = new HashMap<>();
		this.filsFaux = new HashMap<>();

		initialiser();
	}

	private void initialiser() {
		for (Map.Entry<Integer, VivaciteVariable> entree : pere.entrySet()) {
			int numeroDeCase = entree.getKey();
			VivaciteVariable vivacite = entree.getValue();

			if (vivacite.getEtat() == VivaciteVariable.Etat.Mort) {
				filsVrai.put(numeroDeCase, new VivaciteVariable(VivaciteVariable.Etat.Mort));
				filsFaux.put(numeroDeCase, new VivaciteVariable(VivaciteVariable.Etat.Mort));
			}
		}
	}

	public Map<Integer, VivaciteVariable> consolider() {
		Map<Integer, VivaciteVariable> nouveauPere = new HashMap<>();

		for (Integer numeroCase : filsVrai.keySet()) {
			if (filsFaux.containsKey(numeroCase)) {
				if (filsVrai.get(numeroCase).getEtat() == VivaciteVariable.Etat.Mort
						&& filsFaux.get(numeroCase).getEtat() == VivaciteVariable.Etat.Mort) {
					nouveauPere.put(numeroCase, new VivaciteVariable(VivaciteVariable.Etat.Mort));
				}
			}
		}

		return nouveauPere;
	}


}
