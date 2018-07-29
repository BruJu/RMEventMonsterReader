package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.Arrays;
import java.util.stream.Stream;

import fr.bruju.rmeventreader.actionmakers.actionner.Operator;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Représentation d'un combat
 * 
 * @author Bruju
 *
 */
public class Combat {
	/* =========
	 * ATTRIBUTS
	 * ========= */
	
	/** Liste des monstres */
	private Monstre[] monstres;
	/** ID du combat */
	public final int id;
	
	/** Gain d'expérience */
	public int gainExp = 0;
	/** Gain de capacités*/
	private int gainCapa = 0;
	/** Vrai si c'est un combat de boss*/
	private boolean bossBattle = false;
	
	/**
	 * Construit un nouveau combat avec l'id donné
	 * @param id L'id du combat
	 */
	public Combat(int id) {
		this.id = id;
		monstres = new Monstre[Positions.NB_MONSTRES_MAX_PAR_COMBAT];
	}
	
	/* ==================
	 * ACCES AUX MONSTRES
	 * ================== */
	
	/**
	 * Donne le monstre à la position donnée
	 * @param position La position du monstre
	 * @return Le monstre à la position
	 */
	public Monstre getMonstre(int position) {
		return monstres[position];
	}

	/**
	 * Donne le monstre à la position donnée. Si le monstre est null, le crée si l'opérateur donné n'est pas absorbant
	 * à gauche.
	 * @param position La position du monstre
	 * @param operator L'opérateur qui veut appliquer une opération
	 * @return Le monstre à la position donnée
	 */
	public Monstre getMonstre(Integer position, Operator operator) {
		if (monstres[position] == null) {
			if (operator.estAbsorbantAGauche())
				return null;
			
			monstres[position] = new Monstre(this);
		}
		
		return monstres[position];
	}
	
	/**
	 * Donne un flux de monstres contenant la liste des monstres de ce combat
	 */
	public Stream<Monstre> getMonstersStream() {
		return Arrays.stream(monstres).filter(m -> m != null);
	}

	/**
	 * Enlève le monstre à la position donnée
	 * @param idSlot La position du monstre
	 */
	public void remove(int idSlot) {
		monstres[idSlot] = null;
	}
	
	
	/* =====
	 * GAINS
	 * ===== */
	
	/**
	 * Ajoute un gain de capacité
	 */
	public void addGainCapa(int value) {
		gainCapa += value;
	}
	
	/**
	 * Donne le gain en capacité du combat
	 */
	public int getCapa() {
		return gainCapa;
	}
	
	/**
	 * Fixe ce combat comme étant un combat de boss
	 */
	public void declareBossBattle() {
		bossBattle = true;
	}
	
	/**
	 * Renvoie si le combat est un combat de boss
	 */
	public boolean isBossBattle() {
		return bossBattle;
	}

	/* ======
	 * CALCUL
	 * ====== */
	
	/**
	 * Applique une modification des monstres dans le combat
	 * @param idVariable L'id de la variable modifiée
	 * @param operator L'opérateur à appliquer
	 * @param value La valeur appliquée
	 */
	public void applyModificator(int idVariable, Operator operator, int value) {
		Pair<Positions, Integer> paire = Positions.searchNumVariable(idVariable);
		
		if (paire == null)
			return;
		
		Monstre monstre = getMonstre(paire.getRight(), operator);
		
		if (monstre == null) {
			return;
		}
		
		int posStat = paire.getLeft().ordinal();
		
		monstre.apply(posStat, operator, value);
		
	}
	
	
	/* ========
	 * AFFICHAGE
	 * ======== */
	
	/**
	 * Donne une représentation du combat
	 */
	public String getString() {
		StringBuilder s = new StringBuilder();
		
		if (this.bossBattle) {
			s.append("=== Boss " + id);
		} else {
			s.append("=== Combat " + id);
		}
		
		s.append(" ; CAPA = ")
		 .append(this.gainCapa)
		 .append(" ; EXP = ")
		 .append(this.gainExp);
		for (int i = 0 ; i != Positions.NB_MONSTRES_MAX_PAR_COMBAT ; i++) {
			if (monstres[i] == null)
				continue;
			
			s.append("\n")
			 .append(i)
			 .append(";")
			 .append(monstres[i].getString());
		}
		
		return s.toString();
	}
	
	/**
	 * Donne le header CSV d'un combat
	 */
	public static String getCSVHeader() {
		return "ID;EXP;CAPA;BOSS";
	}
	
	/**
	 * Donne une représentation en CSV du combat
	 */
	public String getCSV() {
		return id + ";" + this.gainExp + ";" + this.gainCapa + ";" + ((this.isBossBattle()) ? "Boss" : "Non");
	}

	
}