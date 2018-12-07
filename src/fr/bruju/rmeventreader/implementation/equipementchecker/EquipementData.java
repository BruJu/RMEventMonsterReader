package fr.bruju.rmeventreader.implementation.equipementchecker;

import java.util.Map;
import java.util.StringJoiner;
import java.util.TreeMap;
import java.util.stream.Collectors;

import fr.bruju.util.MapsUtils;

import static fr.bruju.rmeventreader.ProjetS.PROJET;

/**
 * Ensemble de valeurs modifiées par un équipement
 * 
 * @author Bruju
 *
 */
public class EquipementData {
	/* ==============
	 * EquipementData
	 * ============== */
	/** Liste des variables modifiées */
	private Map<Integer, Integer> variablesModifiees = new TreeMap<>();
	/** Si vrai alors d'autres opérations que des ajouts ou des soustrations interviennent */
	public boolean estTropComplexe;

	/**
	 * Crée un ensemble de valeurs modifiées
	 */
	public EquipementData() {
		this.estTropComplexe = false;
	}
	
	/**
	 * Donne une représentation de l'ensemble de valeurs modifiées
	 * <ul>
	 * <li>Si l'évènement est trop complexe, renvoie Complexe</li>
	 * <li>Sinon, renvoie une liste du type V[idVariable] valeurAjoutée</li>
	 * </ul>
	 * @return Une représentation de l'ensemble de valeurs modifiées
	 */
	public String getString() {
		if (estTropComplexe) {
			return "Complexe";
		}

		StringJoiner sj = new StringJoiner("\n");

		variablesModifiees.forEach((variable, bonus) -> {
			sj.add("V[" + variable + ":" + PROJET.extraireVariable(variable) + "] " + bonus);
		});

		return sj.toString();
	}
	
	/**
	 * Détermine que l'équipement fait des opérations complexes
	 */
	public void setComplexe() {
		estTropComplexe = true;
	}
	
	/**
	 * Ajoute la modification de la variable par le nombre valeur
	 * @param variable La variable
	 * @param valeur La valeur à ajouter
	 */
	public void ajouterModification(int variable, int valeur) {
		variablesModifiees.compute(variable, (cle, exValeur) ->	exValeur == null ? valeur : exValeur + valeur);
	}

	/**
	 * Retire les modifications de variables qui ajoutent 0
	 * @return Un objet équivalent à cet objet, sans les modifications par 0
	 */
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
	
	
	/**
	 * Fait la somme des bonus des deux EquipementData
	 * @param a Le premier EquipementData
	 * @param b Le second EquipementData
	 * @return Un EquipementData avec la somme des equipement data donnés
	 */
	private static EquipementData ajouter(EquipementData a, EquipementData b) {
		if (a.estTropComplexe || b.estTropComplexe) {
			return getStaticComplexe();
		}

		EquipementData eq = new EquipementData();
		MapsUtils.fusionnerDans(eq.variablesModifiees, a.variablesModifiees, v -> v, (u, v) -> u + v);
		MapsUtils.fusionnerDans(eq.variablesModifiees, b.variablesModifiees, v -> v, (u, v) -> u + v);
		
		return eq;
	}

	/**
	 * Renvoie un EquipementData complexe
	 */
	private static EquipementData getStaticComplexe() {
		EquipementData eq = new EquipementData();
		eq.setComplexe();
		return eq;
	}

	/* ===================================================================================
	 * TreeMap d'equipement data (liste d'équipements avec les variables qu'ils modifient)
	 * =================================================================================== */
	
	/**
	 * Crée un nouveau TreeMap<Equipement, EquipementData> tel que ce nouveau treemap est la somme des deux treemap
	 * donnés.
	 */
	public static TreeMap<Integer, EquipementData> combiner(TreeMap<Integer, EquipementData> bonus,
			TreeMap<Integer, EquipementData> malus) {
		
		TreeMap<Integer, EquipementData> nouveau = new TreeMap<>();
		
		MapsUtils.fusionnerDans(nouveau, bonus, v -> v, EquipementData::ajouter);
		MapsUtils.fusionnerDans(nouveau, malus, v -> v, EquipementData::ajouter);
		
		return nouveau;
	}
	
	/**
	 * Utilise epurer sur tous les equipement data du TreeMap et renvoie le résultat
	 */
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
}