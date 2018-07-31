package fr.bruju.rmeventreader.implementation.monsterlist.metier;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.utilitaire.Pair;

/**
 * Classe permettant de retrouver la position des variables qui sont écrites dans un fichier ressource.
 * <p>
 * <pre>
 * //Format du fichier
 * NB_MONSTRES int
 * 
 * - Variables -
 * ID int...
 * Niveau int...
 * EXP int...
 * Capacité int...
 * AutresStatistiques int...
 * ...
 * 
 * - Interrupteurs -
 * Propriétés int...
 * ...
 * 
 * - Modules -
 * NomDeLaVariable int
 * ...
 * 
 * </pre>
 * @author Bruju
 *
 */
public class Contexte {
	/* ========
	 * CONTEXTE
	 * ======== */
	/** Nombre de monstres maximal par combat */
	private int nbDeMonstres;
	/** Statistiques des monstres */
	private Map<Integer, Pair<Integer, String>> statistiquesSurMonstres;
	/** Liste des statistiques */
	private List<String> statistiques;
	/** Propriétés des monstres */
	private Map<Integer, Pair<Integer, String>> proprietesSurMonstres;
	/** Liste des propriétés */
	private List<String> proprietes;
	/** Valeurs pour les différents modules */
	private Map<String, Integer> valeursLues;
	
	/**
	 * Construit un contexte
	 */
	public Contexte() {
		this.statistiquesSurMonstres = new HashMap<>();
		this.statistiques = new ArrayList<>();
		this.proprietesSurMonstres = new HashMap<>();
		this.proprietes = new ArrayList<>();
		this.valeursLues = new HashMap<>();
	}
	
	/**
	 * Donne le nombre maximal de monstres par combats
	 */
	public int getNbDeMonstres() {
		return nbDeMonstres;
	}
	
	/**
	 * A partir du numéro de la variable modifiée, donne un couple <numéro du monstre, statistique>
	 */
	public Pair<Integer, String> getStatistique(int position) {
		return statistiquesSurMonstres.get(position);
	}
	
	/**
	 * Donne la liste des statistiques
	 */
	public List<String> getStatistiques() {
		return statistiques;
	}

	/**
	 * A partir du numéro de l'interrupteur modifié, donne un couple <numéro du monstre, statistique>
	 */
	public Pair<Integer, String> getPropriete(int position) {
		return proprietesSurMonstres.get(position);
	}
	
	/**
	 * Donne la liste des propriétés
	 */
	public List<String> getProprietes() {
		return proprietes;
	}
	
	/**
	 * Donne la variable portant le nom valeur
	 */
	public Integer getVariable(String valeur) {
		return valeursLues.get(valeur);
	}
	
	/**
	 * Rempli le contexte à partir du fichier donné.
	 */
	public void remplirContexte(String fichier) {
		try {
			initierLectureFichier();
			FileReaderByLine.lireLeFichierSansCommentaires(fichier, ligne -> etat.lireLigne(ligne));
		} catch (IOException e) {
			e.printStackTrace();
		}		}
	
	/**
	 * Donne la liste des variables concernant une statistique
	 */
	public int[] getListeVariables(String nomStatistique) {
		int[] idVariables = new int[this.getNbDeMonstres()];
		
		statistiquesSurMonstres.forEach((variable, paire) -> {
			if (paire.getRight().equals(nomStatistique)) {
				idVariables[paire.getLeft()] = variable;
			}
		});
		
		return idVariables;
	}
	
	/* ============================
	 * LECTURE DE FICHIER RESSOURCE
	 * ============================ */
	/** Etat actuel de lecture */
	private Etat etat = null;

	/** Met la lecture au début */
	private void initierLectureFichier() {
		etat = new EtatDebut();
	}
	/** Etat de traitement (Design Pattern State) */
	private interface Etat {
		void lireLigne(String ligne);
	}
	
	/** Lecture du nombre de monstres */
	private class EtatDebut implements Etat {
		@Override
		public void lireLigne(String ligne) {
			if (ligne.equals("- Variables -")) {
				etat = new EtatStatistiques();
				return;
			}
			
			String[] decomposition = ligne.split(" ");
			
			if (decomposition[0].equals("NB_MONSTRES")) {
				nbDeMonstres = Integer.decode(decomposition[1]);
			} else {
				System.out.println(decomposition[0]);
				throw new RuntimeException("Fichier Parametres invalide");
			}
		}
	}

	/** Lecture des statistiques et de leurs positions */
	private class EtatStatistiques implements Etat {
		@Override
		public void lireLigne(String ligne) {
			if (ligne.equals("- Interrupteurs -")) {
				etat = new EtatProprietes();
				return;
			}

			String[] decomposition = ligne.split(" ");
			String nomStatistique = decomposition[0];
			
			statistiques.add(nomStatistique);
			
			for (int i = 1 ; i <= nbDeMonstres ; i++) {
				statistiquesSurMonstres.put(Integer.decode(decomposition[i]), new Pair<>(i-1, nomStatistique));
			}
		}
	}

	/** Lecture des propriétés et leurs positions */
	private class EtatProprietes implements Etat {
		@Override
		public void lireLigne(String ligne) {
			if (ligne.equals("- Modules -")) {
				etat = new EtatModules();
				return;
			}
			
			String[] decomposition = ligne.split(" ");
			String nomStatistique = decomposition[0];
			
			proprietes.add(nomStatistique);
			
			for (int i = 1 ; i <= nbDeMonstres ; i++) {
				proprietesSurMonstres.put(Integer.decode(decomposition[i]), new Pair<>(i-1, nomStatistique));
			}
		}
	}

	/** Lecture des positions des valeurs pour les différents modules */
	private class EtatModules implements Etat {
		@Override
		public void lireLigne(String ligne) {
			String[] decomposition = ligne.split(" ");
			valeursLues.put(decomposition[0], Integer.decode(decomposition[1]));
		}
	}

}
