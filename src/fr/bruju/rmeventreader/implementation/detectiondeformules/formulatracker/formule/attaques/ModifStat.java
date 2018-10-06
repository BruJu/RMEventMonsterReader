package fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.attaques;

import java.util.ArrayList;
import java.util.List;

import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.detectiondeformules.formulatracker.formule.personnage.Statistique;

import java.util.Objects;

/**
 * Classe permettant de stocker la statistique modifiée et l'opérateur qui lui est appliqué
 * 
 * @author Bruju
 *
 */
public class ModifStat {
	/** Stat modifiée */
	public final Statistique stat;
	/** Opérateur appliqué */
	public final OpMathematique operateur;
	/** Conditions intégrées */
	private List<GroupeDeConditions> conditions;

	/**
	 * Crée un modificateur de statistique
	 * 
	 * @param stat Statistique affectée
	 * @param opMathematique Opérateur utilisé
	 */
	public ModifStat(Statistique stat, OpMathematique opMathematique) {
		this.stat = stat;
		this.operateur = opMathematique;
		this.conditions = new ArrayList<>();
	}

	public ModifStat(ModifStat modifStat) {
		this.stat = modifStat.stat;
		this.operateur = modifStat.operateur;
		
		this.conditions = new ArrayList<>(modifStat.conditions);
		this.conditions.add(new GroupeDeConditions());
	}
	
	
	public ModifStat(ModifStat modifStat, ConditionAffichable condition) {
		this.stat = modifStat.stat;
		this.operateur = modifStat.operateur;
		
		this.conditions = new ArrayList<>(modifStat.conditions);
		

		GroupeDeConditions dernierGroupe = conditions.get(conditions.size() - 1);
		
		conditions.set(conditions.size() - 1, new GroupeDeConditions(dernierGroupe, condition));

	}
	
	
	
	public ModifStat(ModifStat modifStat, GroupeDeConditions left) {
		this.stat = modifStat.stat;
		this.operateur = modifStat.operateur;
		
		this.conditions = new ArrayList<>(modifStat.conditions);
		this.conditions.add(left);
	}

	public List<GroupeDeConditions> getConditions() {
		return this.conditions;
	}



	@Override
	public String toString() {
		return "<" + stat.possesseur.getNom() + "." + stat.nom + ";" + operateur + ">";
	}



	@Override
	public int hashCode() {
		return Objects.hash(stat, operateur, conditions);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof ModifStat) {
			ModifStat that = (ModifStat) object;
			return Objects.equals(this.stat, that.stat) && Objects.equals(this.operateur, that.operateur)
					&& Objects.equals(this.conditions, that.conditions);
		}
		return false;
	}

	
	public int hashUnifiable() {
		return Objects.hash(stat.nom, operateur, conditions);
	}

	
	public boolean estUnifiable(Object object) {
		if (object instanceof ModifStat) {
			ModifStat that = (ModifStat) object;
			return Objects.equals(this.stat.nom, that.stat.nom) && Objects.equals(this.operateur, that.operateur)
					&& Objects.equals(this.conditions, that.conditions);
		}
		return false;
	}
	
	
}
