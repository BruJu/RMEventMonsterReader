package fr.bruju.rmeventreader.dictionnaires.ConstructeurParFichier;

import java.io.IOException;

import fr.bruju.rmeventreader.dictionnaires.modele.Monteur;
import fr.bruju.rmeventreader.filereader.ActionOnLine;
import fr.bruju.rmeventreader.filereader.FileReaderByLine;
import fr.bruju.rmeventreader.filereader.LigneNonReconnueException;

/**
 * Cette classe permet de monter des objets selon une liste de traitements
 * 
 * @author Bruju
 *
 * @param <R> Type d'objet monté
 * @param <M> Un type de monteur pour l'objet en question
 */
public class ConvertisseurLigneVersObjet<R, M extends Monteur<R>> implements ActionOnLine {
	/* ======
	 * STATIC
	 * ====== */
	
	/**
	 * Construit un objet en lisant un fichier
	 * @param chemin Le chemin vers le fichier à lire
	 * @param ss L'objet permettant de construire l'objet
	 * @return null en cas d'échec, l'objet construit en lisant le fichier sinon
	 */
	public static <T, K extends Monteur<T>> T construire(String chemin, ConvertisseurLigneVersObjet<T, ?> ss) {
		try {
			FileReaderByLine.lireLeFichierSansCommentaires(chemin, ss);
		} catch (IOException | LigneNonReconnueException e) {
			e.printStackTrace();
			return null;
		}

		return ss.build();
	}
	
	/**
	 * Méthode ne faisant rien
	 * @param m Le monteur
	 * @param t Le traitement
	 */
	public static <K extends Monteur<?>, T> void throwAway(K m, T t) {
	}
	
	
	/* =======================
	 * CONSTRUCTION D'UN OBJET
	 * ======================= */
	
	/** Monteur de l'objet */
	private M monteur;
	/** Liste des traitements à appliquer lors de la lecture d'un fichier */
	private Traitement<M>[] traitements;
	/** Numéro du traitement en cours */
	private int numeroTraitement;

	/**
	 * Construit un guide de montage d'objet
	 * @param monteur Le monteur à utiliser
	 * @param traitements La liste des traitements du fichier
	 */
	public ConvertisseurLigneVersObjet(M monteur, Traitement<M>[] traitements) {
		this.monteur = monteur;
		this.traitements = traitements;
		this.numeroTraitement = 0;
	}
	
	/**
	 * Lit une ligne du fichier
	 * @param ligne La ligne du fichier
	 * @return L'avancement retour
	 */
	public Avancement traiter(String ligne) {
		if (numeroTraitement == traitements.length) {
			throw new LigneNonReconnueException("Fichier non conforme " + numeroTraitement + " " + ligne);
		}
		
		Avancement avancement = traitements[numeroTraitement].traiter(ligne);
		
		switch (avancement) {
		case Suivant:
			return avancer();
		case SuivantDirect:
		case FinTraitement:
			return avancerDirect(ligne, avancement);
		case Rester:
		case Tuer:
			return avancement;
		}

		throw new LigneNonReconnueException("Avancement non attendu " + avancement);
	}

	/**
	 * Monte l'objet en appliquant au monteur le résultat de tous les traitements puis en renvoyant l'objet
	 * @return L'objet construit
	 */
	public R build() {
		if (monteur == null)
			return null;
		
		while (this.numeroTraitement != traitements.length) {
			if (!traitements[numeroTraitement].skippable()) {
				return null;
			}
			
			numeroTraitement++;
		}

		for (Traitement<M> traitement : traitements) {
			traitement.appliquer(monteur);
		}

		return monteur.build();
	}
	
	/**
	 * Passe au traitement suivant du guide et l'exécute. Si il n'y a pas de suivant, donne au père le relai
	 */
	private Avancement avancerDirect(String ligne, Avancement avancement) {
		numeroTraitement++;
		
		return numeroTraitement == traitements.length ? avancement : traiter(ligne);
	}

	/**
	 * Termine en signalant un passage au traitement suivant
	 */
	private Avancement avancer() {
		numeroTraitement++;
		
		return numeroTraitement == traitements.length ? Avancement.Suivant : Avancement.Rester;
	}

	@Override
	public void read(String line) {
		if (monteur != null && traiter(line) == Avancement.Tuer) {
			monteur = null;
		}
	}

	@Override
	public String toString() {
		String s = "";
		
		for (Traitement<?> t : traitements) {
			s = s + ";" + t.toString();
		}
		
		return s;
	}
}
