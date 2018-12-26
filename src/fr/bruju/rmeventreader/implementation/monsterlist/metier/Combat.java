package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.util.*;
import java.util.stream.Stream;

import fr.bruju.rmdechiffreur.modele.OpMathematique;
import fr.bruju.rmeventreader.implementation.monsterlist.contexte.Contexte;
import fr.bruju.rmeventreader.implementation.monsterlist.contexte.Statistique;
import fr.bruju.util.Pair;

/**
 * Représentation d'un combat
 * 
 * @author Bruju
 *
 */
public class Combat implements Iterable<Monstre> {
	public static final int NOMBRE_DE_MONSTRES = 3;
	
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
	/** Liste des fonds utilisés pour ce combat */
	public List<String> fonds = new ArrayList<>();

	public final Contexte contexte;
	
	/**
	 * Construit un nouveau combat avec l'id donné
	 * @param id L'id du combat
	 */
	public Combat(Contexte contexte, int id) {
		this.id = id;
		monstres = new Monstre[NOMBRE_DE_MONSTRES];
		this.contexte = contexte;
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
	 * @param creerSiInexistant L'opérateur qui veut appliquer une opération
	 * @return Le monstre à la position donnée
	 */
	public Monstre getMonstre(Integer position, boolean creerSiInexistant) {
		if (monstres[position] == null) {
			if (!creerSiInexistant)
				return null;
			
			monstres[position] = new Monstre(this);
		}
		
		return monstres[position];
	}

	@Override
	public Iterator<Monstre> iterator() {
		return new MonstreIterator();
	}

	private class MonstreIterator implements Iterator<Monstre> {
		int i = 0;

		@Override
		public boolean hasNext() {
			while (i != 3 && monstres[i] == null) {
				i++;
			}

			return i != 3;
		}

		@Override
		public Monstre next() {
			return monstres[i++];
		}
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

	public void fixerStatistiqueMonstre(int idSlot, String nomStatistique, int valeur) {
		Monstre monstre = getMonstre(idSlot, true);
		monstre.assigner(nomStatistique, valeur);
	}

	public void calculerStatistiqueMonstre(int idSlot, String nomStatistique, OpMathematique operateur, int valeur) {
		Monstre monstre = getMonstre(idSlot, !operateur.zeroAbsorbantAGauche);

		if (monstre == null) {
			return;
		}

		monstre.modifier(nomStatistique, ancienneValeur -> operateur.calculer(ancienneValeur, valeur));
	}

	/* ===========
	 * FOND / ZONE
	 * =========== */
	
	/**
	 * Ajoute le fond si il n'est pas déjà présent
	 * @param valeur Le numéro du nouveau fond
	 */
	public void addFond(int valeur) {
		if (!fonds.contains(Integer.toString(valeur)))
			fonds.add(Integer.toString(valeur));		
	}

	
	/* ========
	 * AFFICHAGE
	 * ======== */
	
	/**
	 * Donne une représentation du combat
	 */
	public String getString(Serialiseur serialiseur) {
		StringBuilder s = new StringBuilder();
		
		if (this.bossBattle) {
			s.append("=== Boss " + id);
		} else {
			s.append("=== Combat ").append(id);
		}
		
		s.append(" ; CAPA = ")
		 .append(this.gainCapa)
		 .append(" ; EXP = ")
		 .append(this.gainExp);

		for (int i = 0 ; i != monstres.length ; i++) {
			if (monstres[i] == null)
				continue;
			
			s.append("\n")
			 .append(i)
			 .append(";")
			 .append(serialiseur.serialiserMonstre(monstres[i]));
		}

		return s.toString();
	}
	
	/**
	 * Donne le header CSV d'un combat
	 */
	public static String getCSVHeader() {
		return "ID;EXP;CAPA;BOSS;FOND";
	}
	
	/**
	 * Donne une représentation en CSV du combat
	 */
	public String getCSV() {
		return id + ";" + this.gainExp + ";" + this.gainCapa + ";" + ((bossBattle) ? "Boss" : "Non") + ";" + fonds;
	}

}