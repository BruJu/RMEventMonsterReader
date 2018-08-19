package fr.bruju.rmeventreader.implementation.equipementchecker;

import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

import fr.bruju.rmeventreader.utilitaire.Utilitaire;

public class EquipementData {
	public Map<Integer, Integer> variablesModifiees = new TreeMap<>();
	public boolean estTropComplexe;

	public EquipementData() {
		this.estTropComplexe = false;
	}

	public String getString() {
		if (estTropComplexe) {
			return "Complexe";
		}
		
		return variablesModifiees.entrySet().stream().map(entry -> "V[" + entry.getKey() + "] " + entry.getValue())
							.collect(Collectors.joining("\n"));
	}
	
	
	public void setComplexe() {
		estTropComplexe = true;
	}
	
	public void ajouterModification(int variable, int valeur) {
		variablesModifiees.compute(variable, (cle, exValeur) ->	exValeur == null ? valeur : exValeur + valeur);
	}
	
	
	public static TreeMap<Integer, EquipementData> combiner(TreeMap<Integer, EquipementData> bonus,
			TreeMap<Integer, EquipementData> malus) {
		
		TreeMap<Integer, EquipementData> nouveau = new TreeMap<>();
		
		cumuler(nouveau, bonus, 1);
		cumuler(nouveau, malus, -1);
		
		return nouveau;
	}

	private static void cumuler(TreeMap<Integer, EquipementData> receveur, TreeMap<Integer, EquipementData> donneur,
			int multiplicateur) {
		Utilitaire.Maps.fusionnerDans(receveur, donneur, v -> v,
				(d, s) -> ajouter(d, s));
	}

	private static EquipementData ajouter(EquipementData a, EquipementData b) {
		if (a.estTropComplexe || b.estTropComplexe) {
			return getStaticComplexe();
		}

		EquipementData eq = new EquipementData();
		Utilitaire.Maps.fusionnerDans(eq.variablesModifiees, a.variablesModifiees, v -> v, (u, v) -> u + v);
		Utilitaire.Maps.fusionnerDans(eq.variablesModifiees, b.variablesModifiees, v -> v, (u, v) -> u + v);
		
		return eq;
	}

	private static EquipementData getStaticComplexe() {
		EquipementData eq = new EquipementData();
		eq.setComplexe();
		return eq;
	}

	public static TreeMap<Integer, EquipementData> simplifier(TreeMap<Integer, EquipementData> somme) {
		TreeMap<Integer, EquipementData> nouveau = new TreeMap<>();
		somme.forEach((cle, data) -> {
			EquipementData epure = data.epurer();
			
			if (epure != null) {
				nouveau.put(cle, epure);
			}
		});
		
		return nouveau;
	}

	private EquipementData epurer() {
		if (this.estTropComplexe)
			return this;
		
		EquipementData ed = new EquipementData();
		
		this.variablesModifiees.forEach((cle, valeur) -> {
			if (!valeur.equals(0)) {
				ed.variablesModifiees.put(cle, valeur);
			}
		});
		
		
		return ed.variablesModifiees.isEmpty() ? null : ed;
	}
}